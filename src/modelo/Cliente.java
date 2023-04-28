/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author LaptopSA
 */
public class Cliente {
    
    private int idCliente;
    private String nombreCliente,apellidoCliente,cedulaCLiente,correoCliente;
    private ArrayList<Factura> listaFactura;

    public Cliente() {
        listaFactura = new ArrayList<>();
    }

    public Cliente(int idCliente, String nombreCliente, String apellidoCliente, String cedulaCLiente, String correoCliente) {
        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.cedulaCLiente = cedulaCLiente;
        this.correoCliente = correoCliente;
    }
    

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getCedulaCLiente() {
        return cedulaCLiente;
    }

    public void setCedulaCLiente(String cedulaCLiente) {
        this.cedulaCLiente = cedulaCLiente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }
    
    public ArrayList<Factura> buscarFacturas(String dni){
        
        ArrayList<Factura> lista = new ArrayList<>();
        for (Factura factura : listaFactura) {
            if (factura.getIdCliente().getCedulaCLiente().equals(dni)) {
                lista.add(factura);
            }
        }
        return lista;
    }
    
    
}
