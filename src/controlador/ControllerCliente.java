/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    int id = 1;

    public void inicarControl() {
        rc.getIdCliente().setText(String.valueOf(id));
        rc.setVisible(true);
        rc.getBtnRegistroCliente().addActionListener(l -> registrarCliente());
        f.getBtnAgregarItem().addActionListener(l -> item.setVisible(true));
        cargaColumnas();
        item.getBtnRegistroItem().addActionListener(l -> registroITem());
        item.getBtnCerrarItem().addActionListener(l -> item.dispose());
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
        f.getCedulaCliente().setText(cedulaCLiente);
        f.getTelefonoFactura().setText("0982122184");
        int cod = hashCode();
        f.getRucFactura().setText(String.valueOf(cod));
        DateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss z");

        String date = dateFormat.format(new Date());
        f.getFechaFactura().setText(date);
    }

    public void registroITem() {

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
        double subtotal = fac.subTotal();
        double total = fac.total();
        double Piva = fac.iva();

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

    public void limpiarCamposItem() {
        item.getDetalleItem().setText("");
        item.getCantidadItem().setText("");
        item.getPrecioItem().setText("");
        if (item.getRadioIVA().isSelected()) {
            item.getRadioIVA().setSelected(false);
        }
    }

}
