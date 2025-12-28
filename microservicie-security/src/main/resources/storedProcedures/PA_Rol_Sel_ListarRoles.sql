IF OBJECT_ID('PA_Rol_Sel_ListarRoles') IS NOT NULL
    DROP PROCEDURE PA_Rol_Sel_ListarRoles
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Lista todos los roles activos.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Rol_Sel_ListarRoles
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            nRolId, cNombreRol, cDescripcionRol,
            dFechaCreacion, dFechaModificacion, bEstado
        FROM Rol
        WHERE bEstado = 1
        ORDER BY cNombreRol
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END