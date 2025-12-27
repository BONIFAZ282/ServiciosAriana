package microservice_cargo.Repository.Translator;

import microservice_cargo.Model.Cargo;

public class CargoTranslator {

    private int idcharge;
    private String nameCargue;
    private String descriptionCargue;
    private double salary;
    private boolean stateCargue;
    private int cantidadEmpleados; // NUEVO

    public void setIdcharge(int charge) { this.idcharge = charge; }
    public void setNameCargue(String nameCargue) { this.nameCargue = nameCargue; }
    public void setDescriptionCargue(String descriptionCargue) { this.descriptionCargue = descriptionCargue; }
    public void setStateCargue(boolean stateCargue) { this.stateCargue = stateCargue; }
    public void setSalary(double salary) { this.salary = salary; }
    public void setCantidadEmpleados(int cantidadEmpleados) { this.cantidadEmpleados = cantidadEmpleados; } // NUEVO

    public Cargo toCargoDTO() {
        Cargo cargo = new Cargo();
        cargo.setIdcharge(this.idcharge);
        cargo.setNameCargue(this.nameCargue);
        cargo.setDescriptioncargue(this.descriptionCargue);
        cargo.setSalary(this.salary);
        cargo.setStateCargue(this.stateCargue);
        cargo.setCantidadEmpleados(this.cantidadEmpleados); // NUEVO
        return cargo;
    }
}