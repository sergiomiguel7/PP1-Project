public class Transportes extends Ator {
    private Servico servico;
    private Historico historicoT;
    private double precoKM;
    private double tempoKM;
    private double autonomia;

    //Quanto maior a autonomia, menor o consumo medio;

    public Transportes()
    {
        super(" "," "," "," "," ", 0, 0);
        this.servico=new Servico();
        this.precoKM=0;
        this.tempoKM=0;
        this.autonomia = 0;
    }
    public Transportes(double precoKM, double tempoKM, double autonomia)
    {
        this.servico=new Servico();
        this.precoKM=precoKM;
        this.tempoKM=tempoKM;
        this.autonomia= autonomia;
    }
    public Transportes(String email, String nome , String password, String morada, String dataN, int x , int y, double tempoKM, double precoKM, double autonomia)
    {
        super(email, nome, password, morada, dataN, x, y);
        this.servico=new Servico();
        this.tempoKM=tempoKM;
        this.precoKM=precoKM;
        this.autonomia = autonomia;
    }
    public Transportes(Transportes transportes)
    {
        super(transportes.getEmail(), transportes.getNome(),transportes.getPassword(),transportes.getPassword(),transportes.getDataN(),transportes.getX(),transportes.getY());
        servico=transportes.getServico();
        tempoKM=transportes.getTempoKM();
        precoKM=transportes.getPrecoKM();
        autonomia = transportes.getAutonomia();
    }

    public Servico getServico() { return servico; }

    public void setServico(Servico servico) { this.servico = servico; }

    public double getPrecoKM() { return precoKM; }

    public void setPrecoKM(double precoKM) { this.precoKM = precoKM; }

    public double getTempoKM() { return tempoKM; }

    public void setTempoKM(double tempoKM) { this.tempoKM = tempoKM; }

    public Historico getHistorico(){ return this.historicoT; }

    public void setHistoricoT(Historico historicoT) { this.historicoT = historicoT; }

    public double getAutonomia() { return autonomia; }

    public void setAutonomia(double autonomia) { this.autonomia = autonomia; }

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
                " \n(x,y)=(" + x +","+ y +")" +
                "}";
    }

    public double PrecoTrans(Transportes transportes , double distancia){
        return distancia*(transportes.getPrecoKM());
    }

    public double distanciaXY(Cliente cliente, Transportes transportes){
        double x1 = (transportes.getX() - cliente.getX());
        double y1 = (transportes.getY() - cliente.getY());
        x1 = x1*x1;
        y1 = y1*y1;
        double total = x1 + y1;
        Math.sqrt(total);
        return total;
    }
}
