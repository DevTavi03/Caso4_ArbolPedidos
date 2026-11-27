package app;

import estructura.ArbolPedidos;
import modelo.EstadoPedido;
import modelo.Pedido;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class InterfazPedidos extends JFrame {
    private static final Color FONDO = new Color(0xF3F6FA);
    private static final Color AZUL_OSCURO = new Color(0x142B4A);
    private static final Color AZUL = new Color(0x2563EB);
    private static final Color TEXTO = new Color(0x1E293B);
    private static final Color TEXTO_SECUNDARIO = new Color(0x64748B);
    private static final Color BORDE = new Color(0xDCE4EE);

    private final ArbolPedidos arbol;
    private final JTextField numeroCampo = new JTextField();
    private final JTextField clienteCampo = new JTextField();
    private final JTextField fechaCampo = new JTextField();
    private final JComboBox<EstadoPedido> estadoCampo = new JComboBox<>(EstadoPedido.values());
    private final JLabel contador = new JLabel();
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
            new Object[]{"N.º DE PEDIDO", "CLIENTE", "FECHA", "ESTADO"}, 0) {
        @Override
        public boolean isCellEditable(int fila, int columna) {
            return false;
        }
    };
    private final JTable tabla = new JTable(modeloTabla);

    public InterfazPedidos(ArbolPedidos arbol) {
        super("Pedidos | Tienda Online");
        this.arbol = arbol;
        configurarVentana();
        actualizarTabla();
    }

    private void configurarVentana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 730);
        setMinimumSize(new Dimension(900, 650));
        setLocationRelativeTo(null);

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(FONDO);
        raiz.add(crearEncabezado(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(0, 18));
        contenido.setOpaque(false);
        contenido.setBorder(BorderFactory.createEmptyBorder(22, 28, 24, 28));
        contenido.add(crearPanelFormulario(), BorderLayout.NORTH);
        contenido.add(crearPanelPedidos(), BorderLayout.CENTER);
        raiz.add(contenido, BorderLayout.CENTER);

        setContentPane(raiz);
    }

    private JPanel crearEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(AZUL_OSCURO);
        encabezado.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));

        JPanel titulos = new JPanel(new BorderLayout(0, 5));
        titulos.setOpaque(false);
        JLabel marca = new JLabel("GESTIÓN DE PEDIDOS");
        marca.setFont(new Font("Segoe UI", Font.BOLD, 22));
        marca.setForeground(Color.WHITE);
        JLabel subtitulo = new JLabel("Control sencillo y ordenado de tus pedidos");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(0xC5D3E5));
        titulos.add(marca, BorderLayout.NORTH);
        titulos.add(subtitulo, BorderLayout.SOUTH);
        encabezado.add(titulos, BorderLayout.WEST);

        JLabel etiqueta = new JLabel("TIENDA ONLINE");
        etiqueta.setFont(new Font("Segoe UI", Font.BOLD, 11));
        etiqueta.setForeground(new Color(0xDCE8F7));
        etiqueta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x48617F)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)));
        encabezado.add(etiqueta, BorderLayout.EAST);
        return encabezado;
    }

    private JPanel crearPanelFormulario() {
        JPanel tarjeta = crearTarjeta();
        tarjeta.setLayout(new BorderLayout(0, 16));

        JPanel titulo = new JPanel(new BorderLayout(0, 4));
        titulo.setOpaque(false);
        JLabel encabezado = new JLabel("Registrar o editar pedido");
        encabezado.setFont(new Font("Segoe UI", Font.BOLD, 17));
        encabezado.setForeground(TEXTO);
        JLabel ayuda = new JLabel("Completa los datos y elige una acción para administrar el pedido.");
        ayuda.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ayuda.setForeground(TEXTO_SECUNDARIO);
        titulo.add(encabezado, BorderLayout.NORTH);
        titulo.add(ayuda, BorderLayout.SOUTH);
        tarjeta.add(titulo, BorderLayout.NORTH);

        JPanel campos = new JPanel(new GridBagLayout());
        campos.setOpaque(false);
        agregarCampo(campos, "Número de pedido", numeroCampo, 0);
        agregarCampo(campos, "Cliente", clienteCampo, 1);
        agregarCampo(campos, "Fecha · AAAA-MM-DD", fechaCampo, 2);
        agregarCampo(campos, "Estado", estadoCampo, 3);
        tarjeta.add(campos, BorderLayout.CENTER);

        JButton registrar = crearBoton("Registrar pedido", AZUL, Color.WHITE);
        JButton buscar = crearBoton("Buscar", new Color(0xEAF1FF), AZUL);
        JButton actualizar = crearBoton("Actualizar estado", new Color(0xEAF1FF), AZUL);
        JButton eliminar = crearBoton("Eliminar", new Color(0xFFF0EF), new Color(0xB42318));
        JButton limpiar = crearBoton("Limpiar", new Color(0xF1F5F9), new Color(0x475569));

        JPanel acciones = new JPanel(new FlowLayout(FlowLayout.LEFT, 9, 0));
        acciones.setOpaque(false);
        acciones.add(registrar);
        acciones.add(buscar);
        acciones.add(actualizar);
        acciones.add(eliminar);
        acciones.add(limpiar);
        tarjeta.add(acciones, BorderLayout.SOUTH);

        registrar.addActionListener(evento -> registrarPedido());
        buscar.addActionListener(evento -> buscarPedido());
        actualizar.addActionListener(evento -> actualizarEstado());
        eliminar.addActionListener(evento -> eliminarPedido());
        limpiar.addActionListener(evento -> limpiarFormulario());
        return tarjeta;
    }

    private void agregarCampo(JPanel panel, String etiqueta, java.awt.Component campo, int columna) {
        GridBagConstraints restricciones = new GridBagConstraints();
        restricciones.gridx = columna;
        restricciones.gridy = 0;
        restricciones.weightx = 1;
        restricciones.fill = GridBagConstraints.HORIZONTAL;
        restricciones.insets = new Insets(0, columna == 0 ? 0 : 12, 6, 0);
        JLabel titulo = new JLabel(etiqueta);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titulo.setForeground(new Color(0x475569));
        panel.add(titulo, restricciones);

        prepararCampo(campo);
        restricciones.gridy = 1;
        restricciones.insets = new Insets(0, columna == 0 ? 0 : 12, 0, 0);
        panel.add(campo, restricciones);
    }

    private void prepararCampo(java.awt.Component campo) {
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setForeground(TEXTO);
        campo.setBackground(Color.WHITE);
        campo.setPreferredSize(new Dimension(150, 38));
        if (campo instanceof JTextField) {
            ((JTextField) campo).setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(BORDE),
                    BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        } else {
            ((JComboBox<?>) campo).setBorder(BorderFactory.createLineBorder(BORDE));
        }
    }

    private JPanel crearPanelPedidos() {
        JPanel tarjeta = crearTarjeta();
        tarjeta.setLayout(new BorderLayout(0, 14));

        JPanel encabezado = new JPanel(new BorderLayout(12, 4));
        encabezado.setOpaque(false);
        JPanel titulos = new JPanel(new BorderLayout(0, 4));
        titulos.setOpaque(false);
        JLabel titulo = new JLabel("Pedidos registrados");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 17));
        titulo.setForeground(TEXTO);
        JLabel detalle = new JLabel("Listado ordenado por número de pedido");
        detalle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detalle.setForeground(TEXTO_SECUNDARIO);
        titulos.add(titulo, BorderLayout.NORTH);
        titulos.add(detalle, BorderLayout.SOUTH);

        contador.setFont(new Font("Segoe UI", Font.BOLD, 12));
        contador.setForeground(AZUL);
        contador.setHorizontalAlignment(SwingConstants.RIGHT);
        encabezado.add(titulos, BorderLayout.WEST);
        encabezado.add(contador, BorderLayout.EAST);
        tarjeta.add(encabezado, BorderLayout.NORTH);

        configurarTabla();
        JScrollPane desplazamiento = new JScrollPane(tabla);
        desplazamiento.setBorder(BorderFactory.createLineBorder(BORDE));
        desplazamiento.getViewport().setBackground(Color.WHITE);
        tarjeta.add(desplazamiento, BorderLayout.CENTER);

        JLabel pie = new JLabel("Selecciona una fila para cargar sus datos en el formulario.");
        pie.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        pie.setForeground(TEXTO_SECUNDARIO);
        tarjeta.add(pie, BorderLayout.SOUTH);

        tabla.getSelectionModel().addListSelectionListener(evento -> {
            if (!evento.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                cargarPedidoSeleccionado();
            }
        });
        return tarjeta;
    }

    private void configurarTabla() {
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setFillsViewportHeight(true);
        tabla.setRowHeight(40);
        tabla.setShowGrid(false);
        tabla.setIntercellSpacing(new Dimension(0, 0));
        tabla.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tabla.setForeground(TEXTO);
        tabla.setSelectionBackground(new Color(0xEAF1FF));
        tabla.setSelectionForeground(TEXTO);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.getTableHeader().setPreferredSize(new Dimension(0, 38));
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tabla.getTableHeader().setForeground(new Color(0x475569));
        tabla.getTableHeader().setBackground(new Color(0xF8FAFC));
        tabla.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDE));

        DefaultTableCellRenderer centrado = new DefaultTableCellRenderer();
        centrado.setHorizontalAlignment(SwingConstants.CENTER);
        tabla.getColumnModel().getColumn(0).setCellRenderer(centrado);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(150);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(300);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(3).setCellRenderer(new EstadoCellRenderer());
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
    }

    private JPanel crearTarjeta() {
        return new PanelTarjeta();
    }

    private JButton crearBoton(String texto, Color fondo, Color textoColor) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setForeground(textoColor);
        boton.setBackground(fondo);
        boton.setBorder(BorderFactory.createEmptyBorder(11, 15, 11, 15));
        boton.setFocusPainted(false);
        boton.setOpaque(true);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Color fondoSobre = fondo.equals(AZUL) ? new Color(0x1D4ED8) : new Color(0xDDE7F3);
        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evento) {
                boton.setBackground(fondoSobre);
            }

            @Override
            public void mouseExited(MouseEvent evento) {
                boton.setBackground(fondo);
            }
        });
        return boton;
    }

    private void registrarPedido() {
        try {
            Pedido pedido = leerPedidoFormulario();
            arbol.insertar(pedido);
            actualizarTabla();
            limpiarFormulario();
            mostrarMensaje("Pedido registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            mostrarError(ex.getMessage());
        }
    }

    private void buscarPedido() {
        Integer numero = leerNumero();
        if (numero == null) {
            return;
        }

        Pedido pedido = arbol.buscar(numero);
        if (pedido == null) {
            mostrarError("No se encontró el pedido " + numero + ".");
            return;
        }
        mostrarPedidoEnFormulario(pedido);
        seleccionarFila(numero);
    }

    private void actualizarEstado() {
        Integer numero = leerNumero();
        if (numero == null) {
            return;
        }

        EstadoPedido estado = (EstadoPedido) estadoCampo.getSelectedItem();
        if (estado == null) {
            mostrarError("Seleccione un estado válido.");
            return;
        }
        if (!arbol.actualizarEstado(numero, estado)) {
            mostrarError("No se encontró el pedido " + numero + ".");
            return;
        }
        actualizarTabla();
        mostrarMensaje("Estado actualizado correctamente.");
    }

    private void eliminarPedido() {
        Integer numero = leerNumero();
        if (numero == null) {
            return;
        }

        if (!arbol.eliminar(numero)) {
            mostrarError("No se encontró el pedido " + numero + ".");
            return;
        }
        actualizarTabla();
        limpiarFormulario();
        mostrarMensaje("Pedido eliminado correctamente.");
    }

    private Pedido leerPedidoFormulario() {
        int numero;
        try {
            numero = Integer.parseInt(numeroCampo.getText().trim());
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("El número de pedido debe ser un entero.");
        }

        String cliente = clienteCampo.getText().trim();
        if (cliente.isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }

        LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaCampo.getText().trim());
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("La fecha debe tener el formato yyyy-MM-dd.");
        }

        EstadoPedido estado = (EstadoPedido) estadoCampo.getSelectedItem();
        if (estado == null) {
            throw new IllegalArgumentException("Seleccione un estado válido.");
        }
        return new Pedido(numero, cliente, fecha, estado);
    }

    private Integer leerNumero() {
        try {
            return Integer.parseInt(numeroCampo.getText().trim());
        } catch (NumberFormatException ex) {
            mostrarError("Ingrese un número de pedido entero.");
            return null;
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Pedido pedido : arbol.obtenerPedidosInorden()) {
            modeloTabla.addRow(new Object[]{
                    pedido.getNumeroPedido(),
                    pedido.getCliente(),
                    pedido.getFecha(),
                    pedido.getEstado()
            });
        }
        contador.setText(modeloTabla.getRowCount() + " pedidos");
    }

    private void cargarPedidoSeleccionado() {
        int fila = tabla.getSelectedRow();
        int numero = (int) modeloTabla.getValueAt(fila, 0);
        Pedido pedido = arbol.buscar(numero);
        if (pedido != null) {
            mostrarPedidoEnFormulario(pedido);
        }
    }

    private void mostrarPedidoEnFormulario(Pedido pedido) {
        numeroCampo.setText(Integer.toString(pedido.getNumeroPedido()));
        clienteCampo.setText(pedido.getCliente());
        fechaCampo.setText(pedido.getFecha().toString());
        estadoCampo.setSelectedItem(pedido.getEstado());
    }

    private void seleccionarFila(int numero) {
        for (int fila = 0; fila < modeloTabla.getRowCount(); fila++) {
            if ((int) modeloTabla.getValueAt(fila, 0) == numero) {
                tabla.setRowSelectionInterval(fila, fila);
                return;
            }
        }
    }

    private void limpiarFormulario() {
        numeroCampo.setText("");
        clienteCampo.setText("");
        fechaCampo.setText("");
        estadoCampo.setSelectedItem(EstadoPedido.PENDIENTE);
        tabla.clearSelection();
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Pedidos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static class PanelTarjeta extends JPanel {
        private PanelTarjeta() {
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(20, 22, 18, 22));
        }

        @Override
        protected void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D) graphics.create();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g.setColor(BORDE);
            g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
            g.dispose();
            super.paintComponent(graphics);
        }
    }

    private static class EstadoCellRenderer extends DefaultTableCellRenderer {
        @Override
        public java.awt.Component getTableCellRendererComponent(
                JTable tabla, Object valor, boolean seleccionado, boolean tieneFoco,
                int fila, int columna) {
            java.awt.Component componente = super.getTableCellRendererComponent(
                    tabla, valor, seleccionado, tieneFoco, fila, columna);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Segoe UI", Font.BOLD, 12));
            if (!seleccionado && valor instanceof EstadoPedido) {
                switch ((EstadoPedido) valor) {
                    case PENDIENTE -> setForeground(new Color(0xB45309));
                    case PREPARANDO -> setForeground(new Color(0x1D4ED8));
                    case ENVIADO -> setForeground(new Color(0x6D28D9));
                    case ENTREGADO -> setForeground(new Color(0x15803D));
                }
            }
            return componente;
        }
    }
}
