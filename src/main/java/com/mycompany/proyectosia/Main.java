
import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {

    // HashMap para clientes CLAVE: RUT - VALOR: OBJETO CLIENTE
    Map<String, Cliente> clientes = new HashMap<>();

    // 
    // ********* Clientes de Prueba *********
    //Creación de cliente
    Cliente clienteTest = new Cliente("Alberto Perez", "11736497-3", "alberto.perez@gmail.com", 94724901);
    //Creación de planes de testeo 
    PlanComun planTest1 = new PlanComun("Plan intermedio", 9000, 300, 2000, 600, 500);
    PlanComun planTest2 = new PlanComun("Plan libre", 15500, 0, 0, 0, 0);
    PlanComun planTest3 = new PlanComun("Plan libre", 15500, 0, 0, 0, 0);
    PlanComun planTest4 = new PlanComun("Plan libre", 15500, 0, 0, 0, 0);

    //Añadimos los planes al clientes
    clienteTest.setPlan(planTest1);
    clienteTest.setPlan(planTest2);
    clienteTest.setPlan(planTest3);
    clienteTest.setPlan(planTest4);
    
    //Añadimos el cliente test al mapa de clientes 
    clientes.put("11736497-3", clienteTest);
    
    // *******************************************************************************
    
    //Creación de cliente
    Cliente clienteTest2 = new Cliente("Juana Maria", "15883520-3", "juanamaria19@gmail.com", 921447569);
    //Creación de planes de testeo
    PlanComun planTest5 = new PlanComun("Plan basico", 7000, 10, 1000, 400, 0);
    PlanComun planTest6 = new PlanComun("Plan libre", 15500, 0, 0, 0, 0);
    
    //Añadimos los planes al clientes
    clienteTest2.setPlan(planTest5);
    clienteTest2.setPlan(planTest6);
    
    //Añadimos el cliente test al mapa de clientes 
    clientes.put("15883520-3", clienteTest2);
    
    // ********************************************************************************
    
    //Creación de cliente
    Cliente clienteTest3 = new Cliente("Jose Luis", "1", "joseluis@gmail.com", 986234418);
    PlanComun planTest7 = new PlanComun("Plan libre", 15500, 0, 0, 0, 0);
    PlanComun planTest8 = new PlanComun("Plan basico", 7000, 10, 1000, 400, 0);
    
    //Añadimos los planes al clientes
    clienteTest3.setPlan(planTest7);
    clienteTest3.setPlan(planTest8);
  
    //Añadimos el cliente test al mapa de clientes 
    clientes.put("1", clienteTest3);
    
    // *********************************************************************************

    while(true){
      // MENU PRINCIPAL
      System.out.println("1 - Insercion Manual de Clientes y servicios");
      System.out.println("2 - Mostrar todos los servicios de un cliente ");
      System.out.println("3 - Cerrar sesión ");

      BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
      int opcion = Integer.parseInt(lector.readLine());
      System.out.println(" ");

      switch(opcion){
        // ----- INSERCION DE CLIENTES EN MAPA Y PLANES (+ ROAMING) EN ARRAYLIST -----
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
              System.out.println("(*) '0' para volver al menú principal");

              String rut = lector.readLine();
              if (rut.equals("0")){
                break;
              }
              Cliente clienteEncontrado = clientes.get(rut);
              if (clienteEncontrado != null) {
                System.out.println("Cliente encontrado.");
                System.out.println("");
                System.out.println("[SERVICIOS DISPONIBLES: AGREGAR PLANES, ASIGNAR BOLSAS ROAMING]");
                System.out.println(" "); 
                System.out.println("Planes disponibles:");
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
                System.out.println("(*) Si es usuario nuevo, obtendrá un 5% de dcto. en el total de su primer plan.");
                System.out.println("(*) Por cada 3 planes contratados, recibirá un 10% de dcto. acumulable en el total de su compra. (con tope del 30%)");
                System.out.println(" ");

                // --- Asignacion de plan ---
                PlanComun plan = new PlanComun(null, 0.0, 0, 0, 0, 0); // Instanciar plan vacío
                
                while (true){
                  boolean planValido = false;
                  System.out.println("Plan seleccionado: ");
                  String opcionPlan = lector.readLine();
                  switch(opcionPlan){
                    case "a":
                    case "A":
                        plan.setNombre("Plan Basico");
                        plan.setPrecio(7000);
                        plan.setGigas(100);
                        plan.setMinutos(1000);
                        plan.setSms(400);
                        plan.setRoaming(0);
                        planValido = true;
                      break;
                    case "b":
                    case "B":
                        plan.setNombre("Plan Intermedio");
                        plan.setPrecio(9000);
                        plan.setGigas(300);
                        plan.setMinutos(2000);
                        plan.setSms(600);
                        plan.setRoaming(0);
                        planValido = true;
                      break;
                    case "c":
                    case "C":
                        plan.setNombre("Plan Avanzado");
                        plan.setPrecio(11500);
                        plan.setGigas(700);
                        plan.setMinutos(0);
                        plan.setSms(900);
                        plan.setRoaming(0);
                        planValido = true;
                      break;
                    case "d":
                    case "D":
                        plan.setNombre("Plan Libre");
                        plan.setPrecio(15500);
                        plan.setGigas(0);
                        plan.setMinutos(0);
                        plan.setSms(0);
                        plan.establecerRoaming();
                        planValido = true;
                      break;
                    default:
                      System.out.println("Opcion invalida, ingrese nuevamenteuna opción");
                      break;
                  }
                  // --- Asignacion de roaming ---
                  int mb;
                  while(planValido){
                    System.out.println("Bolsas de Roaming disponibles:");
                    System.out.println(" "); 
                    System.out.println(" -----    50 MB: $2.990   -----");
                    System.out.println(" -----   100 MB: $4.990   -----");
                    System.out.println(" -----   200 MB: $7.990   -----");
                    System.out.println(" ");
                    System.out.println("Desea agregar bolsa de roaming al plan seleccionado? (s/n): ");
                    String opcionRoaming = lector.readLine();

                    //validador opcion valida de S o N
                    while(!opcionRoaming.equals("s") && !opcionRoaming.equals("S") && !opcionRoaming.equals("n") && !opcionRoaming.equals("N")) {
                      System.out.println("Opcion invalida, ingrese nuevamente una opción");
                      System.out.println("Desea agregar bolsa de roaming al plan seleccionado? (s/n): ");
                      opcionRoaming = lector.readLine();
                    }
                    
                    switch(opcionRoaming){
                      case "s":
                      case "S":
                        System.out.println(" ");
                        System.out.println("Ingrese cantidad de MB de bolsa de roaming: ");
                        mb = Integer.parseInt(lector.readLine());
                        while (true){
                          if (plan.establecerRoaming(mb)){
                            double totalPlanSinRoaming = plan.getPrecio();
                            double totalPlanConRoaming = plan.getPrecioRoaming() + totalPlanSinRoaming;
                            plan.setPrecio(totalPlanConRoaming); // actualizacion precio del plan con roaming incluido
                            break;
                          }
                          else{
                            System.out.println(" ");
                            System.out.println("Cantidad de MB invalida, ingrese nuevamente: ");
                            System.out.println("Bolsas de Roaming disponibles:");
                            System.out.println(" "); 
                            System.out.println(" -----    50 MB: $2.990   -----");
                            System.out.println(" -----   100 MB: $4.990   -----");
                            System.out.println(" -----   200 MB: $7.990   -----");
                            System.out.println(" ");
                            System.out.println("Ingrese cantidad de MB de bolsa de roaming: ");
                            mb = Integer.parseInt(lector.readLine());
                          }
                        }
                        break;
                      case "n":
                      case "N":
                        System.out.println(" ");
                        System.out.println("(No se ha seleccionado bolsa de roaming, volviendo a menu principal...)");
                        System.out.println(" ");
                        break;
                    }

                  // --- Definir y asignar descuento ---
                  double precioFinal=0.0;
                  int cantPlanes= clienteEncontrado.getListaPlanes().size() +1;

                  clienteEncontrado.setPlan(plan); // Agregar plan al arraylist del cliente
                  
                  if (cantPlanes == 1) {
                    precioFinal = clienteEncontrado.descuento(plan.getPrecio());
                    System.out.println(" ");
                    System.out.println("¡Por ser usuario nuevo Usted ha recibido un descuento del 5% !");
                    System.out.println("Precio original: " + plan.getPrecio());
                    System.out.println("Precio con descuento: " + precioFinal);
                    plan.setPrecio(precioFinal); 
                  } else if (cantPlanes >= 3){
                    
                    //precioFinal= clienteEncontrado.descuento(clienteEncontrado.getTotalSinDescuento(),cantPlanes);
                    System.out.println(" ");
                    System.out.println("¡Cada 3 planes tendrás un descuento del 10%, el cual se acumula! (tope del 30%)");
                    System.out.println("Precio original: " + plan.getPrecio());
                    //System.out.println("Precio del plan con descuento: " + precioFinal);
                  }
                  
                  //clienteEncontrado.setPlan(plan); // Agregar plan al arraylist del cliente

                  // --- Mostrar carrito de compras ---
                  double totalSinDesc = clienteEncontrado.getTotalSinDescuento();
                  System.out.println("");
                  System.out.println("MONTO TOTAL: " + totalSinDesc);

                  //Llamamos a la funcion que obtiene el descuento total y tambien ayuda para obtener el %.
                  double totalConDesc = clienteEncontrado.getTotalConDescuento();
                  
                  
                  System.out.println("DESCUENTO TOTAL: "+ (clienteEncontrado.getDctoTotal()) * 100 + "%");
                  System.out.println("MONTO FINAL: " + totalConDesc);
                  System.out.println("");
                  break;  
                  } // while roaming
                break;
                } // Opcion planes
              }
              else {
                System.out.println("Cliente no encontrado. Asegúrese de ingresar correctamente los datos.");
              }
            } // Menu principal
          }
          break;
        // ----- MOSTRAR SERVICIOS DE UN CLIENTE -----
        case 2:
          while (true) {
            System.out.println("Ingrese rut del cliente existente: ");
            System.out.println("(*) '0' para volver al menú principal");

            String rut = lector.readLine();
            if (rut.equals("0")) {
              break;
            }
            Cliente clienteEncontrado = clientes.get(rut);
            if (clienteEncontrado != null) {
              System.out.println("");
              System.out.println(" Cliente encontrado. Mostrando datos:");
              System.out.println("Nombre: " + clienteEncontrado.getNombre());
              System.out.println("Rut: " + clienteEncontrado.getRut());
              System.out.println("Correo: " + clienteEncontrado.getCorreo());
              System.out.println("Contacto: " + clienteEncontrado.getNumTelefono());
              System.out.println(" ");

              System.out.println("Servicios contratados: ");
              ArrayList<PlanComun> listaPlanes = clienteEncontrado.getListaPlanes();
              if (!listaPlanes.isEmpty()) {
                for (int i = 0; i < listaPlanes.size(); i++) {
                  PlanComun plan = listaPlanes.get(i);
                  System.out.println("Nombre: " + plan.getNombre());
                  System.out.println("Precio: " + plan.getPrecio());
                  
                  System.out.print("Gigas: ");
                  if (plan.getGigas() == 0){
                    System.out.println("ilimitados");
                  }else{
                     System.out.println(plan.getGigas());
                  }
                  
                  System.out.print("Minutos: ");
                  if (plan.getMinutos() == 0){
                    System.out.println("ilimitado");
                    
                  }else{
                     System.out.println(plan.getMinutos());
                  }
                  
                  System.out.print("SMS: ");
                  if (plan.getMinutos() == 0){
                    System.out.println("ilimitado");
                  }else{
                    System.out.println(plan.getMinutos());
                  }
                  
                  System.out.println("MB Roaming: " + plan.getRoaming());
                  System.out.println(" ");
                }
                
                double totalSinDesc = clienteEncontrado.getTotalSinDescuento();
                System.out.println("Total a pagar:");
                System.out.println("MONTO TOTAL: " + totalSinDesc);
                System.out.println("DESCUENTO TOTAL: "+ (clienteEncontrado.getDctoTotal()) * 100 + "%");
                double totalConDesc = clienteEncontrado.getTotalConDescuento();
                System.out.println("MONTO FINAL: " + totalConDesc);
                System.out.println(" ");
              } else  {
                System.out.println("El cliente no posee planes contratados.");
              }
            }else {
              System.out.println("Cliente no encontrado. Asegúrese de ingresar correctamente los datos.");
            }
          }
          break;
        // ----- SALIDA DEL PROGRAMA -----
        case 3:
          System.exit(0);
          break;
      }
    }
  }
}