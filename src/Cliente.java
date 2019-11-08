import java.time.LocalDateTime;
import java.util.ArrayList;

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
        atordb.getUtilizadores().entrySet().stream()
                .filter(e -> e.getValue() instanceof Transportes)
                .filter(e -> ((Transportes) e.getValue()).getServico().equals(servico))
                .filter(e -> ((Transportes) e.getValue()).isDisponivel() == true)
                .forEach(s -> System.out.println(s.getValue().getNome()));
    }

    public Ator transporteMaisBarato(Servico servico, AtorDB atordb){
        /*atordb.getUtilizadores().entrySet().stream()
                .filter(e -> e.getValue() instanceof  Transportes)
                .filter(e -> ((Transportes) e.getValue()).getServico().equals(servico))
                .filter()*/
        return  null;
    }

}
