
package models;
import controllers.*;
public class PlanComun {
  private String nombre;
  private double precio;
  private int gigas;
  private int minutos;
  private int sms;
  private int roaming;
  private String primerDesc;
  
    

  // CONSTRUCTOR
  public PlanComun(String nombre, double precio, int gigas, int minutos, int sms, int roaming, String primerDesc) {
    this.nombre = nombre;
    this.precio = precio;
    this.gigas = gigas;
    this.minutos = minutos;
    this.sms = sms;
    this.roaming = roaming;
    this.primerDesc=primerDesc;
  }

  // METODOS
  //caso 1, roaming libre , usuario tiene roaming ilimitado y no tiene que ingresar nada
  public void establecerRoaming() {
    if (nombre == "Plan Libre") {
      roaming = 1000000;
    }
    else {
      System.out.println("Roaming no disponible para este plan.");
    }
  }
  // Caso 2, roaming no libre, usuario debe decir cuantos mb quiere 
  public boolean establecerRoaming(int mb) throws RoamingException{
    if (mb == 50 || mb == 100 || mb == 200 || mb ==0) {
      roaming += mb;
      setPrecioRoaming();
      System.out.println("Roaming establecido correctamente");
      return true;
    } else {
        throw new RoamingException();
    }
  }

  public void setPrecioRoaming() {
    if (roaming == 50) {
      precio+= 2990.0;
    }
    else if (roaming == 100) {
      precio+= 4990.0;
    }
    else if (roaming == 200) {
      precio+= 7990.0;
    }
    else {
      precio+= 0.0;
    }
  }

  // METODOS SETTER
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public void setPrecio(double precio) {
    this.precio = precio;
  }
  public void setGigas(int gigas) {
    this.gigas = gigas;
  }
  public void setMinutos(int minutos) {
    this.minutos = minutos;
  }
  public void setSms(int sms) {
    this.sms = sms;
  }
  public void setRoaming(int roaming) {
    this.roaming = roaming;
  }

  // METODOS GETTER
  public String getNombre() {
    return nombre;
  }
  public double getPrecio() {
    return precio;
  }
  public int getGigas() {
    return gigas;
  }
  public int getMinutos() {
    return minutos;
  }
  public int getSms() {
    return sms;
  }
  public int getRoaming() {
    return roaming;
  }
  public String getPrimerDesc(){
    return primerDesc;
  }
}

