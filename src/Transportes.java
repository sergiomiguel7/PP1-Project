public class Transportes extends Ator {
    private Servico servico;
    private Historico historicoT;
    private long precoKM;
    private long tempoKM;
    private long autonomia;

    //Quanto maior a autonomia, menor o consumo medio;

    public Transportes()
    {
        super(" "," "," "," "," ", 0, 0);
        this.servico=new Servico();
        this.precoKM=0;
        this.tempoKM=0;
        this.autonomia = 0;
    }
    public Transportes(long precoKM, long tempoKM, long autonomia)
    {
        this.servico=new Servico();
        this.precoKM=precoKM;
        this.tempoKM=tempoKM;
        this.autonomia= autonomia;
    }
    public Transportes(String email, String nome , String password, String morada, String dataN, int x , int y, long tempoKM, long precoKM, long autonomia)
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

    public long getPrecoKM() { return precoKM; }

    public void setPrecoKM(long precoKM) { this.precoKM = precoKM; }

    public long getTempoKM() { return tempoKM; }

    public void setTempoKM(long tempoKM) { this.tempoKM = tempoKM; }

    public Historico getHistorico(){ return this.historicoT; }

    public void setHistoricoT(Historico historicoT) { this.historicoT = historicoT; }

    public long getAutonomia() { return autonomia; }

    public void setAutonomia(long autonomia) { this.autonomia = autonomia; }

    public String toString() {
        return "Transportes{\n" +
                "servico=" + servico.XtoString() +
                " \nhistoricoT=" + historicoT +
                " \nprecoKM=" + precoKM +
                " \ntempoKM=" + tempoKM +
                " \nemail='" + email +
                " \nnome='" + nome +
                " \npassword='" + password +
                " \nmorada='" + morada +
                " \ndataN='" + dataN +
                " \nautonomia='" + autonomia +
                " \n(x,y)=(" + x +","+ y +")" +
                "}";
    }


}
