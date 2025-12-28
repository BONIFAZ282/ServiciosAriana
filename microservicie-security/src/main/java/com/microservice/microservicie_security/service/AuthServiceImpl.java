package com.microservice.microservicie_security.service;

import com.microservice.microservicie_security.dto.*;
import com.microservice.microservicie_security.entity.Permiso;
import com.microservice.microservicie_security.entity.Rol;
import com.microservice.microservicie_security.entity.Token;
import com.microservice.microservicie_security.entity.UsuarioSecurity;
import com.microservice.microservicie_security.repository.IPermisoRepository;
import com.microservice.microservicie_security.repository.IRolRepository;
import com.microservice.microservicie_security.repository.ITokenRepository;
import com.microservice.microservicie_security.repository.IUsuarioSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IUsuarioSecurityRepository usuarioSecurityRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private IPermisoRepository permisoRepository;

    @Autowired
    private ITokenRepository tokenRepository;

    @Autowired
    private IJwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        try {
            // 1. Buscar usuario por username
            Optional<UsuarioSecurity> usuarioOpt = usuarioSecurityRepository.findByUsername(loginRequest.getUsername());
            if (usuarioOpt.isEmpty()) {
                throw new RuntimeException("Credenciales inválidas");
            }

            UsuarioSecurity usuario = usuarioOpt.get();

            // 2. Verificar si está bloqueado
            if (usuario.isAccountLocked()) {
                throw new RuntimeException("Cuenta bloqueada temporalmente. Intente más tarde.");
            }

            // 3. Verificar contraseña
            if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getCPasswordHash())) {
                // Incrementar intentos fallidos
                usuario.incrementFailedAttempts();
                usuarioSecurityRepository.updateFailedAttempts(usuario.getNUsuarioSecurityId(), usuario.getNIntentosFallidos());
                throw new RuntimeException("Credenciales inválidas");
            }

            // 4. Resetear intentos fallidos y actualizar último login
            usuarioSecurityRepository.updateLastLogin(usuario.getNUsuarioSecurityId());

            // 5. Obtener roles y permisos
            List<Rol> roles = rolRepository.findRolesByUsuarioId(usuario.getNUsuarioSecurityId());
            List<Permiso> permisos = permisoRepository.findPermisosByUsuarioId(usuario.getNUsuarioSecurityId());

            List<String> roleNames = roles.stream().map(Rol::getCNombreRol).collect(Collectors.toList());
            List<String> permisoNames = permisos.stream()
                    .map(p -> p.getCRecurso() + ":" + p.getCAccion())
                    .collect(Collectors.toList());

            // 6. Generar tokens
            String accessToken = jwtService.generateAccessToken(usuario, roleNames, permisoNames);
            String refreshToken = jwtService.generateRefreshToken(usuario);

            // 7. Guardar tokens en BD
            LocalDateTime accessTokenExp = LocalDateTime.now().plusSeconds(1800); // 30 min
            LocalDateTime refreshTokenExp = LocalDateTime.now().plusSeconds(604800); // 7 días

            tokenRepository.createToken(usuario.getNUsuarioSecurityId(), jwtService.hashToken(accessToken), "ACCESS", accessTokenExp);
            tokenRepository.createToken(usuario.getNUsuarioSecurityId(), jwtService.hashToken(refreshToken), "REFRESH", refreshTokenExp);

            return new AuthResponse(
                    accessToken,
                    refreshToken,
                    accessTokenExp,
                    usuario.getNUsuarioId(),
                    usuario.getNUsuarioSecurityId(),
                    usuario.getCUsername(),
                    roleNames,
                    permisoNames
            );

        } catch (Exception e) {
            throw new RuntimeException("Error en el login: " + e.getMessage());
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            // 1. Validar refresh token
            if (!jwtService.isTokenValid(refreshToken)) {
                throw new RuntimeException("Refresh token inválido o expirado");
            }

            // 2. Verificar si está en la BD y no revocado
            String hashedToken = jwtService.hashToken(refreshToken);
            Optional<Token> tokenOpt = tokenRepository.findValidToken(hashedToken);
            if (tokenOpt.isEmpty()) {
                throw new RuntimeException("Refresh token revocado o no encontrado");
            }

            // 3. Obtener usuario
            Integer usuarioSecurityId = jwtService.extractUsuarioSecurityId(refreshToken);
            Optional<UsuarioSecurity> usuarioOpt = usuarioSecurityRepository.findByUsuarioId(usuarioSecurityId);
            if (usuarioOpt.isEmpty()) {
                throw new RuntimeException("Usuario no encontrado");
            }

            UsuarioSecurity usuario = usuarioOpt.get();

            // 4. Obtener roles y permisos actualizados
            List<Rol> roles = rolRepository.findRolesByUsuarioId(usuario.getNUsuarioSecurityId());
            List<Permiso> permisos = permisoRepository.findPermisosByUsuarioId(usuario.getNUsuarioSecurityId());

            List<String> roleNames = roles.stream().map(Rol::getCNombreRol).collect(Collectors.toList());
            List<String> permisoNames = permisos.stream()
                    .map(p -> p.getCRecurso() + ":" + p.getCAccion())
                    .collect(Collectors.toList());

            // 5. Generar nuevo access token
            String newAccessToken = jwtService.generateAccessToken(usuario, roleNames, permisoNames);
            LocalDateTime accessTokenExp = LocalDateTime.now().plusSeconds(1800); // 30 min

            // 6. Guardar nuevo access token
            tokenRepository.createToken(usuario.getNUsuarioSecurityId(), jwtService.hashToken(newAccessToken), "ACCESS", accessTokenExp);

            return new AuthResponse(
                    newAccessToken,
                    refreshToken, // Mantener el mismo refresh token
                    accessTokenExp,
                    usuario.getNUsuarioId(),
                    usuario.getNUsuarioSecurityId(),
                    usuario.getCUsername(),
                    roleNames,
                    permisoNames
            );

        } catch (Exception e) {
            throw new RuntimeException("Error al refrescar token: " + e.getMessage());
        }
    }

    @Override
    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest) {
        try {
            String token = validateTokenRequest.getToken();

            // 1. Validar formato y expiración del token
            if (!jwtService.isTokenValid(token)) {
                return new ValidateTokenResponse(false, "Token inválido o expirado");
            }

            // 2. Verificar si está en la blacklist
            String hashedToken = jwtService.hashToken(token);
            Optional<Token> tokenOpt = tokenRepository.findValidToken(hashedToken);
            if (tokenOpt.isEmpty()) {
                return new ValidateTokenResponse(false, "Token revocado o no encontrado");
            }

            // 3. Extraer información del token
            String username = jwtService.extractUsername(token);
            Integer usuarioId = jwtService.extractUsuarioId(token);
            Integer usuarioSecurityId = jwtService.extractUsuarioSecurityId(token);
            List<String> roles = jwtService.extractRoles(token);
            List<String> permisos = jwtService.extractPermisos(token);
            LocalDateTime expiresAt = jwtService.extractExpiration(token);

            return new ValidateTokenResponse(
                    true,
                    usuarioId,
                    usuarioSecurityId,
                    username,
                    roles,
                    permisos,
                    expiresAt
            );

        } catch (Exception e) {
            return new ValidateTokenResponse(false, "Error al validar token: " + e.getMessage());
        }
    }

    @Override
    public void logout(String token) {
        try {
            String hashedToken = jwtService.hashToken(token);
            tokenRepository.revokeToken(hashedToken);
        } catch (Exception e) {
            throw new RuntimeException("Error al cerrar sesión: " + e.getMessage());
        }
    }

    @Override
    public void changePassword(Integer usuarioSecurityId, ChangePasswordRequest changePasswordRequest) {
        try {
            // 1. Verificar que las contraseñas coincidan
            if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
                throw new RuntimeException("Las contraseñas no coinciden");
            }

            // 2. Buscar usuario
            Optional<UsuarioSecurity> usuarioOpt = usuarioSecurityRepository.findByUsuarioId(usuarioSecurityId);
            if (usuarioOpt.isEmpty()) {
                throw new RuntimeException("Usuario no encontrado");
            }

            UsuarioSecurity usuario = usuarioOpt.get();

            // 3. Verificar contraseña actual
            if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), usuario.getCPasswordHash())) {
                throw new RuntimeException("Contraseña actual incorrecta");
            }

            // 4. Encriptar nueva contraseña
            String newPasswordHash = passwordEncoder.encode(changePasswordRequest.getNewPassword());

            // 5. Actualizar contraseña
            usuarioSecurityRepository.changePassword(usuario.getNUsuarioSecurityId(), newPasswordHash);

            // 6. Revocar todos los tokens del usuario
            tokenRepository.revokeAllTokensByUser(usuario.getNUsuarioSecurityId(), "ACCESS");
            tokenRepository.revokeAllTokensByUser(usuario.getNUsuarioSecurityId(), "REFRESH");

        } catch (Exception e) {
            throw new RuntimeException("Error al cambiar contraseña: " + e.getMessage());
        }
    }

    @Override
    public PermissionCheckResponse hasPermission(PermissionCheckRequest permissionCheckRequest) {
        try {
            Boolean hasPermission = permisoRepository.hasPermission(
                    permissionCheckRequest.getUsuarioId(),
                    permissionCheckRequest.getRecurso(),
                    permissionCheckRequest.getAccion()
            );

            return new PermissionCheckResponse(
                    hasPermission,
                    permissionCheckRequest.getRecurso(),
                    permissionCheckRequest.getAccion(),
                    permissionCheckRequest.getUsuarioId()
            );

        } catch (Exception e) {
            return new PermissionCheckResponse(
                    false,
                    "Error al verificar permisos: " + e.getMessage(),
                    permissionCheckRequest.getRecurso(),
                    permissionCheckRequest.getAccion(),
                    permissionCheckRequest.getUsuarioId()
            );
        }
    }
}