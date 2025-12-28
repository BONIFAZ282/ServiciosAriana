IF OBJECT_ID('PA_Rol_Ins_NuevoRol') IS NOT NULL
    DROP PROCEDURE PA_Rol_Ins_NuevoRol
GO
/*---------------------------------------------------------------------------------
PROPÓSITO         | Inserta un nuevo rol.
AUTOR            | Jorge Bonifaz
FECHA DE CREACIÓN   | 2025-01-01
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Rol_Ins_NuevoRol(
    @cNombreRol VARCHAR(50),
    @cDescripcionRol VARCHAR(200)
)
AS
BEGIN
    SET NOCOUNT ON
    BEGIN TRY
        BEGIN TRAN
            INSERT INTO Rol (cNombreRol, cDescripcionRol)
            VALUES(@cNombreRol, @cDescripcionRol)
        COMMIT TRAN
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0 ROLLBACK TRAN
        DECLARE @ErrorMessage NVARCHAR(4000), @ErrorSeverity INT, @ErrorState INT
        SELECT @ErrorMessage = ERROR_MESSAGE(), @ErrorSeverity = ERROR_SEVERITY(), @ErrorState = ERROR_STATE()
        RAISERROR(@ErrorMessage, @ErrorSeverity, @ErrorState)
    END CATCH
END