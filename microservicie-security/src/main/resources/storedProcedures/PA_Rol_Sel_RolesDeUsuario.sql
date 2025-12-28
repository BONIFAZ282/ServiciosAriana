IF OBJECT_ID('PA_Rol_Sel_RolesDeUsuario') IS NOT NULL
    DROP PROCEDURE PA_Rol_Sel_RolesDeUsuario
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Obtiene todos los roles de un usuario.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Rol_Sel_RolesDeUsuario(
    @nUsuarioSecurityId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            r.nRolId, r.cNombreRol, r.cDescripcionRol,
            r.dFechaCreacion, r.dFechaModificacion, r.bEstado
        FROM Rol r
        INNER JOIN UsuarioRol ur ON r.nRolId = ur.nRolId
        WHERE ur.nUsuarioSecurityId = @nUsuarioSecurityId
        AND r.bEstado = 1 AND ur.bEstado = 1
        ORDER BY r.cNombreRol
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END