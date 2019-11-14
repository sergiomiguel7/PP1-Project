public class SBus extends Servico{
    private boolean transporteC;    //transporte de crianças

    public SBus()
    {
        super();
        transporteC=false;
    }
    public SBus(int limiteT, boolean transporteC)
    {
        setLimiteTBus(limiteT);
        setTransporteTCBus();
        this.transporteC=transporteC;
    }

    public void setLimiteTBus(int limiteT) {
        if (limiteT <= 68 && limiteT > 0) {
            super.setLimiteT(limiteT);
        }else {
            super.setLimiteT(0);
        }
    }

    public void setTransporteTCBus(){
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
        sb.append("Serviço de BUS")
                .append("\nTemperatura constante:").append(super.isTransporteTC())
                .append("\nLimite de Pessoas:").append(super.getLimiteT())
                .append("\nTransporte de Crianças:").append(this.isTransporteC());
        return sb.toString();
    }

    public boolean equals(Servico servico) {
        if (servico instanceof SBus && ((SBus) servico).isTransporteC())
            return true;
        else
            return false;
    }
}
