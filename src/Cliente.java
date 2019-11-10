import java.sql.SQLOutput;
import java.time.LocalDate;
import java.util.Scanner;

public class Cliente extends Ator{

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

    public void transportesDisponiveis(Servico servico,AtorDB atordb){      //falta filtrar para pessoas e bus se transporta ou nao crianças, alterar metodo equals nos dois serviços
        if(servico != null) {
                System.out.println("Transportadoras Disponiveis:");
                atordb.getUtilizadores().values().stream()
                        .filter(e -> e instanceof Transportes)
                        .filter(e -> ((Transportes) e).getServico().equals(servico))
                        .filter(e -> ((Transportes) e).isDisponivel())
                        .filter(e -> ((Transportes) e).getServico().getLimiteT() >= servico.getLimiteT())
                        .filter(e -> ((Transportes) e).getAutonomia() >= ((Transportes) e).getAutonomia())
                        .forEach(s -> System.out.println(s.getNome()));
            }
    }

    public Ator transporteMaisBarato(Servico servico, AtorDB atordb){
            double barato = atordb.getUtilizadores().values().stream()
                    .filter(e -> e instanceof Transportes)
                    .filter(e -> ((Transportes) e).getServico().equals(servico))
                    .filter(e -> ((Transportes) e).isDisponivel())
                    .filter(e -> ((Transportes) e).getServico().getLimiteT() >= servico.getLimiteT())
                    .filter(e -> ((Transportes) e).getAutonomia() >= ((Transportes) e).getAutonomia())
                    .map(e -> ((Transportes) e).trajetoPreco((Transportes) e, this))
                    .sorted().findFirst().get();

            return atordb.getUtilizadores().values().stream()
                    .filter(e -> e instanceof Transportes)
                    .filter(e -> (((Transportes) e).trajetoPreco((Transportes) e, this)) == barato)
                    .findFirst().get();

    }

    public Ator transporteMaisRapido(Servico servico,AtorDB atordb){
        double rapido = atordb.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e -> ((Transportes) e).getServico().equals(servico))
                .filter(e -> ((Transportes) e).isDisponivel())
                .filter(e -> ((Transportes) e).getServico().getLimiteT() >= servico.getLimiteT())
                .map(e -> ((Transportes) e).trajetoTempo((Transportes) e, this))
                .sorted().findFirst().get();

        return atordb.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e -> (((Transportes) e).trajetoTempo((Transportes) e,this)) == rapido)
                .findFirst().get();
    }




    public void AddPedido(Ator b, Servico servico)
    {
        Pedido novo= new Pedido(servico);
       this.getHistorico().getPedidos().add(novo);
       b.getHistorico().getPedidos().add(novo);

    }


    public static Servico escolherServicoC (String a)
    {
        Scanner ler = new Scanner(System.in);
        Servico novo;
        while(true)
        {
            if (a.equalsIgnoreCase("Pessoas")) {
                System.out.println("Quantas pessoas são para transportar?");
                int limit= ler.nextInt();
                System.out.println("Pretende levar consigo crianças?");
                boolean criancas= false;
                if(ler.next().equalsIgnoreCase("Sim"))
                    criancas=true;
                novo = new SPessoas(limit,criancas);
                break;
            }
            else if (a.equalsIgnoreCase("Bus")) {

                System.out.println("Quantas pessoas são para transportar?");
                int limit= ler.nextInt();
                System.out.println("Pretende levar consigo crianças?");
                boolean criancas= false;
                if(ler.next().equalsIgnoreCase("Sim"))
                    criancas=true;
                novo = new SBus(limit, criancas);
                break; }
            else if (a.equalsIgnoreCase("Big")) {
                System.out.println("Total de carga que pretende transportar?");
                int limit= (int)ler.nextInt();
                novo = new SBig(limit);
                break;
            }
            else if (a.equalsIgnoreCase("Urgentes")) {
                System.out.println("Total de produtos que pretende transportar?");
                int limit= (int)ler.nextInt();
                novo= new SUrgentes(limit);
                break;
            }
            else if (a.equalsIgnoreCase("Refeições")) {
                System.out.println("Quantas refeições pretende transportar?");
                int limit= (int)ler.nextInt();
                novo= new SRefeicoes(limit);
                break;
            }
            else{ novo=null;break; }
        }
        return novo;
    }
}
