IF OBJECT_ID('PA_UsuarioSecurity_Upd_BloquearUsuario') IS NOT NULL
    DROP PROCEDURE PA_UsuarioSecurity_Upd_BloquearUsuario
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Bloquea un usuario por X minutos.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioSecurity_Upd_BloquearUsuario(
    @nUsuarioSecurityId INT,
    @nMinutos INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            UPDATE UsuarioSecurity
            SET dBloqueadoHasta = DATEADD(MINUTE, @nMinutos, GETDATE()),
                dFechaModificacion = GETDATE()
            WHERE nUsuarioSecurityId = @nUsuarioSecurityId
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END