public class SUrgentes extends Servico {

    public SUrgentes(){super();}

    public SUrgentes(int limiteT)
    {
        super(true, limiteT);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Serviço de BUS")
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
