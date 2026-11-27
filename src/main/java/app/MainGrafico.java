package app;

import estructura.ArbolPedidos;
import modelo.EstadoPedido;
import modelo.Pedido;

import javax.swing.SwingUtilities;
import java.time.LocalDate;

public class MainGrafico {
    public static void main(String[] args) {
        ArbolPedidos arbol = new ArbolPedidos();
        precargarPedidos(arbol);
        SwingUtilities.invokeLater(() -> new InterfazPedidos(arbol).setVisible(true));
    }

    private static void precargarPedidos(ArbolPedidos arbol) {
        arbol.insertar(new Pedido(1042, "Ana García", LocalDate.of(2026, 11, 20),
                EstadoPedido.PENDIENTE));
        arbol.insertar(new Pedido(1015, "Luis Pérez", LocalDate.of(2026, 11, 21),
                EstadoPedido.PREPARANDO));
        arbol.insertar(new Pedido(1088, "Marta López", LocalDate.of(2026, 11, 22),
                EstadoPedido.ENVIADO));
        arbol.insertar(new Pedido(1003, "Carlos Ruiz", LocalDate.of(2026, 11, 23),
                EstadoPedido.ENTREGADO));
        arbol.insertar(new Pedido(1061, "Sofía Díaz", LocalDate.of(2026, 11, 24),
                EstadoPedido.PENDIENTE));
    }
}
