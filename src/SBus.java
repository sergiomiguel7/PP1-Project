public class SBus extends Servico{
    private boolean transporteC;    //transporte de crianças

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
        return "SBus{" +
                "transporteC=" + transporteC +
                '}';
    }
}
