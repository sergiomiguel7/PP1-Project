import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Pedido implements Serializable {
    //private static final long serialVersionUID = 7L;
    private double classificacao;
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
        this.classificacao=-1;
    }

    public Pedido(Servico servico,LocalDateTime dataInicio, LocalDateTime dataFim,double preco) {
        this.servico=servico;
        this.concluido=false;
        this.dataInicio=dataInicio;
        this.dataFim=dataFim;
        this.preco=preco;
        this.classificacao=-1;
    }

    public Pedido(Pedido outro){
        this.servico = outro.servico;
        this.preco = outro.preco;
        this.concluido = false;
        this.dataFim = null;
        this.dataInicio = null;
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

    public double getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(double classificacao) {
        this.classificacao = classificacao;
    }

    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm");
        DecimalFormat fmt = new DecimalFormat("0.00");
        StringBuilder sb = new StringBuilder();
        sb.append("Servico: ").append(this.getServico())
                .append("\nData Pedido: ").append(this.getDataInicio().format(formatter))
                .append("\nData Entrega: ").append(this.getDataFim().format(formatter))
                .append("\nPreço: ").append(fmt.format(this.preco))
                .append("\nPedido Concluido: ").append(this.concluido);
        return sb.toString();
    }


    public boolean equals(Pedido pedido)
    {
        if(this==pedido)
            return true;
        if((pedido == null ) || (pedido.getClass() != this.getClass())) return false;

        return this.dataInicio.equals(pedido.getDataInicio()) &&
                this.dataFim.equals(pedido.getDataFim()) &&
                this.preco == pedido.getPreco();

    }

}
