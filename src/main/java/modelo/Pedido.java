package modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Pedido {
    private final int numeroPedido;
    private final String cliente;
    private final LocalDate fecha;
    private EstadoPedido estado;

    public Pedido(int numeroPedido, String cliente, LocalDate fecha, EstadoPedido estado) {
        this.numeroPedido = numeroPedido;
        this.cliente = Objects.requireNonNull(cliente, "El cliente no puede ser null");
        this.fecha = Objects.requireNonNull(fecha, "La fecha no puede ser null");
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser null");
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser null");
    }

    @Override
    public String toString() {
        return "Pedido #" + numeroPedido
                + " | Cliente: " + cliente
                + " | Fecha: " + fecha
                + " | Estado: " + estado;
    }
}
