import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Cliente extends Ator{

    //construtores
    public Cliente()
    {
        super(" "," "," "," ", LocalDateTime.now(), 0, 0);
        super.setHistorico(new Historico());
    }

    public Cliente(String email, String nome , String password, String morada, LocalDateTime dataN, int x , int y)
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
        s.append("\nDados:" + super.toString());
        return s.toString();
    }

    public void transportesDisponiveis(Servico servico,AtorDB atordb){
        atordb.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e -> ((Transportes) e).getServico().equals(servico) )
                .forEach(s -> System.out.println(s.getNome()));
    }

    public Ator transporteMaisBarato(Servico servico, AtorDB atordb){

        double barato= atordb.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e-> ((Transportes) e).getServico().equals(servico))
                .map(e->((Transportes) e).trajetoPreco((Transportes) e, this, atordb))
                .sorted().findFirst().get();

        return atordb.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e-> (((Transportes) e).trajetoPreco((Transportes)e, this, atordb))==barato)
                .findFirst().get();
    }

}
