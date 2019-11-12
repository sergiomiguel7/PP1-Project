public class SBus extends Servico{
    private boolean transporteC;    //transporte de crianças

    public SBus()
    {
        super();
        transporteC=false;
    }
    public SBus(int limiteT, boolean transporteC)
    {
        super(false,limiteT);
        this.transporteC=transporteC;
    }

    public boolean isTransporteC() {
        return transporteC;
    }

    public void setTransporteC(boolean transporteC) {
        this.transporteC = transporteC;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Transporte de Crianças:").append(isTransporteC());
        return sb.toString();
    }
    public boolean equals(Servico servico) {
        if (servico instanceof SBus && ((SBus) servico).isTransporteC())
            return true;
        else
            return false;
    }
}
