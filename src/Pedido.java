public class Pedido {
    private boolean concluido;


    public Pedido() { concluido=false; }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
}
