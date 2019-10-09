public class Transportes extends Ator {
    private String servico;
    private long precoKM;
    private long tempoKM;

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public long getPrecoKM() {
        return precoKM;
    }

    public void setPrecoKM(long precoKM) {
        this.precoKM = precoKM;
    }

    public long getTempoKM() {
        return tempoKM;
    }

    public void setTempoKM(long tempoKM) {
        this.tempoKM = tempoKM;
    }
}
