public class Transportes extends Ator {
    private Servico servico;
    private Historico historicoT;
    private long precoKM;
    private long tempoKM;

    public Transportes()
    {
        super(" "," "," "," "," ", 0, 0);
        servico=new Servico();
        precoKM=0;
        tempoKM=0;
    }
    public Transportes(long precoKM, long tempoKM)
    {
        this.servico=new Servico();
        this.precoKM=precoKM;
        this.tempoKM=tempoKM;
    }
    public Transportes(String email, String nome , String password, String morada, String dataN, int x , int y, long tempoKM, long precoKM)
    {
        super(email, nome, password, morada, dataN, x, y);
        this.servico=new Servico();
        this.tempoKM=tempoKM;
        this.precoKM=precoKM;
    }
    public Transportes(Transportes transportes)
    {
        super(transportes.getEmail(), transportes.getNome(),transportes.getPassword(),transportes.getPassword(),transportes.getDataN(),transportes.getX(),transportes.getY());
        servico=transportes.getServico();
        tempoKM=transportes.getTempoKM();
        precoKM=transportes.getPrecoKM();
    }

    public Servico getServico() { return servico; }

    public void setServico(Servico servico) { this.servico = servico; }

    public long getPrecoKM() { return precoKM; }

    public void setPrecoKM(long precoKM) { this.precoKM = precoKM; }

    public long getTempoKM() { return tempoKM; }

    public void setTempoKM(long tempoKM) { this.tempoKM = tempoKM; }

    @Override
    public String toString() {
        return "Transportes{" +
                "servico=" + servico.XtoString() +
                ", historicoT=" + historicoT +
                ", precoKM=" + precoKM +
                ", tempoKM=" + tempoKM +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", password='" + password + '\'' +
                ", morada='" + morada + '\'' +
                ", dataN='" + dataN + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
