public class SBig extends Servico {

    public SBig()
    {
        super();
    }

    public SBig(int limiteT)
    {
        super(false,limiteT);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serviço de BUS")
                .append("\nTemperatura constante:").append(super.isTransporteTC())
                .append("\nLimite de Carga:").append(super.getLimiteT());
        return sb.toString();
    }

    public boolean equals(Servico servico) {
        if (servico instanceof SBig)
            return true;
        else
            return false;
    }


}
