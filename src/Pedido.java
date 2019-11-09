public class Pedido {
    private boolean concluido;
    private Servico servico;

    public Pedido() {
        this.servico=null;
        this.concluido=false;
    }

    public Pedido(Servico servico) {
        this.servico=servico;
        this.concluido=false;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public Servico getServico() { return servico; }

    public void setServico(Servico servico) { this.servico = servico; }


}
