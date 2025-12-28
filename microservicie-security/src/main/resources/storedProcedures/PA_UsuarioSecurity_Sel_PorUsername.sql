IF OBJECT_ID('PA_UsuarioSecurity_Sel_PorUsername') IS NOT NULL
    DROP PROCEDURE PA_UsuarioSecurity_Sel_PorUsername
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Busca un usuario por su username.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------

EJEMPLO:
    EXEC PA_UsuarioSecurity_Sel_PorUsername 'admin'
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioSecurity_Sel_PorUsername(
    @cUsername VARCHAR(100)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            nUsuarioSecurityId,
            nUsuarioId,
            cUsername,
            cPasswordHash,
            dUltimoLogin,
            nIntentosFallidos,
            dBloqueadoHasta,
            dFechaCreacion,
            dFechaModificacion,
            bEstado
        FROM UsuarioSecurity
        WHERE cUsername = @cUsername
        AND bEstado = 1
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