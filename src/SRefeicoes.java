public class SRefeicoes extends Servico {
    private int precoFixo;  //km ate que o preço e fixo

    public SRefeicoes()
    {
        super();
        precoFixo=0;
    }
    public SRefeicoes(int limiteT)
    {
        super(true,limiteT);
        this.precoFixo=5;
    }

    public int getPrecoFixo() {
        return precoFixo;
    }

    public void setPrecoFixo(int precoFixo) {
        this.precoFixo = precoFixo;
    }

    public String toString() {
        return "SRefeicoes{" +
                "precoFixo=" + precoFixo +
                '}';
    }

    public boolean equals(Servico servico) {
        if (servico instanceof SRefeicoes)
            return true;
        else
            return false;
    }
}
