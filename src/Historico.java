import java.util.ArrayList;

public class Historico {
    //atributos
    private ArrayList<Pedido> pedidos;


    public Historico(){
        this.pedidos = new ArrayList<Pedido>();
    }
    //getters
    public ArrayList<Pedido> getPedidos() { return pedidos; }
    //setters
    public void setPedidos(ArrayList<Pedido> pedidos) { this.pedidos = pedidos; }
    //metodos
}
