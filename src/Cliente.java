import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collector;
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

    /**
     * Método maxiomoTempoCusto
     * Recebe um serviço, um tempo e custo maximo e transforma em um iterator com todos os transportes
     * que são capazes de efetuar o serviço nessas condições
     * */
    public Iterator<Transportes> maximoTempoCusto(AtorDB db,long tempo, double custo,Servico servico){
        return  db.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e -> ((Transportes)e).getServico().equals(servico))
                .filter(e -> ((Transportes)e).isDisponivel())
                .filter(e -> ((Transportes)e).getAutonomia() >= ((Transportes) e).distanciaXY(this,((Transportes)e)))
                .filter(e -> ((Transportes) e).trajetoPreco(this) <= custo)
                .filter(e -> ((Transportes)e).trajetoTempoTeorico(((Transportes)e),this) <= tempo)
                .map(e -> (Transportes)e)
                .iterator();
    }


    /**
     * Método atualizar coordenadas
     * Coloca todos as transportadoras disponiveis nas coordenadas inseridas pelo cliente
     * */
    public void atualizarCoordenadas(double x, double y,AtorDB db){
        db.getUtilizadores().values().stream().filter(e ->e instanceof Transportes)
                .filter(e -> ((Transportes) e).isDisponivel())
                .forEach(e -> e.setX(x));
        db.getUtilizadores().values().stream().filter(e ->e instanceof Transportes)
                .filter(e -> ((Transportes) e).isDisponivel())
                .forEach(e -> e.setY(y));
    }

    /**
     * Método semRepetidos
     * Recebe o historico de um utilizador e devolve todas as transportadoras que já utilizou
     */
    public TreeSet<Transportes> semRepetidos(AtorDB db, Historico historico)
    {
        Comparator byNome= Comparator.comparing(Transportes::getNome).reversed();
        List<Pedido> pedidos = this.getHistorico().getPedidosConcluidos().stream().collect(Collectors.toList());
        List<Ator> transportes =  db.getUtilizadores().values().stream().filter(t -> t instanceof Transportes).collect(Collectors.toList());
        List<Transportes> rep = new ArrayList<>();
        for(Ator t : transportes){
            for(Pedido p : pedidos)
                if(t.getHistorico().getPedidosConcluidos().contains(p))
                    rep.add(((Transportes)t));

        }

        TreeSet novo= new TreeSet<Transportes>(byNome); novo.addAll(rep);
        return (TreeSet<Transportes>) novo;

    }



}
