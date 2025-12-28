IF OBJECT_ID('PA_UsuarioRol_Ins_AsignarRol') IS NOT NULL
    DROP PROCEDURE PA_UsuarioRol_Ins_AsignarRol
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Asigna un rol a un usuario.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_UsuarioRol_Ins_AsignarRol(
    @nUsuarioSecurityId INT,
    @nRolId INT
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            -- Verificar si ya existe la asignación
            IF NOT EXISTS(SELECT 1 FROM UsuarioRol WHERE nUsuarioSecurityId = @nUsuarioSecurityId AND nRolId = @nRolId)
            BEGIN
                INSERT INTO UsuarioRol (nUsuarioSecurityId, nRolId)
                VALUES(@nUsuarioSecurityId, @nRolId)
            END
            ELSE
            BEGIN
                -- Si existe pero está inactivo, reactivarlo
                UPDATE UsuarioRol
                SET bEstado = 1,
                    dFechaAsignacion = GETDATE()
                WHERE nUsuarioSecurityId = @nUsuarioSecurityId
                AND nRolId = @nRolId
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