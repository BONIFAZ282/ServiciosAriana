package microservice_cargo.Repository.StoredProcedure;

public class StoredProcedureC {
    /*PA'S PARA LOS CARGOS*/
    public static final String SEL_CARGO = "{call PA_Cargo_Sel_ListarCargo}";
    public static final String SEL_CARGO_CON_EMPLEADOS = "{call PA_Cargo_Sel_ListarConEmpleados}"; // NUEVO
    public static final String INS_CARGONUEVO = "{call PA_Cargo_Ins_NuevoCargo(?,?,?)}";
    public static final String UPD_ELIMARCARGOLOGICO = "{call PA_Cargo_Upd_EliminarCargo(?)}";
    public static final String UPD_MODIFICARCARGO = "{call PA_Cargo_Upd_ModificarCargo(?,?,?,?)}";
}