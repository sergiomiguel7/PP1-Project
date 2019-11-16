
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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

    public void transportesDisponiveis(Servico servico,AtorDB atordb){
        if(servico != null) {
                System.out.println("Transportadoras Disponiveis:");
                atordb.getUtilizadores().values().stream()
                        .filter(e -> e instanceof Transportes)
                        .filter(e -> ((Transportes) e).getServico().equals(servico))
                        .filter(e -> ((Transportes) e).isDisponivel())
                        .filter(e -> ((Transportes) e).getServico().getLimiteT() >= servico.getLimiteT())
                        .filter(e -> ((Transportes) e).getAutonomia() >= ((Transportes) e).distanciaXY(this,(Transportes) e))
                        .forEach(s -> System.out.println(s.getNome()));
            }
    }

    public Ator transporteMaisBarato(Servico servico, AtorDB atordb){
            double barato = atordb.getUtilizadores().values().stream()
                    .filter(e -> e instanceof Transportes)
                    .filter(e -> ((Transportes) e).getServico().equals(servico))
                    .filter(e -> ((Transportes) e).isDisponivel())
                    .filter(e -> ((Transportes) e).getServico().getLimiteT() >= servico.getLimiteT())
                    .filter(e -> ((Transportes) e).getAutonomia() >= ((Transportes) e).distanciaXY(this,(Transportes) e))
                    .map(e -> ((Transportes) e).getPrecoKM())
                    .sorted()
                    .findFirst().get();

            return atordb.getUtilizadores().values().stream()
                    .filter(e -> e instanceof Transportes)
                    .filter(e -> (((Transportes) e).getPrecoKM()) == barato)
                    .findFirst().get();

    }

    public Ator transporteMaisRapido(Servico servico,AtorDB atordb){
        double rapido = atordb.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e -> ((Transportes) e).getServico().equals(servico))
                .filter(e -> ((Transportes) e).isDisponivel())
                .filter(e -> ((Transportes) e).getServico().getLimiteT() >= servico.getLimiteT())
                .filter(e -> ((Transportes) e).getAutonomia() >= ((Transportes) e).distanciaXY(this,(Transportes) e))
                .map(e -> ((Transportes) e).getTempoKM())
                .sorted().findFirst().get();

        return atordb.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e -> (((Transportes) e).getTempoKM()) == rapido)
                .findFirst().get();
    }

    public Ator transportePreco(Servico servico,double preco,AtorDB atordb){
        return atordb.getUtilizadores().values().stream()
                .filter(e -> e instanceof Transportes)
                .filter(e -> ((Transportes) e).getServico().equals(servico))
                .filter(e -> ((Transportes) e).isDisponivel())
                .filter(e -> ((Transportes) e).getServico().getLimiteT() == servico.getLimiteT())
                .filter(e -> ((Transportes) e).trajetoPreco(this) == preco)
                .sorted().findFirst().get();
    }



    public void AddPedido(Ator b, Servico servico)
    {
        Pedido novo= new Pedido(servico, LocalDateTime.now(),LocalDateTime.now().plusMinutes(((Transportes)b).trajetoTempo(((Transportes)b) , this)),((Transportes)b).trajetoPreco(this));
        this.getHistorico().getPedidos().add(novo);
        b.getHistorico().getPedidos().add(novo);

    }

    public List<Ator> maisServicosEfetuados(AtorDB atordb){

        List<Integer> valores =   atordb.getUtilizadores().values().stream()
                .filter(ator -> ator instanceof Transportes)
                .map(ator -> ((Transportes) ator).getHistorico().getPedidosConcluidos().size())
                .sorted()
                .collect(Collectors.toList());

        List<Ator> atores = new ArrayList<>();
        for(int i : valores){
            for(Ator a : atordb.getUtilizadores().values()){
                if(a instanceof Transportes && a.getHistorico().getPedidosConcluidos().size() == i){
                    atores.add(a);
                    break;
                }
            }
        }
        return atores;
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

    public Cliente addCliente(){
        Scanner ler = new Scanner(System.in);
        System.out.print("Nome:"); String nome = ler.nextLine();
        System.out.print("Password:");String pass = ler.nextLine();
        System.out.print("E-mail:");String email = ler.next();
        System.out.print("Morada:");String morada = ler.nextLine();
        System.out.print("Data de Nascimento(dia mes ano):");int dia = ler.nextInt(); int mes = ler.nextInt(); int ano = ler.nextInt();
        LocalDate datan = LocalDate.of(ano,mes,dia);
        System.out.print("Coordenadas de Entregas (x y):"); int x = ler.nextInt(); int y = ler.nextInt();
        return new Cliente(email,nome,pass,morada,datan,x,y);
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
