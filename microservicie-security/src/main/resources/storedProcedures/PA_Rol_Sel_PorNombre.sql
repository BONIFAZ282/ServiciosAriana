IF OBJECT_ID('PA_Rol_Sel_PorNombre') IS NOT NULL
    DROP PROCEDURE PA_Rol_Sel_PorNombre
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Busca un rol por nombre.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Rol_Sel_PorNombre(
    @cNombreRol VARCHAR(50)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            nRolId, cNombreRol, cDescripcionRol,
            dFechaCreacion, dFechaModificacion, bEstado
        FROM Rol
        WHERE cNombreRol = @cNombreRol AND bEstado = 1
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END