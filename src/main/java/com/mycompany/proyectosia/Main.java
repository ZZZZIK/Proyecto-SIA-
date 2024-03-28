import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {

    // HashMap PARA CLIENTES
    Map<String, Cliente> clientes = new HashMap<>();
    
    
    // Creación de clientes de Prueba e insercion en el mapa
    Cliente clienteTest = new Cliente("Alberto Perez", "12483746-0", "alberto.perez@gmail.com", 94724901);
    
    PlanComun planTest1 = new PlanComun("Plan intermedio", 9000, 300, 2000, 600);
    PlanComun planTest2 = new PlanComun("Plan libre", 15500, 0, 0, 0);
    
    clienteTest.setPlan(planTest1);
    clienteTest.setPlan(planTest2);
    
    clientes.put("12483746-0", clienteTest);

    while(true){
      //1) Inserción Manual / agregar elemento
      //2) Mostrar por pantalla listado de elementos. Esto para la 2ª colección de objetos (colección anidada)

      //Listado de opciones
      System.out.println("1 - Insercion Manual de Clientes y servicios");
      //System.out.println("2 - Mostrar todos los servicios de un cliente ");
      System.out.println("3 - Cerrar sesión ");
      
      // Creación del objeto lector
      BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
      int opcion = Integer.parseInt(lector.readLine());
      
      switch(opcion){
        // Insercion de clientes y servicios
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
            // agregar cliente al mapa
            clientes.put(rut, nuevoCliente);
            System.out.println("Cliente creado con exito");
            
          } else if(opcion2 == 2) {

            while (true) {
              System.out.println("Ingrese rut del cliente existente: ");
              System.out.println("(o 0 para volver al menú principal)");
              
              String rut = lector.readLine();
              if (rut.equals("0")){
                break;
              }
              Cliente clienteEncontrado = clientes.get(rut);
              if (clienteEncontrado != null) {
                System.out.println(" Cliente encontrado.");
                System.out.println("");
                
                System.out.println("Planes disponibles:");
                System.out.println(" "); 
                System.out.println(" A) ░░░░░     PLAN BASICO: $7.000    ░░░░░");
                System.out.println("      100 GB - 1.000 MINUTOS - 400 SMS");
                System.out.println(" "); 
                System.out.println(" B) ░░░░░  PLAN INTERMEDIO: $ 9.000  ░░░░░");
                System.out.println("      300 GB - 2.000 MINUTOS - 600 SMS");
                System.out.println(" ");
                System.out.println(" C) ░░░░░   PLAN AVANZADO: $11.500   ░░░░░");
                System.out.println("      700 GB - MINUTOS LIBRES - 900 SMS");
                System.out.println(" ");
                System.out.println(" D) ░░░░░  ☆ PLAN LIBRE: $15.500 ☆  ░░░░░");
                System.out.println("                (más vendido)");
                System.out.println("   GB LIBRES - MINUTOS LIBRES - SMS LIBRES");
                System.out.println(" "); 
                System.out.println("Plan seleccionado: ");

                while (true){
                  String opcionPlan = lector.readLine();
                  PlanComun plan = new PlanComun("", 0.0, 0, 0, 0);
                  switch(opcionPlan){
                    case "a":
                    case "A":
                      //PlanComun plan = new PlanComun("Plan Basico", 7000, 100, 1000, 400);
                        plan.setNombre("Plan Basico");
                        plan.setPrecio(7000);
                        plan.setGigas(100);
                        plan.setMinutos(1000);
                        plan.setSms(400);
                      break;
                    case "b":
                    case "B":
                      //PlanComun plan = new PlanComun("Plan Intermedio", 9000, 300, 2000, 600);
                        plan.setNombre("Plan Intermedio");
                        plan.setPrecio(9000);
                        plan.setGigas(300);
                        plan.setMinutos(2000);
                        plan.setSms(600);
                      break;
                    case "c":
                    case "C":
                      //PlanComun plan = new PlanComun("Plan Avanzado", 11500, 700, 0 , 900);
                        plan.setNombre("Plan Avanzado");
                        plan.setPrecio(11500);
                        plan.setGigas(700);
                        plan.setMinutos(0);
                        plan.setSms(900);
                      break;
                    case "d":
                    case "D":
                      //PlanComun plan = new PlanComun("Plan Libre", 15500, 0, 0, 0 );
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
                  // Agregar plan al arraylist de servicios del cliente
                  clienteEncontrado.setPlan(plan);
                  break;
                }
              }

              
              else {
                System.out.println(" Cliente no encontrado. Asegúrese de ingresar correctamente los datos.");
              }
            }
          }
          break;

        // MOSTRAR PLANES DE UN CLIENTE
        
        case 3:
          System.exit(0);
          break;
      }
    }
  }
}