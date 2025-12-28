IF OBJECT_ID('PA_Token_Sel_ValidarToken') IS NOT NULL
    DROP PROCEDURE PA_Token_Sel_ValidarToken
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Valida si un token es válido y activo.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------

EJEMPLO:
    EXEC PA_Token_Sel_ValidarToken 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...'
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Token_Sel_ValidarToken(
    @cTokenHash VARCHAR(255)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            nTokenId,
            nUsuarioSecurityId,
            cTokenHash,
            cTipoToken,
            dFechaExpiracion,
            dFechaCreacion,
            bExpirado,
            bRevocado
        FROM Token
        WHERE cTokenHash = @cTokenHash
        AND bRevocado = 0
        AND bExpirado = 0
        AND dFechaExpiracion > GETDATE()
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