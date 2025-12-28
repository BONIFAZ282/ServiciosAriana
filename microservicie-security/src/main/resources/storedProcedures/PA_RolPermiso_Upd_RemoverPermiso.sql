IF OBJECT_ID('PA_RolPermiso_Upd_RemoverPermiso') IS NOT NULL
    DROP PROCEDURE PA_RolPermiso_Upd_RemoverPermiso
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Remueve un permiso de un rol (eliminación lógica).
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_RolPermiso_Upd_RemoverPermiso(
    @nRolId INT,
    @nPermisoId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            UPDATE RolPermiso
            SET bEstado = 0
            WHERE nRolId = @nRolId
            AND nPermisoId = @nPermisoId
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END