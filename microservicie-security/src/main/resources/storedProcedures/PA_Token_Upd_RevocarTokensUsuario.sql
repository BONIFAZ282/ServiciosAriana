IF OBJECT_ID('PA_Token_Upd_RevocarTokensUsuario') IS NOT NULL
    DROP PROCEDURE PA_Token_Upd_RevocarTokensUsuario
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Revoca todos los tokens de un usuario por tipo.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Token_Upd_RevocarTokensUsuario(
    @nUsuarioSecurityId INT,
    @cTipoToken VARCHAR(20)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            UPDATE Token
            SET bRevocado = 1
            WHERE nUsuarioSecurityId = @nUsuarioSecurityId
            AND cTipoToken = @cTipoToken
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END