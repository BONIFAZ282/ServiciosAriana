IF OBJECT_ID('PA_Rol_Upd_EliminarRol') IS NOT NULL
    DROP PROCEDURE PA_Rol_Upd_EliminarRol
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Elimina lógicamente un rol.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Rol_Upd_EliminarRol(
    @nRolId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            UPDATE Rol
            SET bEstado = 0,
                dFechaModificacion = GETDATE()
            WHERE nRolId = @nRolId
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END