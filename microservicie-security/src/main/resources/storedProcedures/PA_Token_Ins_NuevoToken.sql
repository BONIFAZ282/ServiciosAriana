IF OBJECT_ID('PA_Token_Ins_NuevoToken') IS NOT NULL
    DROP PROCEDURE PA_Token_Ins_NuevoToken
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Inserta un nuevo token.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------

EJEMPLO:
    EXEC PA_Token_Ins_NuevoToken 1, 'eyJhbGciOi...', 'ACCESS', '2025-01-01 15:30:00'
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Token_Ins_NuevoToken(
    @nUsuarioSecurityId INT,
    @cTokenHash VARCHAR(255),
    @cTipoToken VARCHAR(20),
    @dFechaExpiracion DATETIME
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            INSERT INTO Token (nUsuarioSecurityId, cTokenHash, cTipoToken, dFechaExpiracion)
            VALUES(@nUsuarioSecurityId, @cTokenHash, @cTipoToken, @dFechaExpiracion)
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRAN
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