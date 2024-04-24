package controllers;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.awt.Color;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;






import views.*;
import models.*;

public class ControladorGlobal implements MouseListener{

    private GestorClientes gestor;
    private VistaMenu vMenu;
    private Page1 p1;
    private Page2 p2;
    private Page3 p3;
    
    
    public ControladorGlobal() {
        gestor = new GestorClientes();
        //vMenu = new VistaMenu(p1,p2,p3);
        p1=new Page1();
        p2=new Page2();
        p3=new Page3();
        this.p1.getBtnCrear().addMouseListener(this);
        this.p3.getBtnBuscarC().addMouseListener(this);
        
        VistaMenu ventanaPrincipal=new VistaMenu(p1,p2,p3);
        ventanaPrincipal.setVisible(true);
  
    }
    
    public void mouseClicked(MouseEvent e) {
        //Con condicionales se fijan las acciones para cada botón.
        if (e.getSource() == p1.getBtnCrear()) {
            agregarCliente();
        }
        if (e.getSource() == p3.getBtnBuscarC()) {
            mostrarDatos();
        }

    }
    
    public void agregarCliente() {
        //Recibimos Datos ingresados por la ventana añadir usuario
        String nombre=p1.getNombre();
        String rut=p1.getRut();
        String correo=p1.getCorreo();
        int num=p1.getNum();

        //Le agregamos 2 Planes al cliente para fines de testeo
        Cliente cliente = new Cliente(nombre, rut, correo, num);
        PlanComun planTest1 = new PlanComun("Plan TEST TEST", 9000, 300, 2000, 600, 500);
        PlanComun planTest2 = new PlanComun("Plan test2", 9000, 300, 2000, 600, 500);
        
        cliente.setPlan(planTest1);
        cliente.setPlan(planTest2);
        
        
        boolean estado=gestor.agregarCliente(cliente);
        p1.setStatus(estado);
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
       
        //buscarCliente
        
      
        if(gestor.buscarCliente(p3.getRut())==null){
            System.out.print("es nulo");
        }
        Cliente cliente=gestor.buscarCliente(p3.getRut());

        try{
            if (cliente.getSizePlan()==0){
               doc.insertString(0,"La lista se encuentra vacia\n",style);

            }
            
            //Imprime los planes por orden de insercion
            for(int i=cliente.getSizePlan()-1;i>=0;i--){
                    
                PlanComun planI= cliente.getPlan(i);
                String nombre=planI.getNombre(); 
                doc.insertString(0,nombre+"\n",style);
            }
        }catch(Exception E){
            
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