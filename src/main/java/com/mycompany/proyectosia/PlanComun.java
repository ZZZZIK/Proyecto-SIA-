public class PlanComun {

  private String nombre;
  private double precio;
  private int gigas;
  private int minutos;
  private int sms;

  // CONSTRUCTOR
  public PlanComun(String nombre, double precio, int gigas, int minutos, int sms) {
    this.nombre = nombre;
    this.precio = precio;
    this.gigas = gigas;
    this.minutos = minutos;
    this.sms = sms;
  }
  
  // METODOS
  public double calcularCostoTotal() {
    return precio;
  }

  public double calcularCostoTotal(int smsUsados) {
    double costoTotal = calcularCostoTotal();
    int costoAdicional= 50;
    
    if (sms < smsUsados) {
      costoTotal += (smsUsados - sms) * costoAdicional;
    }
    return costoTotal;
  }
  
  // METODOS SETTER - GETTER
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
}


