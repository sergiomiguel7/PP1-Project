import java.util.ArrayList;

public class Historico {
    private ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
