IF OBJECT_ID('PA_Token_Del_TokensExpirados') IS NOT NULL
    DROP PROCEDURE PA_Token_Del_TokensExpirados
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Elimina tokens expirados hace más de 7 días.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Token_Del_TokensExpirados
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            DELETE FROM Token
            WHERE dFechaExpiracion < DATEADD(DAY, -7, GETDATE())
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END