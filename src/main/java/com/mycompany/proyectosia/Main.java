package com.mycompany.famax.mavenproject1;

import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {

    // HashMap para clientes CLAVE: RUT - VALOR: OBJETO CLIENTE
    Map<String, Cliente> clientes = new HashMap<>();

    
    // ********* Clientes de Prueba *********
    Cliente clienteTest = new Cliente("Alberto Perez", "1", "alberto.perez@gmail.com", 94724901);
    PlanComun planTest1 = new PlanComun("Plan intermedio", 9000, 300, 2000, 600);
    PlanComun planTest2 = new PlanComun("Plan libre", 15500, 0, 0, 0);
    PlanComun planTest3 = new PlanComun("Plan libre", 15500, 0, 0, 0);
    PlanComun planTest4 = new PlanComun("Plan libre", 15500, 0, 0, 0);
    
    clienteTest.setPlan(planTest1);
    clienteTest.setPlan(planTest2);
    clienteTest.setPlan(planTest3);
    clienteTest.setPlan(planTest4);
    clientes.put("1", clienteTest);
    // ***************************************

    
    while(true){
      // MENU PRINCIPAL
      System.out.println("1 - Insercion Manual de Clientes y servicios");
      System.out.println("2 - Mostrar todos los servicios de un cliente ");
      System.out.println("3 - Cerrar sesión ");

      BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
      int opcion = Integer.parseInt(lector.readLine());

      switch(opcion){
        // INSERCION DE CLIENTES EN EL MAPA Y PLANES EN ARRAYLIST
        case 1:
          System.out.println("1 - Crear nuevo Cliente ");
          System.out.println("2 - Agregar servicio a usuario existente");
          
          int opcion2 = Integer.parseInt(lector.readLine());

          if(opcion2 == 1) {
            String nombre, rut, correo, telefonoStr;
            int telefono;
            System.out.println("nombre:");
            nombre = lector.readLine();
            System.out.println("rut:");
            rut = lector.readLine();
            System.out.println("correo:");
            correo = lector.readLine();
            System.out.println("telefono:");
            telefonoStr = lector.readLine();
            telefono = Integer.parseInt(telefonoStr);

            Cliente nuevoCliente = new Cliente(nombre, rut, correo, telefono);
            
            clientes.put(rut, nuevoCliente); // Agregar cliente al mapa
            System.out.println("Cliente creado con exito");
            
          } else if(opcion2 == 2) {
            while (true) {
              System.out.println("");
              System.out.println("Ingrese rut del cliente existente: ");
              System.out.println("(o 0 para volver al menú principal)");
              
              String rut = lector.readLine();
              if (rut.equals("0")){
                break;
              }
              Cliente clienteEncontrado = clientes.get(rut);
              if (clienteEncontrado != null) {
                System.out.println("Cliente encontrado.");
                System.out.println(" ");
                System.out.println("Planes disponibles:");
                System.out.println(" "); 
                System.out.println(" A) -----     PLAN BASICO: $7.000    -----");
                System.out.println("      100 GB - 1.000 MINUTOS - 400 SMS");
                System.out.println(" "); 
                System.out.println(" B) -----  PLAN INTERMEDIO: $ 9.000  -----");
                System.out.println("      300 GB - 2.000 MINUTOS - 600 SMS");
                System.out.println(" ");
                System.out.println(" C) -----   PLAN AVANZADO: $11.500   -----");
                System.out.println("      700 GB - MINUTOS LIBRES - 900 SMS");
                System.out.println(" ");
                System.out.println(" D) -----     PLAN LIBRE: $15.500    -----");
                System.out.println("   GB LIBRES - MINUTOS LIBRES - SMS LIBRES");
                System.out.println(" ");
                System.out.println("- Si es usuario nuevo, obtendrá un 5% de dcto. en su primer plan.");
                System.out.println("- Por cada 3 planes contratados, recibirá un 10% de dcto. acumulable en el total de su compra.");
                System.out.println(" ");
                System.out.println("Plan seleccionado: ");
                
                PlanComun plan = new PlanComun(null, 0.0, 0, 0, 0);

                while (true){
                  String opcionPlan = lector.readLine();
                  switch(opcionPlan){
                    case "a":
                    case "A":
                        plan.setNombre("Plan Basico");
                        plan.setPrecio(7000);
                        plan.setGigas(100);
                        plan.setMinutos(1000);
                        plan.setSms(400);
                      break;
                    case "b":
                    case "B":
                        plan.setNombre("Plan Intermedio");
                        plan.setPrecio(9000);
                        plan.setGigas(300);
                        plan.setMinutos(2000);
                        plan.setSms(600);
                      break;
                    case "c":
                    case "C":
                        plan.setNombre("Plan Avanzado");
                        plan.setPrecio(11500);
                        plan.setGigas(700);
                        plan.setMinutos(0);
                        plan.setSms(900);
                      break;
                    case "d":
                    case "D":
                        plan.setNombre("Plan Libre");
                        plan.setPrecio(15500);
                        plan.setGigas(0);
                        plan.setMinutos(0);
                        plan.setSms(0);
                      break;
                    default:
                      System.out.println("Opcion invalida, ingrese nuevamenteuna opción");
                      break;
                  }
                  double precioFinal;
                  int cantPlanes= clienteEncontrado.getListaPlanes().size() +1;

                  // testeo feo duermanlo !!!!!!!!!!!!!!!!!!!!!
                  System.out.println(cantPlanes);
                  
                  if (cantPlanes == 1) {
                    precioFinal = clienteEncontrado.descuento(plan.getPrecio());

                    System.out.println("¡Por ser usuario nuevo Usted ha recibido un descuento del 5% !");
                    System.out.println("Precio original: " + plan.getPrecio());
                    System.out.println("Precio con descuento: " + precioFinal);
                    plan.setPrecio(precioFinal); 
                } else {
                    precioFinal= clienteEncontrado.descuento(clienteEncontrado.getTotalSinDescuento(), cantPlanes);

                    System.out.println("¡Cada 3 planes tendrás un descuento del 10%, el cual se acumula! ");
                    System.out.println("Precio original: " + plan.getPrecio());
                    //System.out.println("Precio con descuento: " + precioFinal);
                }
  
                clienteEncontrado.setPlan(plan); // Agregar plan al arraylist del cliente
                
                double totalSinDesc = clienteEncontrado.getTotalSinDescuento();
                System.out.println("");
                System.out.println("MONTO TOTAL: " + totalSinDesc);
                System.out.println("El porcentaje de descuento total es :"+ clienteEncontrado.getDctoTotal());                
                double totalConDesc = clienteEncontrado.getTotalConDescuento();
                System.out.println("Total con Descuento :" + totalConDesc);
                
                break;
                }
              } 
              else {
                System.out.println(" Cliente no encontrado. Asegúrese de ingresar correctamente los datos.");
              }
            }
          }
          break;

        // MOSTRAR SERVICIOS DE UN CLIENTE
        case 2:
          while (true) {
            System.out.println("Ingrese rut del cliente existente: ");
            System.out.println("(o 0 para volver al menú principal)");
            
            String rut = lector.readLine();
            if (rut.equals("0")) {
              break;
            }
            Cliente clienteEncontrado = clientes.get(rut);
            if (clienteEncontrado != null) {
              System.out.println(" Cliente encontrado. Mostrando datos:");
              System.out.println("Nombre: " + clienteEncontrado.getNombre());
              System.out.println("Rut: " + clienteEncontrado.getRut());
              System.out.println("Correo: " + clienteEncontrado.getCorreo());
              System.out.println("Contacto: " + clienteEncontrado.getNumTelefono());
              System.out.println(" ");

              System.out.println("Servicios contratados: ");
              ArrayList<PlanComun> listaPlanes = clienteEncontrado.getListaPlanes();
              if (!listaPlanes.isEmpty()) {
                //double sumaPrecios = 0.0;
                for (int i = 0; i < listaPlanes.size(); i++) {
                  PlanComun plan = listaPlanes.get(i);

                  System.out.println("Nombre: " + plan.getNombre());
                  System.out.println("Precio: " + plan.getPrecio());
                  System.out.println("Gigas: " + plan.getGigas());
                  System.out.println("Minutos: " + plan.getMinutos());
                  System.out.println("SMS: " + plan.getSms());
                  System.out.println(" ");
                  
                }

                double totalSinDesc = clienteEncontrado.getTotalSinDescuento();
                System.out.println("MONTO TOTAL: " + totalSinDesc);

                System.out.println("El porcentaje de descuento total es :"+ clienteEncontrado.getDctoTotal());
                //double aPagarDesc = clienteEncontrado.descuento(aPagar, listaPlanes.size());
                //System.out.println("DESCUENTO APLICADO: " + clienteEncontrado.getDctoTotal());
                //System.out.println("MONTO FINAL: " + aPagarDesc);
                
                double totalConDesc = clienteEncontrado.getTotalConDescuento();
                System.out.println(" Total con Descuento :" + totalConDesc);
                        
              } else  {
                System.out.println("El cliente no posee planes contratados.");
              }
            }else {
              System.out.println(" Cliente no encontrado. Asegúrese de ingresar correctamente los datos.");
            }
          }
          break;
        // SALIDA DEL PROGRAMA
        case 3:
          System.exit(0);
          break;
      }
    }
  }
}
