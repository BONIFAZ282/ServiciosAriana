IF OBJECT_ID('PA_UsuarioSecurity_Upd_IntentosFallidos') IS NOT NULL
    DROP PROCEDURE PA_UsuarioSecurity_Upd_IntentosFallidos
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Incrementa los intentos fallidos de login.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------

EJEMPLO:
    EXEC PA_UsuarioSecurity_Upd_IntentosFallidos 1, 2
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioSecurity_Upd_IntentosFallidos(
    @nUsuarioSecurityId INT,
    @nIntentosFallidos INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            DECLARE @dBloqueadoHasta DATETIME = NULL

            -- Si tiene 3 o más intentos, bloquear por 15 minutos
            IF @nIntentosFallidos >= 3
                SET @dBloqueadoHasta = DATEADD(MINUTE, 15, GETDATE())

            UPDATE UsuarioSecurity
            SET nIntentosFallidos = @nIntentosFallidos,
                dBloqueadoHasta = @dBloqueadoHasta,
                dFechaModificacion = GETDATE()
            WHERE nUsuarioSecurityId = @nUsuarioSecurityId
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