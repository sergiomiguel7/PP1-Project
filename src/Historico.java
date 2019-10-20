
import java.util.HashMap;
import java.util.Map;

public class Historico {
    //atributos
    private Map<Ator,Pedido> pedidos;


    public Historico(){
        this.pedidos = new HashMap<>();
    }
    //getters
    public Map<Ator,Pedido> getPedidos() { return pedidos; }
    //setters
    public void setPedidos(HashMap<Ator,Pedido> pedidos) { this.pedidos = pedidos; }


}
