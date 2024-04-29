package models;
import java.util.*;
import controllers.*;
import java.io.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class GestorClientes {
    //Esta Clase se encargará de poner los clientes en su respectivo mapa
    private HashMap<String, Cliente> clientes;
    private ArrayList<Cliente> clientesConNPlanes; // ArrayList que posee los clientes filtrados
    private ArrayList<Cliente> clientesLista; // ArrayList que posee todos los clientes
    
    // Constructor 
    public GestorClientes() {
        clientes = new HashMap<>();
        clientesConNPlanes = new ArrayList<>();
        clientesLista = new ArrayList<>();
    }
    
    // ******************************Métodos Persistencia************************************************
    public void guardarDatos(){
        String persistencia = System.getProperty("user.dir") + "\\src\\main\\java\\Reporte.txt";
        
        try{
            BufferedReader lector = new BufferedReader(new FileReader(persistencia));  
            // Vaciamos el archivo persistencia
            BufferedWriter escritor = new BufferedWriter(new FileWriter(persistencia));
        
            //Obtenemos un arraylist con todos los ruts
            ArrayList<Cliente> lstClientesRut=new ArrayList<Cliente>(clientes.values());
            for(int i=0;i<lstClientesRut.size();i++){
                Cliente cliente= (Cliente) lstClientesRut.get(i);
               
                String nombre=cliente.getNombre();
                String rut = cliente.getRut();
                String correo= cliente.getCorreo();
                int num= cliente.getNumTelefono();
                boolean vip=cliente.getVIP();
                
                escritor.write("Cliente: ");
                escritor.newLine();
                escritor.write(nombre + ";" + rut + ";" + correo + ";" + num + ";" + vip);
                escritor.newLine();
                
                escritor.newLine();

                // Imprimir los planes del cliente
                escritor.write("Planes:");
                escritor.newLine();
                
                // Obtenemos e imprimimos los planes
                for(int j=0;j<cliente.getSizePlan();j++){
                    PlanComun plan= (PlanComun) cliente.getPlan(j);
                    escritor.write(plan.getNombre() + ";" + plan.getPrecio() + ";" + plan.getGigas() + ";" + plan.getMinutos() + ";" + plan.getSms() + ";" + plan.getRoaming());
                    escritor.newLine();
                }
                escritor.newLine();
            }
            escritor.close();
        }catch (FileNotFoundException e) {
            System.out.println("Error: no se encontró el archivo");
        }catch (IOException e) {
            System.out.println("Error al leer el archivo");
        }
    }

    public void cargaDatos(){
        String persistencia = System.getProperty("user.dir") + "\\src\\main\\java\\Reporte.txt";

        try {
            BufferedReader lector = new BufferedReader(new FileReader(persistencia));
            String linea;
            Cliente cliente = null; // Variable para mantener el cliente actual
            
            while ((linea = lector.readLine()) != null) {
                String[] partes = linea.split(";");
                
                //Si tiene 5 partes, es un cliente
                if (partes.length == 5) { 
                    String nombre = partes[0];
                    String rut = partes[1];
                    String correo = partes[2];
                    String telefono = partes[3];
                    boolean vip = Boolean.parseBoolean(partes[4]);
                    int num = Integer.parseInt(telefono);
                    
                    // Crear un nuevo cliente y agregarlo al HashMap
                    if (vip) {
                        cliente = new ClienteVIP(nombre, rut, correo, num);
                        ((ClienteVIP) cliente).setVIP(true); // Marcar como VIP
                    } else {
                        cliente = new Cliente(nombre, rut, correo, num);
                    }
                    clientes.put(rut, cliente);

                // Si tiene 6 partes, es un plan
                } else if (partes.length == 6) {
                    if (cliente != null) { 
                        String nombrePlan = partes[0];
                        double precio = Double.parseDouble(partes[1]);
                        int gigas=Integer.parseInt(partes[2]);
                        int minutos=Integer.parseInt(partes[3]);
                        int sms=Integer.parseInt(partes[4]);
                        int roaming=Integer.parseInt(partes[5]);
                        String primerDesc="";
                        
                        // Crear un nuevo objeto Plan y asociarlo al cliente actual
                        if(cliente.getSizePlan()==0){
                            primerDesc="Descuento de 5%";
                        }
                        PlanComun plan = new PlanComun(nombrePlan, precio, gigas, minutos, sms, roaming,primerDesc);
                        cliente.setPlan(plan);
                    }
                }
            }
            lector.close();
            }catch (FileNotFoundException e) {
                System.out.println("Error: no se encontró el archivo");
            }catch (IOException e) {
                System.out.println("Error al leer el archivo");
            }
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
    public PlanComun obtenerPlanPorNombre(String rut,String nombrePlan)throws ClienteInexistenteException, PlanInexistenteException{
        Cliente cliente = buscarCliente(rut);
        ArrayList<PlanComun> planLista = cliente.getListaPlanes();
        for (int i = 0; i < planLista.size(); i++) {
            PlanComun plan = planLista.get(i);
            if(plan.getNombre().equals(nombrePlan)){
                return plan;
            }
        }
        throw new PlanInexistenteException();
    }
    
    public void modMinutosPlan(PlanComun plan, int newMinutos) {
        plan.setMinutos(newMinutos);
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
    por el usuario. */
    public Cliente obtenerClientesPorNPlanes(int cantPlanesMin, int i) {
        clientesConNPlanes.clear(); //para limpiar la lista cada vez que se ejecute este metodo
        
        ArrayList<Cliente> listaClientes = new ArrayList<>(clientes.values()); //pasar coleccion de clientes del mapa a lista
        Cliente cliente = listaClientes.get(i);
        if (cliente.getSizePlan() == cantPlanesMin) {
            return cliente;
        }
        return null;
    }
    
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
    
    public static PlanComunConOferta aplicarDescuento(PlanComun planComun, double descuento) {
        PlanComunConOferta planConDescuento = new PlanComunConOferta(planComun.getNombre(),planComun.getPrecio(),planComun.getGigas(),planComun.getMinutos(),planComun.getSms(),planComun.getRoaming(),planComun.getPrimerDesc());
        planConDescuento.setPrecio(descuento);
        return planConDescuento;
    }
}