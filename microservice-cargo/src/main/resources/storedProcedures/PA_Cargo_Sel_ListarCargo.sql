IF OBJECT_ID('PA_Cargo_Sel_ListarCargo') IS NOT NULL
    DROP PROCEDURE PA_Cargo_Sel_ListarCargo
GO
/*---------------------------------------------------------------------------------
PROPÓSITO			| Lista todos los cargos sin excepcion del bEstado
AUTOR				| Jorge Bonifaz
FECHA DE CREACIÓN	| 2025-04-22
-----------------------------------------------------------------------------------

EJEMPLO:
	EXEC PA_Cargo_Sel_ListarCargo
--------------------------------------------------- --------------------------------*/

CREATE PROCEDURE PA_Cargo_Sel_ListarCargo
AS
BEGIN
	SET NOCOUNT ON
	BEGIN TRY
		BEGIN TRAN

			SELECT T1.nCargoId, T1.cNombreCargo, T1.cDescripcion, T1.nSueldo, T1.bEstado
			FROM Cargo T1 WITH(NOLOCK)


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