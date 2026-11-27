package estructura;

import modelo.Pedido;

public class NodoPedido {
    private final Pedido pedido;
    private NodoPedido izquierdo;
    private NodoPedido derecho;

    public NodoPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public NodoPedido getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoPedido izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoPedido getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoPedido derecho) {
        this.derecho = derecho;
    }
}
