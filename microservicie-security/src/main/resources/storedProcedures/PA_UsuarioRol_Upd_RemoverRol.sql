IF OBJECT_ID('PA_UsuarioRol_Upd_RemoverRol') IS NOT NULL
    DROP PROCEDURE PA_UsuarioRol_Upd_RemoverRol
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Remueve un rol de un usuario (eliminación lógica).
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioRol_Upd_RemoverRol(
    @nUsuarioSecurityId INT,
    @nRolId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            UPDATE UsuarioRol
            SET bEstado = 0
            WHERE nUsuarioSecurityId = @nUsuarioSecurityId
            AND nRolId = @nRolId
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END