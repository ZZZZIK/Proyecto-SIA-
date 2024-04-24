package models;
import java.util.*;


public class Cliente {
  private String nombre;
  private String rut;
  private String correo;
  private int numTelefono;
  private double dctoTotal;
  private double totalSinDescuento;
  //private double totalConDescuento;
  private ArrayList<PlanComun> listaPlanes;

  

  // CONSTRUCTOR
  public Cliente(String nombre, String rut, String correo, int numTelefono) {
    this.nombre = nombre;
    this.rut = rut;
    this.correo = correo;
    this.numTelefono = numTelefono;
    this.dctoTotal = 0.0;
    this.totalSinDescuento = 0.0;
    //this.totalConDescuento = 0.0;
    listaPlanes = new ArrayList<PlanComun>();
  }

  // Descuento para cliente nuevo 5%
  public double descuento(double montoPrimerPlan){
    double precioDcto = montoPrimerPlan * 0.05;
    double precioFinal = montoPrimerPlan - precioDcto;
    this.dctoTotal = 0.05;
    return precioFinal;
  }

  // Descuento cliente del 10% del total que ya posee 3 planes o más 
  
  public double descuento(double totalApagar,int cant_planes){
      System.out.println("cant planes ; "+cant_planes);
      System.out.println("tot a pagar "+totalApagar);
    if (this.dctoTotal >= 0.3) {
      System.out.println("Limite del 30% alcanzado.");
      double precioFinal=totalApagar-(totalApagar*0.3);
      this.dctoTotal =0.3;
      return precioFinal; // No se aplica más descuento
    }
    int acumDcto = cant_planes / 3;

    double precioDescuento = totalSinDescuento * (0.1 * acumDcto);
    double precioFinal = totalSinDescuento - precioDescuento;
    this.dctoTotal = (0.1 * acumDcto);

    //guardamos el total a pagar en el cliente
    this.totalSinDescuento = precioFinal;
    return precioFinal;
  }

  
   //**************************************************************
  // METODOS SETTER
  
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
    
  public void setDctoTotal(double dctoTotal) {
    this.dctoTotal = dctoTotal;
  }

 
  // Setter de variable de instancia totalSinDescuento
  
  /*
  public void setTotalAPagar() {
    int cantPlanes = listaPlanes.size();
    double sumTotalAPagar=0;
    for (int i=0; i<cantPlanes;i++){
        PlanComun plan = listaPlanes.get(i);
        sumTotalAPagar += plan.getPrecio();
    }  
    totalSinDescuento = sumTotalAPagar;
  }
  
  public void setTotalConDescuento(double totalConDescuento){
     this.totalConDescuento=totalConDescuento;
  }
  */
  // SETTER DEL ARRAYLIST listaPlanes
  
  
  public void setPlan(PlanComun plan) {
      listaPlanes.add(plan);
  }
  
  
  //**************************************************************
  // METODOS GETTER
  
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

  public double getDctoTotal(){
      return dctoTotal;
  }
  
  /*
  public double getTotalSinDescuento(){
    setTotalAPagar();
    return totalSinDescuento;
  }
  

  public double getTotalConDescuento(){
    double totalConDescuento = descuento(getTotalSinDescuento(),listaPlanes.size());
    this.totalConDescuento=totalConDescuento;
    return totalConDescuento;
  }
  */
  
  
  public PlanComun getPlan(int i){
      PlanComun plan =(PlanComun)listaPlanes.get(i);
      return plan;
  }
  
  public ArrayList<PlanComun> getListaPlanes() {
    return listaPlanes;
  }
  
  public int getSizePlan(){
      return listaPlanes.size();
  }
  
  
  
}
