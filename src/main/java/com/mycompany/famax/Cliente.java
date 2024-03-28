package com.mycompany.famax;


public class Cliente {
    private String nombre;
    private String rut;
    private String correo;
    private int numTelefono;

  // CONSTRUCTOR
    public Cliente (String nombre, String rut, String correo, int numTelefono) {
        this.nombre = nombre;
        this.rut = rut;
        this.correo = correo;
        this.numTelefono = numTelefono;
    }

    // METODOS SETTER - GETTER
    public void set_nombre(String nombre) {
        this.nombre = nombre;
    }
    public void set_rut(String rut) {
        this.rut = rut;
    }
    public void set_correo(String correo) {
        this.correo = correo;
    }
    public void set_numTelefono(int numTelefono) {
        this.numTelefono = numTelefono;
    }

    
    
    public String get_nombre() {
        return nombre;
    }
    public String get_rut() {
        return rut;
    }
    public String get_correo(){
        return correo;
    }
    public int get_numTelefono() {
        return numTelefono;
    }
}