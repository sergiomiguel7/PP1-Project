public class SPessoas extends Servico {
    private boolean transporteC;

    public SPessoas(boolean transporteTC, int limiteT, int limiteD, boolean transporteC)
    {
        super(transporteTC,limiteT,limiteD);
        this.transporteC=transporteC;

    }

    public boolean isTransporteC() {
        return transporteC;
    }

    public void setTransporteC(boolean transporteC) {
        this.transporteC = transporteC;
    }
}
