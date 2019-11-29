
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Historico implements Serializable {
    //atributos
    private List<Pedido> pedidos;
    private static final long serialVersionUID = 4L;

    public Historico(){
        this.pedidos = new ArrayList<Pedido>();

    }
    //getters
    public List<Pedido> getPedidos() { return pedidos; }

    public List<Pedido> getPedidosConcluidos(){
        return this.pedidos.stream().filter(pedido -> pedido.isConcluido()).collect(Collectors.toList());
    }


    //setters
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }


    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Pedidos:").append(this.pedidos);
        return sb.toString();
    }



}
