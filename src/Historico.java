
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Historico {
    //atributos
    private List<Pedido> pedidos;


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

}
