
package models;

public class PlanComunConOferta extends PlanComun {
    private double oferta;

    public PlanComunConOferta(String nombre, double precio, int gigas, int minutos, int sms, int roaming,String primerDesc) {
        super(nombre, precio, gigas, minutos, sms, roaming,primerDesc);
        
    }

    @Override
    public void setPrecio(double descuento) {
        this.oferta = descuento;
        double precioConDescuento = getPrecio() * (1 - (descuento/100));
        super.setPrecio(precioConDescuento);
    }

    public double getOferta() {
        return oferta;
    }

    public void setOferta(int oferta) {
        this.oferta = oferta;
    }
    
    // ----- MODIFICAR UN ELEMENTO ----- (oferta)
    public static PlanComunConOferta aplicarDescuento(PlanComun planComun, double descuento) {
        PlanComunConOferta planConDescuento = new PlanComunConOferta(planComun.getNombre(),planComun.getPrecio(),planComun.getGigas(),planComun.getMinutos(),planComun.getSms(),planComun.getRoaming(),planComun.getPrimerDesc());
        planConDescuento.setPrecio(descuento);
        return planConDescuento;
    }

    // en el main se ocuparia asi: se crea un planComun plan y se guarda este en el cliente: PlanComunConOferta planConDescuento = aplicarDescuento(plan, 50);
}
