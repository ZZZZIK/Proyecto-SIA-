package controllers;

public class ClienteInexistenteException extends Exception {
    public ClienteInexistenteException() {
        super("El cliente ingresado no existe.");
    }
}
