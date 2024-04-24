
package models;
import java.util.*;

public class GestorClientes {
    //Esta Clase se encargará de poner los clientes en su respectivo mapa
    
    
    private HashMap<String, Cliente> clientes;
    private ArrayList<String> clientesCopia;
    
    public GestorClientes() {
        clientes = new HashMap<>();
        clientesCopia= new ArrayList<String>();
    }
    
    public void eliminarCliente(String rut) {
        this.clientes.remove(rut);
    }
    
    public boolean agregarCliente(Cliente cliente) {
        //Añade cliente al mapa
        clientes.put(cliente.getRut(), cliente);
        
        
        //Añade rut cliente a la copia
        clientesCopia.add(cliente.getRut());

        /*Impresion por consola de los clientes registrados        
        for (int i=0;i<clientesCopia.size();i++){
            System.out.println(clientesCopia.get(i));
        }
        */
        
        return true;
    }
    
    public Cliente buscarCliente(String rut) {
        if (clientes.containsKey(rut)) {
            return clientes.get(rut);
        }
        else return null;
    }
    
    public Cliente getCliente(String rut) {
        return this.clientes.get(rut);
    }
    
    
}

   