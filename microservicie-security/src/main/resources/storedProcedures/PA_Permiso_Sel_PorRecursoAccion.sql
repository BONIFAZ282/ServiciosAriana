IF OBJECT_ID('PA_Permiso_Sel_PorRecursoAccion') IS NOT NULL
    DROP PROCEDURE PA_Permiso_Sel_PorRecursoAccion
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Busca un permiso por recurso y acción.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Permiso_Sel_PorRecursoAccion(
    @cRecurso VARCHAR(100),
    @cAccion VARCHAR(50)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            nPermisoId, cRecurso, cAccion, cDescripcionPermiso,
            dFechaCreacion, bEstado
        FROM Permiso
        WHERE cRecurso = @cRecurso
        AND cAccion = @cAccion
        AND bEstado = 1
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END