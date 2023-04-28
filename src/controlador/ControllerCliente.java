/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Cliente;
import vista.ItemFactura;
import vista.RegistroCliente;

/**
 *
 * @author LaptopSA
 */
public class ControllerCliente {
    
    ArrayList<Cliente> listaCliente = new ArrayList<>();
    private RegistroCliente rc = new RegistroCliente();
    private ItemFactura item = new ItemFactura();
    
    
    public void inicarControl() {
        rc.setVisible(true);
        rc.getBtnRegistroCliente().addActionListener(l -> registrarCliente());
    }
    
    public void registrarCliente() {
        
        int id = 0;
        String nombreCliente = rc.getNombreCliente().getText();
        String apellidoCliente = rc.getApeCliente().getText();
        String cedulaCLiente = rc.getCedulaCliente().getText();
        String correoCliente = rc.getCorreoCliente().getText();
        
        Cliente cliente = new Cliente();
        cliente.setNombreCliente(nombreCliente);
        cliente.setApellidoCliente(apellidoCliente);
        cliente.setCorreoCliente(correoCliente);
        cliente.setCedulaCLiente(cedulaCLiente);
        
        if (id > 0) {
            id++;
            cliente.setIdCliente(id);
        }
        
        for (int i = 0; i < listaCliente.size(); i++) {
            listaCliente.add(cliente);
        }
        System.out.println(cliente.getNombreCliente());
        JOptionPane.showMessageDialog(rc, "CLIENTE REGISTRADO CORRECTAMENTE");
        item.setVisible(true);
    }
    
}
