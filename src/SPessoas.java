public class SPessoas extends Servico {
    private boolean transporteC;    //transporte de crianças

    public SPessoas()
    {
        super();
        this.transporteC=false;
    }
    public SPessoas(int limiteT,  boolean transporteC)
    {
            setTransporteTCP();
            setlimiteTP(limiteT);
            this.transporteC=transporteC;
    }


    public void setlimiteTP(int limiteT){
        if(limiteT <= 7 && limiteT > 0){
            super.setLimiteT(limiteT);
        }else {
            super.setLimiteT(0);
        }
    }

    public void setTransporteTCP(){
        super.setTransporteTC(false);
    }


    public boolean isTransporteC() {
        return transporteC;
    }

    public void setTransporteC(boolean transporteC) {
        this.transporteC = transporteC;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serviço de Pessoas")
                .append("\nTemperatura constante:").append(super.isTransporteTC())
                .append("\nLimite de Pessoas:").append(super.getLimiteT())
                .append("\nTransporte de Crianças:").append(this.isTransporteC());
        return sb.toString();
    }

    @Override
    public boolean equals(Servico servico) {
        if (servico instanceof SPessoas && ((SPessoas) servico).isTransporteC())
            return true;
        else if (servico instanceof SPessoas && ((SPessoas)servico).transporteC == false)
            return true;
        else
            return false;
    }
}
