package app;

import estructura.ArbolPedidos;
import modelo.EstadoPedido;
import modelo.Pedido;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final ArbolPedidos ARBOL = new ArbolPedidos();

    public static void main(String[] args) {
        precargarPedidos();
        System.out.println("Sistema de administración de pedidos");
        System.out.println("Se precargaron 5 pedidos de prueba.");

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            try {
                int opcion = leerEntero("Seleccione una opción: ");
                switch (opcion) {
                    case 1 -> registrarPedido();
                    case 2 -> buscarPedido();
                    case 3 -> actualizarEstado();
                    case 4 -> ARBOL.mostrarInorden();
                    case 5 -> eliminarPedido();
                    case 6 -> {
                        continuar = false;
                        System.out.println("Hasta luego.");
                    }
                    default -> System.out.println("Opción inválida. Elija un número del 1 al 6.");
                }
            } catch (FinEntradaException ex) {
                continuar = false;
                System.out.println("\nEntrada finalizada.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú ---");
        System.out.println("1. Registrar e insertar pedido");
        System.out.println("2. Buscar pedido");
        System.out.println("3. Actualizar estado");
        System.out.println("4. Mostrar pedidos ordenados");
        System.out.println("5. Eliminar pedido");
        System.out.println("6. Salir");
    }

    private static void registrarPedido() {
        int numero = leerEntero("Número de pedido: ");
        String cliente = leerTextoNoVacio();
        LocalDate fecha = leerFecha();
        EstadoPedido estado = leerEstado();

        try {
            ARBOL.insertar(new Pedido(numero, cliente, fecha, estado));
            System.out.println("Pedido registrado correctamente.");
        } catch (IllegalArgumentException ex) {
            System.out.println("No se pudo registrar: " + ex.getMessage());
        }
    }

    private static void buscarPedido() {
        int numero = leerEntero("Número de pedido a buscar: ");
        Pedido pedido = ARBOL.buscar(numero);
        System.out.println(pedido == null ? "No se encontró el pedido." : pedido);
    }

    private static void actualizarEstado() {
        int numero = leerEntero("Número de pedido: ");
        EstadoPedido estado = leerEstado();
        if (ARBOL.actualizarEstado(numero, estado)) {
            System.out.println("Estado actualizado correctamente.");
        } else {
            System.out.println("No se encontró el pedido.");
        }
    }

    private static void eliminarPedido() {
        int numero = leerEntero("Número de pedido a eliminar: ");
        System.out.println(ARBOL.eliminar(numero)
                ? "Pedido eliminado correctamente."
                : "No se encontró el pedido.");
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            String entrada = leerLinea(mensaje);
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Entrada inválida. Ingrese un número entero.");
            }
        }
    }

    private static String leerTextoNoVacio() {
        while (true) {
            String texto = leerLinea("Cliente: ");
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.print("El cliente no puede quedar vacío. Intente de nuevo: ");
        }
    }

    private static LocalDate leerFecha() {
        while (true) {
            String entrada = leerLinea("Fecha (yyyy-MM-dd): ");
            try {
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException ex) {
                System.out.println("Fecha inválida. Use el formato yyyy-MM-dd.");
            }
        }
    }

    private static EstadoPedido leerEstado() {
        while (true) {
            String entrada = leerLinea(
                    "Estado (PENDIENTE, PREPARANDO, ENVIADO, ENTREGADO): ").toUpperCase(Locale.ROOT);
            try {
                return EstadoPedido.valueOf(entrada);
            } catch (IllegalArgumentException ex) {
                System.out.println("Estado inexistente. Intente de nuevo.");
            }
        }
    }

    private static String leerLinea(String mensaje) {
        System.out.print(mensaje);
        if (!SCANNER.hasNextLine()) {
            throw new FinEntradaException();
        }
        return SCANNER.nextLine().trim();
    }

    private static void precargarPedidos() {
        ARBOL.insertar(new Pedido(1042, "Ana García", LocalDate.of(2026, 11, 20),
                EstadoPedido.PENDIENTE));
        ARBOL.insertar(new Pedido(1015, "Luis Pérez", LocalDate.of(2026, 11, 21),
                EstadoPedido.PREPARANDO));
        ARBOL.insertar(new Pedido(1088, "Marta López", LocalDate.of(2026, 11, 22),
                EstadoPedido.ENVIADO));
        ARBOL.insertar(new Pedido(1003, "Carlos Ruiz", LocalDate.of(2026, 11, 23),
                EstadoPedido.ENTREGADO));
        ARBOL.insertar(new Pedido(1061, "Sofía Díaz", LocalDate.of(2026, 11, 24),
                EstadoPedido.PENDIENTE));
    }

    private static class FinEntradaException extends RuntimeException {
    }
}
