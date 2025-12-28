IF OBJECT_ID('PA_Token_Sel_TokensDeUsuario') IS NOT NULL
    DROP PROCEDURE PA_Token_Sel_TokensDeUsuario
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Obtiene todos los tokens de un usuario.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Token_Sel_TokensDeUsuario(
    @nUsuarioSecurityId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            nTokenId, nUsuarioSecurityId, cTokenHash, cTipoToken,
            dFechaExpiracion, dFechaCreacion, bExpirado, bRevocado
        FROM Token
        WHERE nUsuarioSecurityId = @nUsuarioSecurityId
        ORDER BY dFechaCreacion DESC
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END