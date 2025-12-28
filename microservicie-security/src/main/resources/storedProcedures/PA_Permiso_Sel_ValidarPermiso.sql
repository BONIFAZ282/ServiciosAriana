IF OBJECT_ID('PA_Permiso_Sel_ValidarPermiso') IS NOT NULL
    DROP PROCEDURE PA_Permiso_Sel_ValidarPermiso
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Valida si un usuario tiene un permiso específico.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------

EJEMPLO:
    EXEC PA_Permiso_Sel_ValidarPermiso 1, 'cargo', 'read'
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Permiso_Sel_ValidarPermiso(
    @nUsuarioSecurityId INT,
    @cRecurso VARCHAR(100),
    @cAccion VARCHAR(50)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        DECLARE @bTienePermiso BIT = 0

        IF EXISTS(
            SELECT 1
            FROM Permiso p
            INNER JOIN RolPermiso rp ON p.nPermisoId = rp.nPermisoId
            INNER JOIN UsuarioRol ur ON rp.nRolId = ur.nRolId
            WHERE ur.nUsuarioSecurityId = @nUsuarioSecurityId
            AND p.cRecurso = @cRecurso
            AND p.cAccion = @cAccion
            AND p.bEstado = 1
            AND rp.bEstado = 1
            AND ur.bEstado = 1
        )
            SET @bTienePermiso = 1

        SELECT @bTienePermiso AS TienePermiso
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000)
        DECLARE @ErrorSeverity INT
        DECLARE @ErrorState INT

        SELECT
            @ErrorMessage = ERROR_MESSAGE(),
            @ErrorSeverity = ERROR_SEVERITY(),
            @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END