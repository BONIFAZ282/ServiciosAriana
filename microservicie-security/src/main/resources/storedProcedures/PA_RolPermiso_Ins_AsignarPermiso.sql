IF OBJECT_ID('PA_RolPermiso_Ins_AsignarPermiso') IS NOT NULL
    DROP PROCEDURE PA_RolPermiso_Ins_AsignarPermiso
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Asigna un permiso a un rol.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_RolPermiso_Ins_AsignarPermiso(
    @nRolId INT,
    @nPermisoId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            -- Verificar si ya existe la asignación
            IF NOT EXISTS(SELECT 1 FROM RolPermiso WHERE nRolId = @nRolId AND nPermisoId = @nPermisoId)
            BEGIN
                INSERT INTO RolPermiso (nRolId, nPermisoId)
                VALUES(@nRolId, @nPermisoId)
            END
            ELSE
            BEGIN
                -- Si existe pero está inactivo, reactivarlo
                UPDATE RolPermiso
                SET bEstado = 1,
                    dFechaAsignacion = GETDATE()
                WHERE nRolId = @nRolId
                AND nPermisoId = @nPermisoId
            END
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END