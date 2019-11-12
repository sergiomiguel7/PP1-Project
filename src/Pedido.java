import java.time.LocalDateTime;

public class Pedido {
    private boolean concluido;
    private Servico servico;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;

    public Pedido() {
        this.servico=null;
        this.concluido=false;
        this.dataFim= null;
        this.dataInicio=null;
    }

    public Pedido(Servico servico,LocalDateTime dataInicio, LocalDateTime dataFim) {
        this.servico=servico;
        this.concluido=false;
        this.dataInicio=dataInicio;
        this.dataFim=dataFim;
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
