/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author LaptopSA
 */
public class Factura {

    private int idFactura;
    private Cliente idCliente;
    private String ruc, direccion, telefono;
    private Date fecha;
    private ArrayList<Item> listaItem;

    public Factura() {
        listaItem = new ArrayList<>();
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    public Cliente getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Cliente idCliente) {
        this.idCliente = idCliente;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double  subTotal() {
        double subtotal = 0;
        for (Item item : listaItem) {
            subtotal = subtotal + item.getCantidad() * item.getPrecio();
        }
        return subtotal;
    }

    public double iva() {
        double iva = 0.12;
        double Piva = 0;

        for (Item item : listaItem) {
            if (item.isIva()) {
                Piva += (item.getCantidad() * item.getPrecio()) * iva;
            }
        }

        return Piva;
    }

    public double total() {
        return this.iva() + this.subTotal();
    }

}
