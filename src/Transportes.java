import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private static final long serialVersionUID = 2L;


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

    public long tempoRestante()
    {
        Pedido naoC= this.getHistorico().getPedidos().stream()
                .filter(s -> !s.isConcluido())
                .findFirst().get();

        Duration duration = Duration.between(naoC.getDataFim(), LocalDateTime.now());
        return duration.toMinutes();

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


    public Iterator<Transportes> transportesDisponiveis(AtorDB db, Servico servico,Cliente cliente){
        Comparator<Transportes >byNome = Comparator.comparing(Transportes::getNome);
        TreeSet<Transportes> res = codicaoTreeSet(byNome,db,servico,cliente);
        return res.iterator();
    }

    public Ator transporteMaisRapido(AtorDB db, Servico servico,Cliente cliente){
        Comparator<Transportes> maisRapido = (t1,t2) -> (int) (t1.getTempoKM() - t2.getTempoKM());
        TreeSet<Transportes> res = codicaoTreeSet(maisRapido,db,servico,cliente);
        return res.first();
    }

    public Ator transporteMaisBarato(AtorDB db, Servico servico,Cliente cliente){
        Comparator<Transportes> maisBarato = (t1,t2) -> (int) (t1.getPrecoKM() - t2.getPrecoKM());
        TreeSet<Transportes> res = codicaoTreeSet(maisBarato,db,servico,cliente);
        return res.first();
    }


    public List<Transportes> maisServicosEfetuados(AtorDB atordb)
    {
        Comparator<Transportes> byServicosE = (t1,t2)->  (int) (t1.getHistorico().getPedidosConcluidos().size()-t2.getHistorico().getPedidosConcluidos().size());
        List<Integer> valores =   atordb.getUtilizadores().values().stream()
                .filter(ator -> ator instanceof Transportes)
                .map(ator -> ((Transportes) ator).getHistorico().getPedidosConcluidos().size())
                .sorted()
                .collect(Collectors.toList());

        List<Transportes> atores = new ArrayList<>();
        for(int i : valores){
            for(Ator a : atordb.getUtilizadores().values()){
                if(a instanceof Transportes && a.getHistorico().getPedidosConcluidos().size() == i && a.getHistorico().getPedidosConcluidos().size()>0){
                    atores.add(((Transportes)a));
                    break;
                }
            }
        }
        atores.sort(byServicosE);

        return atores;
    }

    public TreeSet<Transportes> codicaoTreeSet(Comparator<Transportes> c, AtorDB db,Servico servico, Cliente cliente){
        TreeSet<Transportes> res = new TreeSet<>(c);
        List<Transportes> aux = new ArrayList<>();
        for(Ator a : db.getUtilizadores().values()) {
            if(a instanceof Transportes ){
                if (((Transportes) a).getServico().equals(servico) && ((Transportes) a).isDisponivel() &&
                        ((Transportes) a).getServico().getLimiteT() >= servico.getLimiteT() &&
                        ((Transportes) a).getAutonomia() >= ((Transportes) a).distanciaXY(cliente,db.getTransportes(a.getNome()))){
                    aux.add((Transportes) a);
                }
            }
        }
        res.addAll(aux);
        return res ;
    }

}