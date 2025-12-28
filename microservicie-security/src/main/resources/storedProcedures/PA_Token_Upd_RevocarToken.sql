IF OBJECT_ID('PA_Token_Upd_RevocarToken') IS NOT NULL
    DROP PROCEDURE PA_Token_Upd_RevocarToken
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Revoca un token específico.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Token_Upd_RevocarToken(
    @cTokenHash VARCHAR(255)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            UPDATE Token
            SET bRevocado = 1
            WHERE cTokenHash = @cTokenHash
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END