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

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serviço de BUS")
                .append("\nTemperatura constante:").append(super.isTransporteTC())
                .append("\nLimite de Pessoas:").append(super.getLimiteT())
                .append("\nTransporte de Crianças:").append(this.isTransporteC());
        return sb.toString();
    }

    @Override
    public boolean equals(Servico servico) {
        if (servico instanceof SPessoas && ((SPessoas) servico).isTransporteC())
            return true;
        else
            return false;
    }
}
