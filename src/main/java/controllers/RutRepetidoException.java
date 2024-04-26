package controllers;

public class RutRepetidoException extends Exception {
    public RutRepetidoException() {
        super("El RUT ingresado ya existe, intente con otro.");
    }
}