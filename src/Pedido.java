public class Pedido {
    private boolean concluido;
    private Servico servico;
    private int carga;

    public Pedido() {
        this.servico=null;
        this.concluido=false;
        this.carga=0;
    }

    public Pedido(Servico servico, int carga) {

        this.servico=servico;
        this.concluido=false;
        this.carga= carga;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public Servico getServico() { return servico; }

    public void setServico(Servico servico) { this.servico = servico; }

    public int getCarga() { return carga; }

    public void setCarga(int carga) { this.carga = carga; }


}
