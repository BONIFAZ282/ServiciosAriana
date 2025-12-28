IF OBJECT_ID('PA_UsuarioSecurity_Upd_UltimoLogin') IS NOT NULL
    DROP PROCEDURE PA_UsuarioSecurity_Upd_UltimoLogin
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Actualiza el último login del usuario y resetea intentos.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------

EJEMPLO:
    EXEC PA_UsuarioSecurity_Upd_UltimoLogin 1
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioSecurity_Upd_UltimoLogin(
    @nUsuarioSecurityId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            UPDATE UsuarioSecurity
            SET dUltimoLogin = GETDATE(),
                nIntentosFallidos = 0,
                dBloqueadoHasta = NULL,
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