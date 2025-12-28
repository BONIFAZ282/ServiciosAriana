IF OBJECT_ID('PA_UsuarioSecurity_Sel_PorUsuarioId') IS NOT NULL
    DROP PROCEDURE PA_UsuarioSecurity_Sel_PorUsuarioId
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Busca un usuario por su ID de usuario (referencia).
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioSecurity_Sel_PorUsuarioId(
    @nUsuarioId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        SELECT
            nUsuarioSecurityId, nUsuarioId, cUsername, cPasswordHash,
            dUltimoLogin, nIntentosFallidos, dBloqueadoHasta,
            dFechaCreacion, dFechaModificacion, bEstado
        FROM UsuarioSecurity
        WHERE nUsuarioId = @nUsuarioId AND bEstado = 1
    END TRY
    BEGIN CATCH
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END