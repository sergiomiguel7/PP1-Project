public class SUrgentes extends Servico {

    public SUrgentes(){super();}

    public SUrgentes(int limiteT)
    {
        super(true, limiteT);
    }

    public String toString() {
        return "SUrgentes";
    }

    public boolean equals(Servico servico) {
        if (servico instanceof SUrgentes)
            return true;
        else
            return false;
    }
}
