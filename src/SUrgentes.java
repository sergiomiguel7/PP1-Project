public class SUrgentes extends Servico implements TemperaturaConstante {

    private boolean tc;

    public SUrgentes(){super();}

    public SUrgentes(int limiteT)
    {
        setlimiteTUrg(limiteT);
    }


    public void setlimiteTUrg(int limiteT){
        if(limiteT > 0){
            super.setLimiteT(limiteT);
        }else {
            super.setLimiteT(0);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serviço de Urgentes")
                .append("\nLimite de Pessoas:").append(super.getLimiteT());
        return sb.toString();
    }

    public boolean equals(Servico servico) {
        if (servico instanceof SUrgentes && this.isTemperaturaConstante()==((SUrgentes) servico).isTemperaturaConstante())
            return true;
        else if(servico instanceof SUrgentes && !((SUrgentes) servico).isTemperaturaConstante()) return true;
        else
            return false;
    }

    @Override
    public void temperaturaConstante(boolean quer) {
        this.tc = quer;
    }

    @Override
    public boolean isTemperaturaConstante() {
        return tc;
    }
}
