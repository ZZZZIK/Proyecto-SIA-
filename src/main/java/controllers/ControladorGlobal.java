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
    private Page5 p5;
    boolean planBasico=false;
    boolean planIntermedio=false;
    boolean planAvanzado=false;
    boolean planLibre=false;
    boolean vip=false;
    
    public ControladorGlobal() {
        //Crear modelo
        gestor = new GestorClientes();
        //agregar persistencia de archivos
        gestor.cargaDatos();
        
        p1=new Page1();
        p2=new Page2();
        p3=new Page3();
        p4=new Page4();
        p5=new Page5();
        this.p1.getBtnCrear().addMouseListener(this);
        
        //Detección si presiona botones de agregar Planes
        this.p2.getbtnPlanBasico().addMouseListener(this);
        this.p2.getbtnPlanIntermedio().addMouseListener(this);
        this.p2.getbtnPlanAvanzado().addMouseListener(this);
        this.p2.getbtnPlanLibre().addMouseListener(this);

        this.p2.getbtnAgregar().addMouseListener(this); //Detección boton agregar Plan
        
        this.p2.getbtnEliminar().addMouseListener(this); //Detección boton eliminar Plan
      
        this.p3.getBtnBuscarC().addMouseListener(this); //Detección boton buscar usuario (ingresar)

        this.p4.getBtnBuscarC().addMouseListener(this); //Detección boton buscar N Planes

        this.p5.getBntMod().addMouseListener(this); //Detección boton modificar Plan
        
        ventanaPrincipal=new VistaMenu(p1,p2,p3,p4,p5);
        ventanaPrincipal.setVisible(true);   
        
        this.ventanaPrincipal.getpageBtn4().addMouseListener(this);
        this.ventanaPrincipal.getSalirbtn().addMouseListener(this);
        this.ventanaPrincipal.getBtnReporte().addMouseListener(this);
        
        
    }
      
    public void mouseClicked(MouseEvent e) {
        //Con condicionales se fijan las acciones para cada botón.
        
        if (e.getSource() == p1.getBtnCrear()) {
            BtnSelec();
            agregarCliente();
            p1.deseleccionarRadioButtons();
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
        //Si se presiona modificar Plan...
        if (e.getSource ()== p5.getBntMod()){
            modPlan();
        }
        if (e.getSource()== ventanaPrincipal.getpageBtn4()){
            mostrarClientes();
        }
        
        if (e.getSource()== ventanaPrincipal.getBtnReporte()){
            gestor.generarReporte();
        }
       
        // Cuando se presiona el boton de salir del programa...
        if (e.getSource()== ventanaPrincipal.getSalirbtn()){
            //Escritura persistencia
            gestor.guardarDatos();
            //Escritura reporte
            System.exit(0);
        }
    }
    
    //*************************** AGREGAR CLIENTE/CLIENTE VIP *****************************************
    public void BtnSelec(){
        if(p1.getBtnRVip().isSelected()){
            vip=true;
        }
    }
    
    public void agregarCliente() {
        //Recibimos Datos ingresados por la ventana añadir usuario
        String nombre=p1.getNombre();
        String rut=p1.getRut();
        String correo=p1.getCorreo();
        int num=p1.getNum();
       
        String msg="";
        
        try {  
            if (vip){
                //Uso de sobrescritura Cliente a cliente vip
                ClienteVIP cliente= new ClienteVIP(nombre, rut, correo, num);
                gestor.agregarCliente(cliente);
                vip=false;
            }else{
                Cliente cliente = new Cliente(nombre, rut, correo, num);
                gestor.agregarCliente(cliente);
            }
            msg="Se ha agregador correctamente.";
        }catch (RutRepetidoException ex) {
            //Exepción para clientes que ya existen.
            msg="Error, Cliente ya existe";
        } catch (Exception e){
            //Exepcion general
            p1.setStatus("Error");
        }
        p1.setStatus(msg);
    }
    
    //************************************ AÑADIR/ELIMINAR PLANES ****************************************************
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
    
    public void agregarPlanCliente(){
        String msg="";
        String msgRoaming="";
        String rut=p2.getRut();
        int mbRoamingBasico=0;
        int mbRoamingIntermedio=0;
        int mbRoamingAvanzado=0;
        
        String desc="";
        
        if (!p2.getLblRoamingBasico().isEmpty()){
            mbRoamingBasico=Integer.parseInt(p2.getLblRoamingBasico());
        }
        if(!p2.getLblRoamingIntermedio().isEmpty()){
            mbRoamingIntermedio=Integer.parseInt(p2.getLblRoamingIntermedio());
        }
        if(!p2.getLblRoamingAvanzado().isEmpty()){
            mbRoamingAvanzado=Integer.parseInt(p2.getLblRoamingAvanzado());
        }
        
        try{
            Cliente cliente=(Cliente)gestor.buscarCliente(rut);
            //si es el primer plan del cliente
            if(cliente.getSizePlan()==0){
               desc="Descuento de 5%";
            }
            
            if(planBasico){
                PlanComun plan= new PlanComun("Plan Basico",7000,100,1000,400,0,desc);
                if(plan.establecerRoaming(mbRoamingBasico)){
                    msgRoaming="y roaming añadido";
                }
                gestor.agregarPlan(rut, plan);
                planBasico=false;
                desc="";
            }
            if(planIntermedio){
                PlanComun plan= new PlanComun("Plan Intermedio",9000,300,2000,600,0,desc);
                if(plan.establecerRoaming(mbRoamingIntermedio)){
                    msgRoaming="y roaming añadido";
                }
                gestor.agregarPlan(rut, plan);
                planIntermedio=false;
                desc="";
            }
            if(planAvanzado){
                PlanComun plan= new PlanComun("Plan Avanzado",11500,700,1000000,900,0,desc);
                if(plan.establecerRoaming(mbRoamingAvanzado)){
                    msgRoaming="y roaming añadido";
                }
                gestor.agregarPlan(rut, plan);
                planAvanzado=false;
                desc="";
            }
            if(planLibre){
                PlanComun plan= new PlanComun("Plan Libre",15500,1000000,1000000,1000000,9999999,desc);
                // Uso de sobrecarga roaming
                if(plan.establecerRoaming()){
                    msgRoaming="y roaming ilimitado añadido";
                }
                gestor.agregarPlan(rut, plan);
                planLibre=false;
                desc="";
            }
        }catch(ClienteInexistenteException e){
            msg="No existe el Cliente";
        }catch(RoamingException e2){
            msg="roaming inválido";
        }catch(Exception e3){
            msg="Plan no seleccionado";
        }
        msg="Plan añadido ";
        p2.setStatus(msg+" "+msgRoaming);
    }
    
    public void modPlan(){
        String opcion= p5.getTxtOpcion();
        String msg="";
        boolean verificador=true;
        int minutos=Integer.parseInt(p5.getTxtMinutos());
        String nPlan="";
        
        if (opcion.equalsIgnoreCase("A")) {
            nPlan = "Plan Basico";
        } else if (opcion.equalsIgnoreCase("B")) {
            nPlan = "Plan Intermedio";
        } else if (opcion.equalsIgnoreCase("C")) {
            nPlan = "Plan Avanzado";
        } else if (opcion.equalsIgnoreCase("D")) {
            nPlan = "Plan Libre";
        }else{
            msg="opcion ingresada no valida ";
            p5.setTxtError(msg);
            verificador=false;
        }
        // Si se ingresó una opción valida...
        if(verificador){
            for (int i=0; i<gestor.largolst();i++){ //recorrer clientes dentro del mapa
                Cliente cliente=(Cliente)gestor.posicionClienteLista(i);
                for(int j=0;j<cliente.getSizePlan();j++){ //recorrer planes dentro del cliente (arraylist planes)
                    PlanComun plan=(PlanComun)cliente.getPlan(j);
                    
                    // Si el plan coincide con el nombre seleccionado por la opción
                    if (plan.getNombre().equals(nPlan)) {
                        plan.setMinutos(minutos);
                    }
                }
            }
        }
    }
    
    public void eliminarPlanCliente(){
        String msg="";
        String rut=p2.getRut();
        try{ 
            if(planBasico){
                gestor.eliminarPlanDeCliente(rut,"Plan Basico");
                planBasico=false;
            }
            if(planIntermedio){
                gestor.eliminarPlanDeCliente(rut,"Plan Intermedio");
                planIntermedio=false;
            }
            if(planAvanzado){
                gestor.eliminarPlanDeCliente(rut,"Plan Avanzado");
                planAvanzado=false;
            }
            if(planLibre){
                gestor.eliminarPlanDeCliente(rut,"Plan Libre");
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
    
    //******************************** MOSTRAR DATOS CLIENTE/MOSTRAR TODOS LOS CLIENTES / MOSTRAR TODOS LOS CLIENTES+FILTROCANTPLANES **************************************************

    public void mostrarDatos(){
        p3.getTextPane().setText("");
        p3.getTextPane().setEditable(false);
        StyledDocument doc = p3.getTextPane().getStyledDocument();
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
        StyleConstants.setForeground(style, Color.BLACK);
                   
        String msg="";
        
        int total=0;
        Cliente cliente;

        try{
            //Buscamos el cliente dentro del trycatch con exepcion de cliente no existente
            cliente=gestor.buscarCliente(p3.getRut());
            
            //En caso de que el cliente no posea planes 
            if (cliente.getSizePlan()==0){
                doc.insertString(0,"La lista se encuentra vacia\n",style);
            }
            //Imprime los planes por orden de insercion    
            for(int i=cliente.getSizePlan()-1;i>=0;i--){
                PlanComun planI= cliente.getPlan(i);
                String nombre=planI.getNombre();
                int precio=(int)planI.getPrecio();
                String getPrimerDesc=planI.getPrimerDesc();
                 
                if(i==0){
                    precio=(int)cliente.descuento(precio);
                }
                doc.insertString(0,nombre + "        " + precio +"           "+ getPrimerDesc +"\n",style);
                total+=precio; 
            }
            // Calculamos y mostramos en el lablel el total sin descuento
            cliente.setTotalSinDesc(total);
            p3.setlblTotalSin((int)cliente.getTotalSinDescuento());
            
            //Obtenemos el total con descuento
            double totDescuento=0;
                    
            //Si el cliente es vip...
            if(cliente instanceof ClienteVIP){
                ClienteVIP clienteVIP=(ClienteVIP)cliente;
                totDescuento = clienteVIP.getTotalConDescuento();
                msg=" CLIENTE VIP ";
            }else{
                totDescuento= cliente.getTotalConDescuento();
            }
            //Lo pasamos a int 
            int totDescuentoInt=(int) totDescuento;
            
            //Ponemos el label con el del total con descuento aplicado
            p3.setlblTotalCon(totDescuentoInt);
            p3.setlblTotalPorcentaje("");
            
            //Solo en caso de poseer descuento se mostrará el porcentaje
            if(cliente.getDctoTotal()!=0){
                double descporcentaje=cliente.getDctoTotal()*100;
                int descporcentajeInt = (int) descporcentaje;
                p3.setlblTotalPorcentaje("Descuento total de :"+descporcentajeInt + "%");
            }  
        }catch(ClienteInexistenteException e1){
            msg="No existe cliente";
            p3.setLblstatusbusqueda(msg);           
        }catch(Exception e2){
            msg="Error";
            p3.setLblstatusbusqueda(msg);
        }
        p3.setLblstatusbusqueda("Cliente Encontrado" + msg);           
    }

    public void mostrarClientes(){
        p4.getTextPane().setText("");
        p4.getTextPane().setEditable(false);
        StyledDocument doc = p4.getTextPane().getStyledDocument();
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
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
            System.out.println("Error al mostrar clientes");
        }  
    }
    
    public void mostrarFiltro(){
        p4.getTextPaneFiltro().setText("");
        p4.getTextPaneFiltro().setEditable(false);
        StyledDocument doc = p4.getTextPaneFiltro().getStyledDocument();
        StyleContext context = new StyleContext();
        Style style = context.addStyle("test", null);
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
            System.out.println("Error al mostrar clientes por filtro");
        }  
    }
     
    //*************************************************************************************************************************************************************************
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