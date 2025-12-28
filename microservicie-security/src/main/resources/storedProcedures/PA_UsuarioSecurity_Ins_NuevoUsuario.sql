IF OBJECT_ID('PA_UsuarioSecurity_Ins_NuevoUsuario') IS NOT NULL
    DROP PROCEDURE PA_UsuarioSecurity_Ins_NuevoUsuario
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Inserta un nuevo usuario de seguridad.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------

EJEMPLO:
    EXEC PA_UsuarioSecurity_Ins_NuevoUsuario 1, 'juan.perez', '$2a$12$...'
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioSecurity_Ins_NuevoUsuario(
    @nUsuarioId INT,
    @cUsername VARCHAR(100),
    @cPasswordHash VARCHAR(255)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            INSERT INTO UsuarioSecurity (nUsuarioId, cUsername, cPasswordHash)
            VALUES(@nUsuarioId, @cUsername, @cPasswordHash)
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK TRAN
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