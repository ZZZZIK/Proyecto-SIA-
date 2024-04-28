package models;
public class ClienteVIP extends Cliente {
    public ClienteVIP(String nombre, String rut, String correo, int numTelefono) {
        super(nombre, rut, correo, numTelefono);
        //setVip(true);
    }

    //@Override
    public double getTotalConDescuento() {
        //double descuentoEspecial = totalConDescuento * 0.15; // Descuento especial del 15% en el total de su compra
        //return totalConDescuento - descuentoEspecial;
        
        
        double totalConDescuento = descuento(getTotalSinDescuento(),getSizePlan());
        double descuentoEspecial =totalConDescuento*0.15; // Descuento especial del 15% en el total de su compra
        
        
        return totalConDescuento-descuentoEspecial;
    }
}