package estructura;

import modelo.EstadoPedido;
import modelo.Pedido;

import java.util.ArrayList;
import java.util.List;

public class ArbolPedidos {
    private NodoPedido raiz;

    public void insertar(Pedido pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido no puede ser null.");
        }
        raiz = insertarRecursivo(raiz, pedido);
    }

    // Cada llamada elige el subárbol correspondiente hasta encontrar una posición libre.
    private NodoPedido insertarRecursivo(NodoPedido actual, Pedido pedido) {
        if (actual == null) {
            return new NodoPedido(pedido);
        }

        int comparacion = Integer.compare(
                pedido.getNumeroPedido(), actual.getPedido().getNumeroPedido());
        if (comparacion < 0) {
            actual.setIzquierdo(insertarRecursivo(actual.getIzquierdo(), pedido));
        } else if (comparacion > 0) {
            actual.setDerecho(insertarRecursivo(actual.getDerecho(), pedido));
        } else {
            throw new IllegalArgumentException(
                    "Ya existe un pedido con el número " + pedido.getNumeroPedido() + ".");
        }
        return actual;
    }

    public Pedido buscar(int numeroPedido) {
        return buscarRecursivo(raiz, numeroPedido);
    }

    // La comparación con la clave guía la búsqueda recursiva por una sola rama.
    private Pedido buscarRecursivo(NodoPedido actual, int numeroPedido) {
        if (actual == null) {
            return null;
        }

        int numeroActual = actual.getPedido().getNumeroPedido();
        if (numeroPedido == numeroActual) {
            return actual.getPedido();
        }
        return numeroPedido < numeroActual
                ? buscarRecursivo(actual.getIzquierdo(), numeroPedido)
                : buscarRecursivo(actual.getDerecho(), numeroPedido);
    }

    public boolean actualizarEstado(int numeroPedido, EstadoPedido nuevo) {
        if (nuevo == null) {
            throw new IllegalArgumentException("El nuevo estado no puede ser null.");
        }
        Pedido pedido = buscar(numeroPedido);
        if (pedido == null) {
            return false;
        }
        pedido.setEstado(nuevo);
        return true;
    }

    public void mostrarInorden() {
        if (estaVacio()) {
            System.out.println("No hay pedidos registrados.");
            return;
        }
        mostrarInordenRecursivo(raiz);
    }

    public List<Pedido> obtenerPedidosInorden() {
        List<Pedido> pedidos = new ArrayList<>();
        obtenerPedidosInordenRecursivo(raiz, pedidos);
        return pedidos;
    }

    private void obtenerPedidosInordenRecursivo(NodoPedido actual, List<Pedido> pedidos) {
        if (actual != null) {
            obtenerPedidosInordenRecursivo(actual.getIzquierdo(), pedidos);
            pedidos.add(actual.getPedido());
            obtenerPedidosInordenRecursivo(actual.getDerecho(), pedidos);
        }
    }

    private void mostrarInordenRecursivo(NodoPedido actual) {
        if (actual != null) {
            mostrarInordenRecursivo(actual.getIzquierdo());
            System.out.println(actual.getPedido());
            mostrarInordenRecursivo(actual.getDerecho());
        }
    }

    public boolean eliminar(int numeroPedido) {
        if (buscar(numeroPedido) == null) {
            return false;
        }
        raiz = eliminarRecursivo(raiz, numeroPedido);
        return true;
    }

    // Se desciende por la clave; al encontrar el nodo se resuelven sus casos de eliminación.
    private NodoPedido eliminarRecursivo(NodoPedido actual, int numeroPedido) {
        if (actual == null) {
            return null;
        }

        int numeroActual = actual.getPedido().getNumeroPedido();
        if (numeroPedido < numeroActual) {
            actual.setIzquierdo(eliminarRecursivo(actual.getIzquierdo(), numeroPedido));
        } else if (numeroPedido > numeroActual) {
            actual.setDerecho(eliminarRecursivo(actual.getDerecho(), numeroPedido));
        } else {
            if (actual.getIzquierdo() == null) {
                return actual.getDerecho();
            }
            if (actual.getDerecho() == null) {
                return actual.getIzquierdo();
            }

            NodoPedido izquierdo = actual.getIzquierdo();
            NodoPedido derecho = actual.getDerecho();
            NodoPedido sucesor = encontrarMinimo(derecho);
            NodoPedido nuevoDerecho = eliminarMinimo(derecho);
            sucesor.setIzquierdo(izquierdo);
            sucesor.setDerecho(nuevoDerecho);
            return sucesor;
        }
        return actual;
    }

    private NodoPedido encontrarMinimo(NodoPedido actual) {
        while (actual.getIzquierdo() != null) {
            actual = actual.getIzquierdo();
        }
        return actual;
    }

    private NodoPedido eliminarMinimo(NodoPedido actual) {
        if (actual.getIzquierdo() == null) {
            return actual.getDerecho();
        }
        actual.setIzquierdo(eliminarMinimo(actual.getIzquierdo()));
        return actual;
    }

    public boolean estaVacio() {
        return raiz == null;
    }
}
