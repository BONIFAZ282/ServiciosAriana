IF OBJECT_ID('PA_Permiso_Ins_NuevoPermiso') IS NOT NULL
    DROP PROCEDURE PA_Permiso_Ins_NuevoPermiso
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Inserta un nuevo permiso.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Permiso_Ins_NuevoPermiso(
    @cRecurso VARCHAR(100),
    @cAccion VARCHAR(50),
    @cDescripcionPermiso VARCHAR(200)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            INSERT INTO Permiso (cRecurso, cAccion, cDescripcionPermiso)
            VALUES(@cRecurso, @cAccion, @cDescripcionPermiso)
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END