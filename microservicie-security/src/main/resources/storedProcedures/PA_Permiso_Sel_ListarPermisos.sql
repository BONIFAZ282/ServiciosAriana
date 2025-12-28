IF OBJECT_ID('PA_Permiso_Sel_ListarPermisos') IS NOT NULL
    DROP PROCEDURE PA_Permiso_Sel_ListarPermisos
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Lista todos los permisos activos.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Permiso_Sel_ListarPermisos
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            nPermisoId, cRecurso, cAccion, cDescripcionPermiso,
            dFechaCreacion, bEstado
        FROM Permiso
        WHERE bEstado = 1
        ORDER BY cRecurso, cAccion
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END