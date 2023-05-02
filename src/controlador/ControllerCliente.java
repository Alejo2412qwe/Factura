/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.Cliente;
import modelo.Factura;
import modelo.Item;
import vista.FacturaV;
import vista.ItemFactura;
import vista.RegistroCliente;

/**
 *
 * @author LaptopSA
 */
public class ControllerCliente {

    DefaultTableModel dtm = new DefaultTableModel();
    ArrayList<Cliente> listaCliente = new ArrayList<>();
    private RegistroCliente rc = new RegistroCliente();
    private ItemFactura item = new ItemFactura();
    private FacturaV f = new FacturaV();
    private Factura fac = new Factura();
    private Cliente c;

    double subtotal;
    double total;
    double Piva;

    int id = 1;

    public void inicarControl() {
        rc.getIdCliente().setText(String.valueOf(id));
        rc.setVisible(true);
        rc.getBtnRegistroCliente().addActionListener(l -> registrarCliente());
        f.getBtnAgregarItem().addActionListener(l -> abrirItem());
        cargaColumnas();
        item.getBtnRegistroItem().addActionListener(l -> registroITem());
        item.getBtnCerrarItem().addActionListener(l -> item.dispose());
        f.getBtnCerrarFactura().addActionListener(l -> finalizarFactura());
    }

    public void abrirItem() {
        item.getIdFactura().setText(String.valueOf(id));
        item.setVisible(true);
    }

    public void cargaColumnas() {
        ArrayList<Object> columnas = new ArrayList<>();
        columnas.add("DETALLE");
        columnas.add("IVA");
        columnas.add("CANTIDAD");
        columnas.add("PRECIO");
        for (Object columna : columnas) {
            dtm.addColumn(columna);
        }
        item.getTablaItems().setModel(dtm);
    }

    public void registrarCliente() {

        if (validarCliente()) {

            String nombreCliente = rc.getNombreCliente().getText();
            String apellidoCliente = rc.getApeCliente().getText();
            String cedulaCLiente = rc.getCedulaCliente().getText();
            String correoCliente = rc.getCorreoCliente().getText();

            Cliente cliente = new Cliente();
            cliente.setNombreCliente(nombreCliente);
            cliente.setApellidoCliente(apellidoCliente);
            cliente.setCorreoCliente(correoCliente);
            cliente.setCedulaCLiente(cedulaCLiente);

            for (int i = 0; i < listaCliente.size(); i++) {
                listaCliente.add(cliente);
            }

            System.out.println(cliente.getNombreCliente());
            JOptionPane.showMessageDialog(null, "CLIENTE REGISTRADO CORRECTAMENTE");
            rc.dispose();
            f.setVisible(true);
            f.getIdFactura().setText(String.valueOf(id));
            f.getDireccionFactura().setText("Av. San Miguel con San Bartolo");
            f.getCedulaCliente().setText(cedulaCLiente);
            f.getTelefonoFactura().setText("0982122184");
            int cod = hashCode();
            f.getRucFactura().setText(String.valueOf(cod));
            DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");

            String date = dateFormat.format(new Date());
            f.getFechaFactura().setText(date);
            limpiarCamposCliente();
        }
    }

    public void registroITem() {

        if (validarItem()) {
            item.getIdFactura().setText(String.valueOf(id));
            boolean iva;
            ArrayList<Object> datos = new ArrayList<>();
            if (item.getRadioIVA().isSelected()) {
                iva = true;
            } else {
                iva = false;
            }

            Item i = new Item();
            i.setCantidad(Integer.parseInt(item.getCantidadItem().getText()));
            i.setDetalle(item.getDetalleItem().getText());
            i.setIva(iva);
            i.setPrecio(Double.parseDouble(item.getPrecioItem().getText()));

            fac.listaItem.add(i);
            subtotal = fac.subTotal();
            total = fac.total();
            Piva = fac.iva();

            f.getSubtotalFactura().setText(String.valueOf(subtotal));
            f.getTotalFactura().setText(String.valueOf(total));
            f.getIvaFactura().setText(String.valueOf(Piva));

            Object[] objeto = new Object[]{item.getDetalleItem().getText(), iva, item.getCantidadItem().getText(), item.getPrecioItem().getText()};
            datos.add(objeto);

            for (Object object : datos) {
                dtm.addRow(objeto);
            }
            item.getTablaItems().setModel(dtm);
            limpiarCamposItem();
        }

    }

    public void finalizarFactura() {

        f.dispose();
        item.dispose();

        for (int i = 0; i < fac.listaItem.size(); i++) {
            fac.listaItem.remove(i);
        }

        DefaultTableModel modelo = (DefaultTableModel) item.getTablaItems().getModel();
        modelo.setRowCount(0);

        JOptionPane.showMessageDialog(rc, "FACTURA REGISTRADA");

        String direccion = "Av. San Miguel con San Bartolo";
        Factura factura = new Factura();
        factura.setIdFactura(id);
        factura.setDireccion(direccion);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        String date = formato.format(new Date());
        f.getFechaFactura().setText(date);
        Date fecha = new Date();
        try {
            fecha = formato.parse(date);
            factura.setFecha(fecha);
            System.out.println(fecha);
        } catch (Exception ex) {
            Logger.getLogger(ControllerCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        f.getRucFactura().setText(generarRuc(c.getCedulaCLiente()));
        
        f.getSubtotalFactura().setText("");
        f.getTotalFactura().setText("");
        f.getIvaFactura().setText("");

        String telefono = "0982122184";
        f.getDireccionFactura().setText(direccion);
        f.getTelefonoFactura().setText(telefono);

        factura.setRuc(generarRuc(c.getCedulaCLiente()));
        factura.setTelefono(date);
        factura.setIdCliente(c);

        id = id + 1;
        f.setVisible(true);
        f.getIdFactura().setText(String.valueOf(id));
        for (int i = 0; i < listaCliente.size(); i++) {
            f.getCedulaCliente().setText(listaCliente.get(i).getCedulaCLiente());
        }

        limpiarCamposCliente();
        limpiarCamposItem();
        subtotal = 0;
        Piva = 0;
        total = 0;
    }

    public void limpiarCamposCliente() {
        rc.getNombreCliente().setText("");
        rc.getApeCliente().setText("");
        rc.getTelefonoCliente().setText("");
        rc.getCedulaCliente().setText("");
        rc.getCorreoCliente().setText("");
    }

    public void limpiarCamposItem() {
        item.getDetalleItem().setText("");
        item.getCantidadItem().setText("");
        item.getPrecioItem().setText("");
        if (item.getRadioIVA().isSelected()) {
            item.getRadioIVA().setSelected(false);
        }
    }

    public boolean validarItem() {

        boolean ban = true;

        if (item.getDetalleItem().getText().isEmpty()) {
            JOptionPane.showMessageDialog(rc, "EL DETALLE ESTA VACIO");
            ban = false;
        }
        if (!item.getDetalleItem().getText().matches("[a-zA-Z]*")) {
            JOptionPane.showMessageDialog(rc, "EL DETALLE ES INCORRECTO");
            ban = false;
        }

        if (item.getCantidadItem().getText().isEmpty()) {
            JOptionPane.showMessageDialog(rc, "LA CANTIDAD ESTA VACIA");
            ban = false;
        }

        if (!item.getCantidadItem().getText().matches("[0-9]*")) {
            JOptionPane.showMessageDialog(rc, "LA CANTIDAD ES INCORRECTA");
            ban = false;
        }

        if (item.getPrecioItem().getText().isEmpty()) {
            JOptionPane.showMessageDialog(rc, "EL PRECIO ESTA VACIO");
            ban = false;
        }

        if (!item.getPrecioItem().getText().matches("^-?[0-9]+(\\.[0-9]+)?$")) {
            JOptionPane.showMessageDialog(rc, "EL PRECIO ES INCORRECTO");
            ban = false;
        }

        return ban;
    }

    public boolean validarCliente() {

        boolean ban = true;

        if (rc.getNombreCliente().getText().isEmpty()) {
            JOptionPane.showMessageDialog(rc, "EL NOMBRE ESTA VACIO");
            ban = false;
        }
        if (!rc.getNombreCliente().getText().matches("[a-zA-Z]*")) {
            JOptionPane.showMessageDialog(rc, "EL NOMBRE ES INCORRECTO");
            ban = false;
        }

        if (rc.getApeCliente().getText().isEmpty()) {
            JOptionPane.showMessageDialog(rc, "EL APELLIDO ESTA VACIO");
            ban = false;
        }

        if (!rc.getApeCliente().getText().matches("[a-zA-Z]*")) {
            JOptionPane.showMessageDialog(rc, "EL APELLIDO ES INCORRECTO");
            ban = false;
        }

        if (rc.getCedulaCliente().getText().isEmpty()) {
            JOptionPane.showMessageDialog(rc, "LA CEDULA ESTA VACIA");
            ban = false;
        }

        if (!validarCedula(rc.getCedulaCliente().getText())) {
            JOptionPane.showMessageDialog(rc, "LA CEDULA ES INCORRECTA");
            ban = false;
        }

        if (rc.getTelefonoCliente().getText().isEmpty()) {
            JOptionPane.showMessageDialog(rc, "EL TELEFONO ESTA VACIO");
            ban = false;
        }

        if (!rc.getTelefonoCliente().getText().matches("^\\d{10}$")) {
            JOptionPane.showMessageDialog(rc, "EL TELEFONO ES INCORRECTO");
            ban = false;
        }

        if (rc.getCorreoCliente().getText().isEmpty()) {
            JOptionPane.showMessageDialog(rc, "EL CORREO ESTA VACIO");
            ban = false;
        }

        if (!rc.getCorreoCliente().getText().matches("^(.+)@(.+)$")) {
            JOptionPane.showMessageDialog(rc, "EL CORREO ES INCORRECTO");
            ban = false;
        }

        return ban;
    }
    
    public static String generarRuc(String cedula) {
    String ruc = "";
    if (cedula.length() == 10) {
        ruc = "2" + cedula.substring(0, 9);
        int sum = 0;
        for (int i = 0; i < ruc.length(); i++) {
            int digit = Integer.parseInt(ruc.substring(i, i + 1));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }
            sum += digit;
        }
        int lastDigit = (10 - (sum % 10)) % 10;
        ruc += lastDigit;
    }
    return ruc;
}

    public boolean validarCedula(String cedula) {
        boolean val = false;
        //Divide la cadena en los 10 numeros
        //Integer.parseInt sirve para transformar una cadena a entero. 
        //subString es un metodo de string(Desde, hasta)
        if (cedula.matches("\\d{10}")) {
            int d1 = Integer.parseInt(cedula.substring(0, 1));
            int d2 = Integer.parseInt(cedula.substring(1, 2));
            int d3 = Integer.parseInt(cedula.substring(2, 3));
            int d4 = Integer.parseInt(cedula.substring(3, 4));
            int d5 = Integer.parseInt(cedula.substring(4, 5));
            int d6 = Integer.parseInt(cedula.substring(5, 6));
            int d7 = Integer.parseInt(cedula.substring(6, 7));
            int d8 = Integer.parseInt(cedula.substring(7, 8));
            int d9 = Integer.parseInt(cedula.substring(8, 9));
            int d10 = Integer.parseInt(cedula.substring(9));

            //Multiplica todas la posciones impares * 2 y las posiciones pares se multiplica 1
            d1 = d1 * 2;
            if (d1 > 9) {
                d1 = d1 - 9;
            }

            d3 = d3 * 2;
            if (d3 > 9) {
                d3 = d3 - 9;
            }

            d5 = d5 * 2;
            if (d5 > 9) {
                d5 = d5 - 9;
            }

            d7 = d7 * 2;
            if (d7 > 9) {
                d7 = d7 - 9;
            }

            d9 = d9 * 2;
            if (d9 > 9) {
                d9 = d9 - 9;
            }

            // SUMA TODOS LOS  NUMEROS PARES E IMPARES
            int sumpar = d2 + d4 + d6 + d8;
            int sumimp = d1 + d3 + d5 + d7 + d9;
            int total = sumpar + sumimp;

            //DIVIDO MI DECENA SUPERIRO PARA 10 Y SI EL RESULTADO ES DIFERENTE DE 0 SUMA 1
            double decenasuperior = total;
            while (decenasuperior % 10 != 0) {
                decenasuperior = decenasuperior + 1;
            }

            if ((decenasuperior - total) == d10) {
                val = true;
            }
        }

        return val;
    }

}
