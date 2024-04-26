package controllers;

public class PlanInexistenteException extends Exception {
    public PlanInexistenteException() {
        super("El plan ingresado no existe.");
    }
}