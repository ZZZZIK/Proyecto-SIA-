package models;
import java.util.*;
import controllers.*;

public class GestorClientes {
    //Esta Clase se encargará de poner los clientes en su respectivo mapa
    private HashMap<String, Cliente> clientes;
    // ArrayList que posee los clientes filtrados
    private ArrayList<Cliente> clientesConNPlanes;
    
    // ArrayList que posee todos los clientes
    private ArrayList<Cliente> clientesLista;
    
    
    // Constructor 
    public GestorClientes() {
        clientes = new HashMap<>();
        clientesConNPlanes = new ArrayList<>();
        clientesLista = new ArrayList<>();
        
    }
    
    
    
    // ******************************Métodos Clientes************************************************
    public Boolean agregarCliente(Cliente cliente) throws RutRepetidoException {
        if (!clientes.containsKey(cliente.getRut())) {
            clientes.put(cliente.getRut(), cliente);
            
            return true;
        } else {
            throw new RutRepetidoException();
            
        }
    }
    
    public Cliente buscarCliente(String rut) throws ClienteInexistenteException {
        if (clientes.containsKey(rut)) {
            return clientes.get(rut);
        } else {
            throw new ClienteInexistenteException();
        }
    }
    
    public void eliminarCliente(String rut) throws ClienteInexistenteException {
        if (clientes.containsKey(rut)) {
            clientes.remove(rut);
        } else {
            throw new ClienteInexistenteException();
        }
    }
    
    
    //******************************Métodos Planes************************************************
    
    public void agregarPlan(String rut, PlanComun plan) throws ClienteInexistenteException {
        Cliente cliente = buscarCliente(rut);
        cliente.setPlan(plan);
    }
    
    public boolean buscarPlanPorNombre(String rut, String nombrePlan) throws ClienteInexistenteException, PlanInexistenteException {
        Cliente cliente = buscarCliente(rut);
        ArrayList<PlanComun> planLista = cliente.getListaPlanes();
        for (int i = 0; i < planLista.size(); i++) {
            PlanComun plan = planLista.get(i);
            if (plan.getNombre().equals(nombrePlan)) {
                return true;
            }
        }
        throw new PlanInexistenteException();
    }
    
    public void eliminarPlanDeCliente(String rut, String nombrePlan) throws ClienteInexistenteException, PlanInexistenteException {
        Cliente cliente = buscarCliente(rut);
        boolean planEncontrado = false;
        ArrayList<PlanComun> planLista = cliente.getListaPlanes();
        for (int i = 0; i < planLista.size(); i++) {
            PlanComun plan = planLista.get(i);
            if (plan.getNombre().equals(nombrePlan)) {
                planEncontrado = true;
                planLista.remove(i);
                break;
            }
        }
        if (!planEncontrado) {
            throw new PlanInexistenteException();
        }
    }
    /*
    ----- SUBCONJUNTO FILTRADO POR CRITERIO -----
    // Obtiene una lista de todos los clientes con cantidad de planes planes == numero ingresado 
    por el usuario.
    */
    
    public ArrayList obtenerClientesPorNPlanes(int cantPlanesMin) {
        clientesConNPlanes.clear(); // para limpiar la lista cada vez que se ejecute este metodo

        ArrayList<Cliente> listaClientes = new ArrayList<>(clientes.values()); // pasar coleccion de clientes del mapa a lista
        for (int i = 0; i < listaClientes.size(); i++) {
            Cliente cliente = listaClientes.get(i);
            
            if (cliente.getSizePlan() < cantPlanesMin) {
                clientesConNPlanes.add(cliente);
            }
        }
        return clientesConNPlanes; // posible error: LISTA VACÍA. ***aplicar condicion en donde se vaya a utilizar: if(listaRetornada.isEmpty()){ ... }
    }
    
    /*
    public ArrayList actualizarListaClientes() {
        clientesLista.clear();
        clientesLista.addAll(clientes.values());
        return clientesLista;
    }
    */
    
    public void actualizarListaClientes() {
        ArrayList aux = new ArrayList<>(clientes.values());
        if(clientesLista.size() != aux.size()) {
            clientesLista.clear();
            clientesLista.addAll(clientes.values());
        }
    }
    
    public Cliente posicionClienteLista(int i) {
        actualizarListaClientes();
        return clientesLista.get(i);
    }

    
    public int largolst(){
        return clientes.values().size();
    }
    
    
    
}
    
    
    
    
    
    
    
    
 

   