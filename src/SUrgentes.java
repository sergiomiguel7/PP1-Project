public class SUrgentes extends Servico {

    public SUrgentes(){super();}

    public SUrgentes(int limiteT)
    {
        setlimiteTUrg(limiteT);
        setTransporteTCUrg();
    }

    public void setlimiteTUrg(int limiteT){
        if(limiteT > 0){
            super.setLimiteT(limiteT);
        }else {
            super.setLimiteT(0);
        }
    }

    public void setTransporteTCUrg(){
        super.setTransporteTC(true);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serviço de Urgentes")
                .append("\nTemperatura constante:").append(super.isTransporteTC())
                .append("\nLimite de Pessoas:").append(super.getLimiteT());
        return sb.toString();
    }

    public boolean equals(Servico servico) {
        if (servico instanceof SUrgentes)
            return true;
        else
            return false;
    }
}
