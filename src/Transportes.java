import java.time.LocalDateTime;
import java.util.*;

public class Transportes extends Ator {
    private Servico servico;
    private double precoKM;
    private double tempoKM;
    private double autonomia;

    //Quanto maior a autonomia, menor o consumo medio;

    public Transportes() {
        super(" ", " ", " ", " ", LocalDateTime.now(), 0, 0);
        super.setHistorico(new Historico());
        this.servico = new Servico();
        this.precoKM = 0;
        this.tempoKM = 0;
        this.autonomia = 0;
    }

    public Transportes(double precoKM, double tempoKM, double autonomia) {
        this.servico = new Servico();
        this.precoKM = precoKM;
        this.tempoKM = tempoKM;
        this.autonomia = autonomia;
    }

    public Transportes(String email, String nome, String password, String morada, LocalDateTime dataN, int x, int y, double tempoKM, double precoKM, double autonomia) {
        super(email, nome, password, morada, dataN, x, y);
        super.setHistorico(new Historico());
        this.servico = new Servico();
        this.tempoKM = tempoKM;
        this.precoKM = precoKM;
        this.autonomia = autonomia;
    }

    public Transportes(Transportes transportes) {
        super(transportes.getEmail(), transportes.getNome(), transportes.getPassword(), transportes.getPassword(), transportes.getDataN(), transportes.getX(), transportes.getY());
        super.getHistorico();
        servico = transportes.getServico();
        tempoKM = transportes.getTempoKM();
        precoKM = transportes.getPrecoKM();
        autonomia = transportes.getAutonomia();
    }

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
        s.append("\nPreço por km: " + this.getPrecoKM() );
        s.append("\nTempo por km: "+ this.getTempoKM());
        s.append("\nAutonomia: " + this.getAutonomia());
        return s.toString();
    }

    public double PrecoTrans(Transportes transportes, double distancia) {
        return distancia * (transportes.getPrecoKM());
    }

    public double distanciaXY(Cliente cliente, Transportes transportes) {
        double x1 = (transportes.getX() - cliente.getX());
        double y1 = (transportes.getY() - cliente.getY());
        x1 = x1 * x1;
        y1 = y1 * y1;
        double total = x1 + y1;
        Math.sqrt(total);
        return total;
    }

    //Maior Autonomia por serviço
    public Ator maiorAutoSer(Servico servico) {
        AtorDB atordb = null;
        Map<String, Ator> utilizadores = atordb.getUtilizadores();
        Collection<Ator> valores = utilizadores.values();
        double autonomia = 0;
        Ator teste = null;
        for (Ator a : valores) {
            if (a instanceof Transportes) {
                if (((Transportes) a).getServico() == servico && ((Transportes) a).getAutonomia() > autonomia) {
                    teste = a;
                }
            }
        }
        return teste;
    }

    //Da return a uma lista com os transportes com os mesmos serviços
    public List<Ator> transServico(Servico servico) {
        AtorDB atordb = null;
        List<Ator> arAtor = new ArrayList<>();
        Map<String, Ator> utilizadores = atordb.getUtilizadores();
        Collection<Ator> valores = utilizadores.values();
        for (Ator a : valores) {
            if (a instanceof Transportes) {
                if (((Transportes) a).getServico() == servico) {
                    arAtor.add(a);
                }
            }
        }
        return arAtor;
    }

    public void mostrartransServ(List<Ator> arAtor) {
        System.out.println(arAtor);
    }
    


    public double trajetoTempo(Transportes transporte,Cliente cliente){
        double distancia = distanciaXY(cliente,transporte);
        double tempo = transporte.getTempoKM();
        tempo = tempo  * distancia;
        tempo*=(1+0.05*distancia);
        return tempo;
    }

    public double trajetoPreco(Transportes transporte, Cliente cliente,AtorDB atordb){
        double preco = getPrecoKM();
        preco *= distanciaXY(cliente,transporte);
        if(transporte.getServico().equals(maiorAutoSer(transporte.getServico(),atordb))){
            preco -= preco*0.1;
        }
        return preco;
    }




}