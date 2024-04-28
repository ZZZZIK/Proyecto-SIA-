package controllers;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


import views.Page1;
import views.*;
import models.*;

public class ControladorGlobal implements MouseListener{

    private GestorClientes gestor;
    private VistaMenu ventanaPrincipal;
    private Page1 p1;
    private Page2 p2;
    private Page3 p3;
    private Page4 p4;
    boolean planBasico=false;
    boolean planIntermedio=false;
    boolean planAvanzado=false;
    boolean planLibre=false;
        
    
    public ControladorGlobal() {
        //Creamos el modelo
        gestor = new GestorClientes();
        //agregar persistencia de archivos
        gestor.cargaDatos();
        
        p1=new Page1();
        p2=new Page2();
        p3=new Page3();
        p4=new Page4();
        
        this.p1.getBtnCrear().addMouseListener(this);
        
        
        //Detección si presiona botones de agregar Planes
        this.p2.getbtnPlanBasico().addMouseListener(this);
        this.p2.getbtnPlanIntermedio().addMouseListener(this);
        this.p2.getbtnPlanAvanzado().addMouseListener(this);
        this.p2.getbtnPlanLibre().addMouseListener(this);

        //Detección si apretaron boton agregar Plan
        this.p2.getbtnAgregar().addMouseListener(this);

        //Detección si apretaron boton eliminar Plan
        this.p2.getbtnEliminar().addMouseListener(this);
        
        
        this.p3.getBtnBuscarC().addMouseListener(this);
        
        this.p4.getBtnBuscarC().addMouseListener(this);
        
        ventanaPrincipal=new VistaMenu(p1,p2,p3,p4);
        ventanaPrincipal.setVisible(true);   
        
        this.ventanaPrincipal.getpageBtn4().addMouseListener(this);
        this.ventanaPrincipal.getSalirbtn().addMouseListener(this);
        
        //Falta añadir escucha de boton p4
    }
      
    public void mouseClicked(MouseEvent e) {
        
        //Con condicionales se fijan las acciones para cada botón.
        
        if (e.getSource() == p1.getBtnCrear()) {
            agregarCliente();
        }
        //***************************************************
        // Detección que boton de plan se presionó
        
        if (e.getSource() == p2.getbtnPlanBasico()){
            
        }
        if (e.getSource() == p2.getbtnPlanIntermedio()){
           
        }
        if (e.getSource() == p2.getbtnPlanAvanzado()){
            
        }
        if (e.getSource() == p2.getbtnPlanLibre()){
           
        }
        
        
        
        // Cuando se presione agregar planes...
        if (e.getSource() == p2.getbtnAgregar()){
            //Actualización de botones
            botonesSeleccionados();
            agregarPlanCliente();
            
            //Limpieza botones
            p2.deseleccionarRadioButtons();
        }
        
        // Cuando se presione eliminar planes...
        if (e.getSource() == p2.getbtnEliminar()){
            //Actualización de botones
            botonesSeleccionados();
            eliminarPlanCliente();
            
            //Limpieza botones
            p2.deseleccionarRadioButtons();
        }
        
        //****************************************************
        if (e.getSource() == p3.getBtnBuscarC()) {
            mostrarDatos();
        }
        if (e.getSource() == p4.getBtnBuscarC()){
            mostrarFiltro();
        }
        
        
        
        if (e.getSource()== ventanaPrincipal.getpageBtn4()){
            mostrarClientes();
        }
        
        
        // Cuando se presiona el boton de salir del programa...
        if (e.getSource()== ventanaPrincipal.getSalirbtn()){
            //Escritura persistencia
            gestor.guardarDatos();
            //Escritura reporte
            System.exit(0);
            
        }
        
        
        
        //p5 mostrarDatos();   
    }
    
    public void botonesSeleccionados(){
        if(p2.getbtnPlanBasico().isSelected()){
            planBasico=true;
        }
        if(p2.getbtnPlanIntermedio().isSelected()){
            planIntermedio=true;
        }
        if(p2.getbtnPlanAvanzado().isSelected()){
            planAvanzado=true;
        }
        if(p2.getbtnPlanLibre().isSelected()){
            planLibre=true;
        }
    }
    
    
    public void eliminarPlanCliente(){
        String msg="";
        String rut=p2.getRut();
        try{
            //public void eliminarPlanDeCliente(String rut, String nombrePlan) 
            if(planBasico){
                gestor.eliminarPlanDeCliente(rut,"Plan Basico");
                System.out.println("del basico ");
                planBasico=false;
            }
            if(planIntermedio){
                gestor.eliminarPlanDeCliente(rut,"Plan Intermedio");
                System.out.println("del interm ");
                planIntermedio=false;
            }
            if(planAvanzado){
                gestor.eliminarPlanDeCliente(rut,"Plan Avanzado");
                System.out.println("del avanzao ");
                planAvanzado=false;
            }
            if(planLibre){
                gestor.eliminarPlanDeCliente(rut,"Plan Libre");
                System.out.println("del libre ");
                planLibre=false;
            }
        }catch(ClienteInexistenteException e){
            msg="No existe el Cliente";
        }
        catch(PlanInexistenteException e){
            msg="Plan no encontrado";
        }
        p2.setStatus(msg);
    }
    
    public void mostrarFiltro(){
        p4.getTextPaneFiltro().setText("");
        p4.getTextPaneFiltro().setEditable(false);
        StyledDocument doc = p4.getTextPaneFiltro().getStyledDocument();
        StyleContext context = new StyleContext();
        // build a style
        Style style = context.addStyle("test", null);
        // set some style properties
        StyleConstants.setForeground(style, Color.BLACK);
        
        // Recibimos el num dado por el usuario para el filtro
        int cantPlanesFiltro=Integer.parseInt(p4.getTextCant());

        // bandera si esta vacio o no 
        int cont=0;
        
        try{
            for(int i=gestor.largolst()-1;i>=0;i--){
                Cliente cliente = (Cliente)gestor.obtenerClientesPorNPlanes(cantPlanesFiltro,i);
                
                if (cliente!=null){
                    String nombre=cliente.getNombre();
                    doc.insertString(0,nombre+"\n",style);
                    cont++;
                }
            }
            if (cont==0){
                doc.insertString(0,"La lista se encuentra vacia\n",style);
            }
        }catch(Exception e){
        
        }  
    }
    
    
    
    
    
 
    
    public void agregarPlanCliente(){
        String msg="";
        String rut=p2.getRut();
        try{
            
            
            if(planBasico){
                
                PlanComun plan= new PlanComun("Plan Basico",7000,100,1000,400,0);
                
                gestor.agregarPlan(rut, plan);
                
                System.out.println("basico ");
                planBasico=false;
            }
            if(planIntermedio){
                
                PlanComun plan= new PlanComun("Plan Intermedio",9000,300,2000,600,0);
                gestor.agregarPlan(rut, plan);
                
                System.out.println("interm ");
                planIntermedio=false;
            }
            if(planAvanzado){
                
                PlanComun plan= new PlanComun("Plan Avanzado",11500,700,1000000,900,0);
                gestor.agregarPlan(rut, plan);
                
                System.out.println("avanzao ");
                planAvanzado=false;
            }
            if(planLibre){
                
                PlanComun plan= new PlanComun("Plan Libre",1000000,1000000,1000000,1000000, 0);
                // Uso de sobrecarga roaming
                plan.establecerRoaming();
                
                gestor.agregarPlan(rut, plan);
                
                
                System.out.println("libre ");
                planLibre=false;
            }
            
        /*
            Plan Basico;7000;100;1000;400;0
            Plan Intermedio;9000;300;2000;600;0
            Plan Avanzado;11500;700;1000000;900;0
            Plan Libre;1000000;1000000;1000000;1000000;0
            PlanComun plan=
            
            gestor.agregarPlan(rut, )
            
        */    
            
            
            
        }catch(ClienteInexistenteException e){
            msg="No existe el Cliente";
        }catch(Exception e){
            msg="Plan no seleccionado";
        }
        
        p2.setStatus(msg);
   
    }
    
    
    
    
    
    
    public void agregarCliente() {
        
        //Recibimos Datos ingresados por la ventana añadir usuario
        String nombre=p1.getNombre();
        String rut=p1.getRut();
        String correo=p1.getCorreo();
        int num=p1.getNum();
       
        String msg="";
        
        // Se crea el Cliente
        Cliente cliente = new Cliente(nombre, rut, correo, num);

        try {    
            gestor.agregarCliente(cliente);
            
            msg="Se ha agregador correctamente.";
        } catch (RutRepetidoException ex) {
            
        //Exepción para clientes que ya existen.
           
            msg="Error, Cliente ya existe";
     
            
        } catch (Exception e){
            p1.setStatus("Error");
          
        }
        
        p1.setStatus(msg);
    }
    

    public void mostrarDatos(){
        
        p3.getTextPane().setText("");
        p3.getTextPane().setEditable(false);
        StyledDocument doc = p3.getTextPane().getStyledDocument();
        StyleContext context = new StyleContext();
        // build a style
        Style style = context.addStyle("test", null);
        // set some style properties
        StyleConstants.setForeground(style, Color.BLACK);
                   
        String msg="";
            try{
                Cliente cliente=gestor.buscarCliente(p3.getRut());
                if (cliente.getSizePlan()==0){
                    doc.insertString(0,"La lista se encuentra vacia\n",style);

                }

                //Imprime los planes por orden de insercion
                for(int i=cliente.getSizePlan()-1;i>=0;i--){

                    PlanComun planI= cliente.getPlan(i);
                    String nombre=planI.getNombre();
                    doc.insertString(0,nombre+"\n",style);
                }
            //ClienteInexistente    
            }catch(ClienteInexistenteException e1){
                msg="No existe cliente";
                        
            }catch(Exception e2){
                msg="Error:Nose";
     
            }
            p3.setLblstatusbusqueda(msg);
            
   }
    
    
    
    
    
    public void mostrarClientes(){
        p4.getTextPane().setText("");
        p4.getTextPane().setEditable(false);
        StyledDocument doc = p4.getTextPane().getStyledDocument();
        StyleContext context = new StyleContext();
        // build a style
        Style style = context.addStyle("test", null);
        // set some style properties
        StyleConstants.setForeground(style, Color.BLACK);
        

        
        try{
            if (gestor.largolst()==0){
                doc.insertString(0,"La lista se encuentra vacia\n",style);

            }
            //Imprime los planes por orden de insercion
            for(int i=gestor.largolst()-1;i>=0;i--){
                Cliente cliente=(Cliente)gestor.posicionClienteLista(i);
                String nombre=cliente.getNombre();
                doc.insertString(0,nombre+"\n",style);
            }
        }catch(Exception e){
            
        }  
}
    
    
    
    

    @Override
    public void mousePressed(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
         //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
         //To change body of generated methods, choose Tools | Templates.
    }
    
}