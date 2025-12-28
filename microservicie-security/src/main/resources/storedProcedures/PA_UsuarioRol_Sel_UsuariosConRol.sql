IF OBJECT_ID('PA_UsuarioRol_Sel_UsuariosConRol') IS NOT NULL
    DROP PROCEDURE PA_UsuarioRol_Sel_UsuariosConRol
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Obtiene usuarios que tienen un rol específico.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioRol_Sel_UsuariosConRol(
    @nRolId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            ur.nUsuarioRolId, ur.nUsuarioSecurityId, ur.nRolId,
            ur.dFechaAsignacion, ur.bEstado
        FROM UsuarioRol ur
        WHERE ur.nRolId = @nRolId
        AND ur.bEstado = 1
        ORDER BY ur.dFechaAsignacion DESC
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END