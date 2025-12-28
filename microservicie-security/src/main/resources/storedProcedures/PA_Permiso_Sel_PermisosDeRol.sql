IF OBJECT_ID('PA_Permiso_Sel_PermisosDeRol') IS NOT NULL
    DROP PROCEDURE PA_Permiso_Sel_PermisosDeRol
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Obtiene todos los permisos de un rol.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Permiso_Sel_PermisosDeRol(
    @nRolId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            p.nPermisoId, p.cRecurso, p.cAccion, p.cDescripcionPermiso,
            p.dFechaCreacion, p.bEstado
        FROM Permiso p
        INNER JOIN RolPermiso rp ON p.nPermisoId = rp.nPermisoId
        WHERE rp.nRolId = @nRolId
        AND p.bEstado = 1 AND rp.bEstado = 1
        ORDER BY p.cRecurso, p.cAccion
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END