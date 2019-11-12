import java.time.LocalDateTime;

public class Pedido {
    private boolean concluido;
    private Servico servico;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private double preco;

    public Pedido() {
        this.servico=null;
        this.concluido=false;
        this.dataFim= null;
        this.dataInicio=null;
        this.preco=0;
    }

    public Pedido(Servico servico,LocalDateTime dataInicio, LocalDateTime dataFim,double preco) {
        this.servico=servico;
        this.concluido=false;
        this.dataInicio=dataInicio;
        this.dataFim=dataFim;
        this.preco=preco;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public Servico getServico() { return servico; }

    public void setServico(Servico servico) { this.servico = servico; }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Servico: ").append(this.getServico());
        sb.append("\nData Pedido: ").append(this.dataInicio.toString());
        sb.append("\nData Entrega: ").append(this.dataFim.toString());
        sb.append("\nPreço: ").append(this.preco).toString();
        sb.append("\nPedido Concluido: ").append(this.concluido);
        return sb.toString();
    }
}
