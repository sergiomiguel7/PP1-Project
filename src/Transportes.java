import java.util.*;

public class Transportes extends Ator {
    private Servico servico;
    private Historico historicoT;
    private double precoKM;
    private double tempoKM;
    private double autonomia;

    //Quanto maior a autonomia, menor o consumo medio;

    public Transportes() {
        super(" ", " ", " ", " ", " ", 0, 0);
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

    public Transportes(String email, String nome, String password, String morada, String dataN, int x, int y, double tempoKM, double precoKM, double autonomia) {
        super(email, nome, password, morada, dataN, x, y);
        this.servico = new Servico();
        this.tempoKM = tempoKM;
        this.precoKM = precoKM;
        this.autonomia = autonomia;
    }

    public Transportes(Transportes transportes) {
        super(transportes.getEmail(), transportes.getNome(), transportes.getPassword(), transportes.getPassword(), transportes.getDataN(), transportes.getX(), transportes.getY());
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

    public Historico getHistorico() {
        return this.historicoT;
    }

    public void setHistoricoT(Historico historicoT) {
        this.historicoT = historicoT;
    }

    public double getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(double autonomia) {
        this.autonomia = autonomia;
    }

    public String toString() {
        return "Transportes{\n" +
                "servico=" + servico.getServicoX() +
                " \nhistoricoT=" + historicoT +
                " \nprecoKM=" + precoKM +
                " \ntempoKM=" + tempoKM +
                " \nemail=" + email +
                " \nnome=" + nome +
                " \npassword=" + password +
                " \nmorada=" + morada +
                " \ndataN=" + dataN +
                " \nautonomia=" + autonomia +
                " \n(x,y)=(" + x + "," + y + ")" +
                "}";
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

    //Da return aos tres servicos com menor preco por km
    //Esta super complicado mas nao consegui pensar melhor, irei voltar a esta funçao
    public List<Ator> transpreco() {
        AtorDB atordb = null;
        double precokm = 99999;
        /*
        Poderia colocar aqui o valor do primeiro transporte, mas necessitava de um ciclo for
        e poderiam acontecer erros que por acaso ja me ocorreram
         */
        Ator b = null;
        Ator c = null;
        Ator d = null;
        List<Ator> arAtor = new ArrayList<>();
        Map<String, Ator> utilizadores = atordb.getUtilizadores();
        Collection<Ator> valores = utilizadores.values();
        for (int i = 0; i < 3; i++) {
            for (Ator a : valores) {
                if (a instanceof Transportes) {
                    if (precokm > ((Transportes) a).getPrecoKM() && i == 0) {
                        b = a;
                        c = a;
                        precokm = ((Transportes) a).getPrecoKM();
                    }
                    if (precokm > ((Transportes) a).getPrecoKM() && i == 1 && c != a) {
                        b = a;
                        d = a;
                        precokm = ((Transportes) a).getPrecoKM();
                    }
                    if (precokm > ((Transportes) a).getPrecoKM() && i == 2 && c != a && d != a) {
                        b = a;
                        precokm = ((Transportes) a).getPrecoKM();
                    }
                }
            }
            arAtor.add(b);
            precokm = 99999;
        }
        return arAtor;
    }


}