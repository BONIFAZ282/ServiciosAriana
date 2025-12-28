package com.microservice.microservicie_security.repository.StoredProcedure;

public class StoredProcedureSecurity {

    /*PA'S PARA USUARIOS SECURITY*/
    public static final String SEL_USUARIO_POR_USERNAME = "{call PA_UsuarioSecurity_Sel_PorUsername(?)}";
    public static final String SEL_USUARIO_POR_ID = "{call PA_UsuarioSecurity_Sel_PorUsuarioId(?)}";
    public static final String INS_USUARIO_NUEVO = "{call PA_UsuarioSecurity_Ins_NuevoUsuario(?,?,?)}";
    public static final String UPD_ULTIMO_LOGIN = "{call PA_UsuarioSecurity_Upd_UltimoLogin(?)}";
    public static final String UPD_INTENTOS_FALLIDOS = "{call PA_UsuarioSecurity_Upd_IntentosFallidos(?,?)}";
    public static final String UPD_BLOQUEAR_USUARIO = "{call PA_UsuarioSecurity_Upd_BloquearUsuario(?,?)}";
    public static final String UPD_DESBLOQUEAR_USUARIO = "{call PA_UsuarioSecurity_Upd_DesbloquearUsuario(?)}";
    public static final String UPD_CAMBIAR_PASSWORD = "{call PA_UsuarioSecurity_Upd_CambiarPassword(?,?)}";

    /*PA'S PARA ROLES*/
    public static final String SEL_ROLES = "{call PA_Rol_Sel_ListarRoles}";
    public static final String SEL_ROL_POR_NOMBRE = "{call PA_Rol_Sel_PorNombre(?)}";
    public static final String SEL_ROLES_DE_USUARIO = "{call PA_Rol_Sel_RolesDeUsuario(?)}";
    public static final String INS_ROL_NUEVO = "{call PA_Rol_Ins_NuevoRol(?,?)}";
    public static final String UPD_MODIFICAR_ROL = "{call PA_Rol_Upd_ModificarRol(?,?,?)}";
    public static final String UPD_ELIMINAR_ROL = "{call PA_Rol_Upd_EliminarRol(?)}";

    /*PA'S PARA PERMISOS*/
    public static final String SEL_PERMISOS = "{call PA_Permiso_Sel_ListarPermisos}";
    public static final String SEL_PERMISO_POR_RECURSO_ACCION = "{call PA_Permiso_Sel_PorRecursoAccion(?,?)}";
    public static final String SEL_PERMISOS_DE_USUARIO = "{call PA_Permiso_Sel_PermisosDeUsuario(?)}";
    public static final String SEL_PERMISOS_DE_ROL = "{call PA_Permiso_Sel_PermisosDeRol(?)}";
    public static final String SEL_VALIDAR_PERMISO = "{call PA_Permiso_Sel_ValidarPermiso(?,?,?)}";
    public static final String INS_PERMISO_NUEVO = "{call PA_Permiso_Ins_NuevoPermiso(?,?,?)}";

    /*PA'S PARA TOKENS*/
    public static final String SEL_VALIDAR_TOKEN = "{call PA_Token_Sel_ValidarToken(?)}";
    public static final String SEL_TOKENS_DE_USUARIO = "{call PA_Token_Sel_TokensDeUsuario(?)}";
    public static final String INS_TOKEN_NUEVO = "{call PA_Token_Ins_NuevoToken(?,?,?,?)}";
    public static final String UPD_REVOCAR_TOKEN = "{call PA_Token_Upd_RevocarToken(?)}";
    public static final String UPD_REVOCAR_TOKENS_USUARIO = "{call PA_Token_Upd_RevocarTokensUsuario(?,?)}";
    public static final String DEL_TOKENS_EXPIRADOS = "{call PA_Token_Del_TokensExpirados}";

    /*PA'S PARA USUARIO-ROL*/
    public static final String INS_ASIGNAR_ROL = "{call PA_UsuarioRol_Ins_AsignarRol(?,?)}";
    public static final String UPD_REMOVER_ROL = "{call PA_UsuarioRol_Upd_RemoverRol(?,?)}";
    public static final String SEL_USUARIOS_CON_ROL = "{call PA_UsuarioRol_Sel_UsuariosConRol(?)}";

    /*PA'S PARA ROL-PERMISO*/
    public static final String INS_ASIGNAR_PERMISO = "{call PA_RolPermiso_Ins_AsignarPermiso(?,?)}";
    public static final String UPD_REMOVER_PERMISO = "{call PA_RolPermiso_Upd_RemoverPermiso(?,?)}";
}