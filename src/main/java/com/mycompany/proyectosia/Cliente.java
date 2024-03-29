import java.util.*;

public class Cliente {
  private String nombre;
  private String rut;
  private String correo;
  private int numTelefono;
  private ArrayList<PlanComun> listaPlanes;
  
  
  // CONSTRUCTOR
  public Cliente(String nombre, String rut, String correo, int numTelefono) {
    this.nombre = nombre;
    this.rut = rut;
    this.correo = correo;
    this.numTelefono = numTelefono;
    this.listaPlanes = new ArrayList<PlanComun>();
  }

  // Sobrecarga de método descuento
  
  // Descuento para cliente nuevo 5%
  public double descuento(double precioOriginal){
    double precioDcto = precioOriginal * 0.05;
    double precioFinal = precioOriginal - precioDcto;
    return precioFinal;
  }

  // Descuento cliente que ya posee 3 planes del 15%
  public double descuento(double precioOriginal,int cant_planes){
    int acumDcto = cant_planes / 3;
    double precioDescuento = precioOriginal * (0.05 * acumDcto);
    double precioFinal = precioOriginal - precioDescuento;
    return precioFinal;
  }

  
  // METODOS SETTER - GETTER
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setRut(String rut) {
    this.rut = rut;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public void setNumTelefono(int numTelefono) {
    this.numTelefono = numTelefono;
  }

  public void setPlan(PlanComun plan) {
      listaPlanes.add(plan);
  }

  public String getNombre() {
    return nombre;
  }

  public String getRut() {
    return rut;
  }

  public String getCorreo() {
    return correo;
  }

  public int getNumTelefono() {
    return numTelefono;
  }

  public ArrayList<PlanComun> getListaPlanes() {
    return listaPlanes;
  }
}