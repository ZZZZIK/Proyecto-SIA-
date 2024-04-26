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
        gestor = new GestorClientes();
        
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
        
        
        this.p3.getBtnBuscarC().addMouseListener(this);
        
        ventanaPrincipal=new VistaMenu(p1,p2,p3,p4);
        ventanaPrincipal.setVisible(true);
        this.ventanaPrincipal.getpageBtn4().addMouseListener(this);
        
        //Falta añadir escucha de boton p4

    }
    
    /*
     public JRadioButton getbtnPlanBasico() {
        return btnPlanBasico;
    }
    public JRadioButton getbtnPlanIntermedio() {
        return btnPlanIntermedio;
    }
    public JRadioButton getbtnPlanAvanzado() {
        return btnPlanAvanzado;
    }
    
    public JRadioButton getbtnPlanLibre() {
        return btnPlanLibre;
    }
    public JButton getbtnAgregar() {
       return btnAgregar;
    }
    
    */
    
    
    
    public void mouseClicked(MouseEvent e) {
        
        //Con condicionales se fijan las acciones para cada botón.
        
        if (e.getSource() == p1.getBtnCrear()) {
            agregarCliente();
        }
        //***************************************************
        // Detección que boton de plan se presionó
        if (e.getSource() == p2.getbtnPlanBasico()){
            planBasico=true;
        }
        if (e.getSource() == p2.getbtnPlanIntermedio()){
            planIntermedio=true;
        }
        if (e.getSource() == p2.getbtnPlanAvanzado()){
            planAvanzado=true;
        }
        if (e.getSource() == p2.getbtnPlanLibre()){
            planLibre=true;
        }
        
        if (e.getSource() == p2.getbtnAgregar()){
            agregarPlanCliente();
            //Limpieza
            p2.deseleccionarRadioButtons();
        }
        //****************************************************
        if (e.getSource() == p3.getBtnBuscarC()) {
            mostrarDatos();
        }
        if (e.getSource()== ventanaPrincipal.getpageBtn4()){
            mostrarCliente();
        }
          
    }
    
    
 
    
    public void agregarPlanCliente(){
        String rut=p2.getRut();
        try{
            if(planBasico){
                gestor.agregarPlan(rut, new PlanBasico());
                System.out.println("basico ");
                planBasico=false;
            }
            if(planIntermedio){
                gestor.agregarPlan(rut, new PlanIntermedio());
                System.out.println("interm ");
                planIntermedio=false;
            }
            if(planAvanzado){
                gestor.agregarPlan(rut, new PlanAvanzado());
                System.out.println("avanzao ");
                planAvanzado=false;
            }
            if(planLibre){
                gestor.agregarPlan(rut, new PlanLibre());
                System.out.println("libre ");
                planLibre=false;
            }
        }catch(ClienteInexistenteException e){
            
            p2.setStatus("No existe el Cliente");
        }
        
    }
    
    
    
    
    public void agregarCliente() {
        
        //Recibimos Datos ingresados por la ventana añadir usuario
        String nombre=p1.getNombre();
        String rut=p1.getRut();
        String correo=p1.getCorreo();
        int num=p1.getNum();
       
        // Se crea el Cliente
        Cliente cliente = new Cliente(nombre, rut, correo, num);

        try {    
            gestor.agregarCliente(cliente);
            
        } catch (RutRepetidoException ex) {
            //Exepción para clientes que ya existen.
            System.out.println("Error al agregar cliente");
            //estado= false;
        } 
        
        //p1.setStatus(estado);
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
    
    
    
    
    public void mostrarCliente(){
        p4.getTextPane().setText("");
        p4.getTextPane().setEditable(false);
        StyledDocument doc = p4.getTextPane().getStyledDocument();
        StyleContext context = new StyleContext();
        // build a style
        Style style = context.addStyle("test", null);
        // set some style properties
        StyleConstants.setForeground(style, Color.BLACK);
        

        //for(int i=0;i<gestor.largolst();i++){
        //    Cliente cliente=(Cliente)gestor.posicionClienteLista(i);    
        //}
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