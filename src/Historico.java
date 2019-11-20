
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    public Pedido getPedido(List<Pedido> concluidos)
    {
        Scanner ler = new Scanner(System.in);
        int escolhido=0;
        int i=1;
        for(Pedido p : concluidos)
        {
            System.out.println(i+" Serviço:"+p.getServico()+"\nPreço:"+p.getPreco());
            i++;
        }
        System.out.println("Escolha o numero do serviço que pretende repetir se mudou de ideias escreva 0.");
        escolhido=ler.nextInt();
        if(escolhido>0)
            return concluidos.get(escolhido-1);
        else
            return null;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Lista de Pedidos:").append(this.pedidos);
        return sb.toString();
    }



}
