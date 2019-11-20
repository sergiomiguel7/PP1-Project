import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;
import java.util.*;
import java.util.stream.Collectors;

public class Transportes extends Ator {
    private Servico servico;
    private double precoKM;
    private double tempoKM;
    private double autonomia;
    private boolean disponivel;
    private double extra;

    //Quanto maior a autonomia, menor o consumo medio;

    public Transportes() {
        super(" ", " ", " ", " ", LocalDate.now(), 0, 0);
        super.setHistorico(new Historico());
        this.precoKM = 0;
        this.tempoKM = 0;
        this.autonomia = 0;
        this.extra = 0;
        this.disponivel = true;
    }

    public Transportes(double precoKM, double tempoKM, double autonomia, Servico a,double extra) {
        this.servico = a;
        this.precoKM = precoKM;
        this.tempoKM = tempoKM;
        this.autonomia = autonomia;
        this.extra = extra;
        this.disponivel = true;
    }

    public Transportes(String email, String nome, String password, String morada, LocalDate dataN, Servico a, double tempoKM, double precoKM, double autonomia,double extra) {
        super(email, nome, password, morada, dataN, 0, 0);
        super.setHistorico(new Historico());
        this.servico = a;
        this.tempoKM = tempoKM;
        this.precoKM = precoKM;
        this.autonomia = autonomia;
        this.disponivel = true;
        this.extra = extra;
    }

    public Transportes(Transportes transportes) {
        super(transportes.getEmail(), transportes.getNome(), transportes.getPassword(), transportes.getPassword(), transportes.getDataN(), transportes.getX(), transportes.getY());
        super.getHistorico();
        this.servico = transportes.getServico();
        this.tempoKM = transportes.getTempoKM();
        this.precoKM = transportes.getPrecoKM();
        this.autonomia = transportes.getAutonomia();
        this.disponivel = transportes.isDisponivel();
        this.extra = transportes.getExtra();
    }

    public double getExtra() { return extra; }

    public void setExtra(double extra) { this.extra = extra; }

    public boolean isDisponivel() { return disponivel; }

    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public double getPrecoKM() {
        return precoKM;
    }

    public void setPrecoKM(double precoKM) {
        this.precoKM = precoKM;
    }

    public double getTempoKM() {
        return tempoKM;
    }

    public void setTempoKM(double tempoKM) {
        this.tempoKM = tempoKM;
    }


    public double getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(double autonomia) {
        this.autonomia = autonomia;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("\nDados:" + super.toString());
        s.append("\nServiço:" + this.getServico());
        s.append("\nPreço por km: " + this.getPrecoKM() );
        s.append("\nTempo por km: "+ this.getTempoKM());
        s.append("\nAutonomia: " + this.getAutonomia());
        s.append("\nPreco Extra: " + this.getExtra());
        return s.toString();
    }

    public double distanciaXY(Cliente cliente, Transportes transportes) {
        double x1 = (transportes.getX() - cliente.getX());
        double y1 = (transportes.getY() - cliente.getY());
        x1 = x1 * x1;
        y1 = y1 * y1;
        double total = x1 + y1;
        total=Math.sqrt(total);
        return total;
    }

    public long trajetoTempo(Transportes transporte,Cliente cliente){
        Random rand = new Random();
        double distancia = distanciaXY(cliente,transporte);
        double tempo = transporte.getTempoKM();
        tempo = tempo  * distancia;
        for(int i = 0;i < distancia; i ++ ){
            int randprob = rand.nextInt(100);
            int randtempo = rand.nextInt(5);
            if(randprob <= 5 ){
                tempo+=randtempo;
            }
        }
        return (long) Math.round(tempo);
    }

    public long trajetoTempoTeorico(Transportes transporte,Cliente cliente){
        Random rand = new Random();
        double distancia = distanciaXY(cliente,transporte);
        double tempo = transporte.getTempoKM();
        tempo = tempo  * distancia;
        return (long) tempo;
    }



    public double trajetoPreco(Cliente cliente){
        LocalTime inicio = LocalTime.of(9,0,0);
        LocalTime fim = LocalTime.of(21,0,0);
        double preco = 0;
        double dist = 0;
        if(this.servico instanceof SRefeicoes){
            preco = ((SRefeicoes) this.servico).getPrecoFixo();
            dist =  distanciaXY(cliente,this);
            if(dist > 5){
                dist -= 5;
                preco += (dist*getPrecoKM());
            }

        }else {
            preco = getPrecoKM();
            preco *= distanciaXY(cliente, this);
        }

        if (LocalTime.now().isAfter(fim) && LocalTime.now().isBefore(inicio)) {
            preco += extra;
        }

        return preco;
    }

    public Transportes addTransporte(Servico a){

        Scanner ler = new Scanner(System.in);
        Scanner ler2 = new Scanner(System.in).useDelimiter("\n");
        System.out.print("Nome:"); String nome = ler.next();
        System.out.print("Password:");String pass = ler.next();
        System.out.print("E-mail:");String email = ler.next();
        System.out.print("Morada:"); String morada = ler2.next();
        System.out.print("Data de Nascimento(dia mes ano):");int dia = ler.nextInt(); int mes = ler.nextInt(); int ano = ler.nextInt();
        LocalDate datan = LocalDate.of(ano,mes,dia);
        System.out.print("Preço por Km:");double precoKM = ler.nextDouble();
        System.out.print("Tempo por Km:");double tempoKM = ler.nextDouble();
        System.out.print("Autonomia:");double autonomia = ler.nextDouble();
        System.out.print("Preco extra (noturno):");double extra = ler.nextDouble();
        return new Transportes(email,nome,pass,morada,datan,a,tempoKM,precoKM,autonomia,extra);
    }

    public static Servico escolherServicoT(String a)
    {
        Scanner ler = new Scanner(System.in);
        Servico novo;
        while(true) {
            if (a.equalsIgnoreCase("Pessoas"))
            {
                int limit;
                boolean criancas = false;
                System.out.println("Maximo de pessoas que vai transportar [1,7]:");
                limit = ler.nextInt();
                System.out.println("Permite o transporte de crianças?(Sim ou Não)");
                if (ler.next().equalsIgnoreCase("sim"))
                    criancas = true;
                else
                    criancas = false;
                novo = new SPessoas(limit, criancas);
                break;
            } else if (a.equalsIgnoreCase("Bus"))
            {
                int limit;
                boolean criancas = false;
                System.out.println("Maximo de pessoas que vai transportar [1,68]:");
                limit = ler.nextInt();
                System.out.println("Permite o transporte de crianças?(Sim ou Não)");
                if (ler.next().equalsIgnoreCase("sim"))
                    criancas = true;
                else
                    criancas = false;
                novo = new SBus(limit, criancas);
                break;
            } else if (a.equalsIgnoreCase("Big")) {
                int limit;
                boolean criancas = false;
                System.out.println("Maximo de carga que vai transportar (até 6 toneladas):");
                limit = ler.nextInt();
                novo = new SBig(limit);
                break;
            } else if (a.equalsIgnoreCase("Urgentes")) {
                int limit;
                System.out.println("Maximo de produtos por utilizador que pode transportar:");
                limit=ler.nextInt();
                novo= new SUrgentes(limit);
                break;
            } else if (a.equalsIgnoreCase("Refeições")) {
                int limit;
                System.out.println("Maximo de refeições por utilizador que pode transportar[1,15]:");
                limit=ler.nextInt();
                novo= new SRefeicoes(limit);
                break;
            }
            else{
                novo=null;
                break;
            }
        }
        return novo;

    }
}