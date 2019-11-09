
import java.util.ArrayList;
import java.util.List;

public class Historico {
    //atributos
    private List<Pedido> pedidos;


    public Historico(){
        this.pedidos = new ArrayList<Pedido>();
    }
    //getters
    public List<Pedido> getPedidos() { return pedidos; }
    //setters
    public void setPedidos(List<Pedido> pedidos) { this.pedidos = pedidos; }

}
