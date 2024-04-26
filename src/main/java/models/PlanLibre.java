package models;

public class PlanLibre extends PlanComun {
    public PlanLibre() {
        super("Plan Libre", 1000000, 1000000, 1000000, 1000000, 0); // constructor de la clase padre PlanComun
        establecerRoaming();
    }
}