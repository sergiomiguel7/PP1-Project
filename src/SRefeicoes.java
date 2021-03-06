public class SRefeicoes extends Servico implements TemperaturaConstante {
    private int precoFixo;  //km ate que o preço e fixo
    private boolean tc;

    public SRefeicoes()
    {
        super();
        precoFixo=0;
    }
    public SRefeicoes(int limiteT)
    {
        setlimiteTRef(limiteT);
        setPrecoFixo(limiteT);
    }

    public void setlimiteTRef(int limiteT){
        if(limiteT <= 15 && limiteT > 0){
            super.setLimiteT(limiteT);
        }else {
            super.setLimiteT(0);
        }
    }


    public void setPrecoFixo(int limiteT) {
        if(limiteT > 0 && limiteT <= 15){
            if (limiteT <= 5){
                this.precoFixo = 5; //Assumindo bicicleta ou mota
            }else {
                this.precoFixo = 8; //Assumindo carro
            }
        }else{
            this.precoFixo = 0;
        }

    }

    public int getPrecoFixo() {
        return precoFixo;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serviço de Refeições")
                .append("\nLimite de Carga:").append(super.getLimiteT());
        return sb.toString();
    }

    @Override
    public boolean equals(Servico servico) {
        if (servico instanceof SRefeicoes && this.isTemperaturaConstante()==((SRefeicoes) servico).isTemperaturaConstante())
            return true;
        else if(servico instanceof SRefeicoes && !((SRefeicoes) servico).isTemperaturaConstante()) return true;
        else
            return false;
    }

    public boolean verificaRefrigeracao(double tempo){
        if(tempo > 20){return  true;}
        else return false;
    }

    @Override
    public void temperaturaConstante(boolean quer) {
        this.tc=quer;
    }

    @Override
    public boolean isTemperaturaConstante() {
        return tc;
    }
}
