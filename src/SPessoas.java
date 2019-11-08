public class SPessoas extends Servico {
    private boolean transporteC;    //transporte de crianças

    public SPessoas()
    {
        super();
        this.transporteC=false;
    }
    public SPessoas(int limiteT,  boolean transporteC)
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

    public String toString() { return "SPessoas{transporteC=" + transporteC + '}'; }
}
