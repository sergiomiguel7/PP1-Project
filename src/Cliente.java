import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Cliente extends Ator{

    private static final long serialVersionUID = 3L;

    //construtores
    public Cliente()
    {
        super(" "," "," "," ", LocalDate.now(), 0, 0);
        super.setHistorico(new Historico());
    }

    public Cliente(String email, String nome , String password, String morada, LocalDate dataN, int x , int y)
    {
        super(email, nome, password, morada, dataN, x, y);
        super.setHistorico(new Historico());

    }
    public Cliente(Cliente cliente)
    {
        super(cliente.getEmail(), cliente.getNome(),cliente.getPassword(),cliente.getPassword(),cliente.getDataN(),cliente.getX(),cliente.getY());
        super.getHistorico();
    }

    //getters e setters
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\nDados:\n" + super.toString());
        return s.toString();
    }


    public void AddPedido(Ator b, Servico servico)
    {
        if(((Transportes)b).isDisponivel()) {
            Pedido novo = new Pedido(servico, LocalDateTime.now(), LocalDateTime.now().plusMinutes(((Transportes) b).trajetoTempo(((Transportes) b), this)), ((Transportes) b).trajetoPreco(this));
            this.getHistorico().getPedidos().add(novo);
            b.getHistorico().getPedidos().add(novo);
            ((Transportes) b).setDisponivel(false);
        }
    }


    public double faturadoIntervaloTempo(Transportes transportes,LocalDateTime inicio,LocalDateTime fim){
        List<Pedido> pedidosconc = transportes.getHistorico().getPedidosConcluidos();

        return pedidosconc.stream()
            .filter(pedido -> pedido.getDataFim().isAfter(inicio))
            .filter(pedido -> pedido.getDataFim().isBefore(fim))
            .mapToDouble(pedido -> Math.round(pedido.getPreco()))
            .sum();
    }

    public void maximoTempoCusto(AtorDB db,long tempo, double custo,Servico servico){
        db.getUtilizadores().values().stream().filter(e -> e instanceof Transportes)
                .filter(e -> ((Transportes)e).getServico().equals(servico))
                .filter(e -> ((Transportes)e).isDisponivel())
                .filter(e -> ((Transportes)e).getAutonomia() >= ((Transportes) e).distanciaXY(this,((Transportes)e)))
                .filter(e -> ((Transportes) e).trajetoPreco(this) <= custo)
                .filter(e -> ((Transportes)e).trajetoTempoTeorico(((Transportes)e),this) <= tempo)
                .forEach(e -> System.out.println(e.getNome()));
    }



    public void atualizarCoordenadas(double x, double y,AtorDB db){
        db.getUtilizadores().values().stream().filter(e ->e instanceof Transportes)
                .forEach(e -> e.setX(x));
        db.getUtilizadores().values().stream().filter(e ->e instanceof Transportes)
                .forEach(e -> e.setY(y));
    }




}
