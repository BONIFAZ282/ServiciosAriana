IF OBJECT_ID('PA_Cargo_Upd_EliminarCargo') IS NOT NULL
    DROP PROCEDURE PA_Cargo_Upd_EliminarCargo
GO
/*---------------------------------------------------------------------------------
PROPÓSITO			| Elimina el cargo a nivel lógico
AUTOR				| Jorge Bonifaz
FECHA DE CREACIÓN	| 2025-04-22
-----------------------------------------------------------------------------------

EJEMPLO:
	EXEC PA_Cargo_Upd_EliminarCargo 1
-----------------------------------------------------------------------------------*/

CREATE PROCEDURE PA_Cargo_Upd_EliminarCargo(
	@nCargoId		INT
)
AS
BEGIN
	SET NOCOUNT ON
	BEGIN TRY
		BEGIN TRAN

			UPDATE Cargo
			SET bEstado = 0
			WHERE nCargoId = @nCargoId


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