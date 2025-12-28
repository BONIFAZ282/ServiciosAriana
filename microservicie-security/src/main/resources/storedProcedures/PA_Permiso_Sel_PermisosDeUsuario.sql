IF OBJECT_ID('PA_Permiso_Sel_PermisosDeUsuario') IS NOT NULL
    DROP PROCEDURE PA_Permiso_Sel_PermisosDeUsuario
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Obtiene todos los permisos de un usuario.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------

EJEMPLO:
    EXEC PA_Permiso_Sel_PermisosDeUsuario 1
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Permiso_Sel_PermisosDeUsuario(
    @nUsuarioSecurityId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT DISTINCT
            p.nPermisoId,
            p.cRecurso,
            p.cAccion,
            p.cDescripcionPermiso,
            p.dFechaCreacion,
            p.bEstado
        FROM Permiso p
        INNER JOIN RolPermiso rp ON p.nPermisoId = rp.nPermisoId
        INNER JOIN UsuarioRol ur ON rp.nRolId = ur.nRolId
        WHERE ur.nUsuarioSecurityId = @nUsuarioSecurityId
        AND p.bEstado = 1
        AND rp.bEstado = 1
        AND ur.bEstado = 1
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000)
        DECLARE @ErrorSeverity INT
        DECLARE @ErrorState INT

        SELECT
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END