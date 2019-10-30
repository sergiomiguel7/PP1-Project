import java.time.LocalDate;
import java.time.LocalDateTime;

public class Ator {

    private String email, nome,password,morada;
    private LocalDateTime dataN;
    private Historico historico;
    private double x, y;

    //construtores
    public Ator()
    {
        this.dataN= LocalDateTime.now();
        this.email=" ";
        this.morada=" ";
        this.nome=" ";
        this.password=" ";
        this.x=0;
        this.y=0;
        this.historico= new Historico();
    }
    public Ator (String email, String nome , String password, String morada, LocalDateTime dataN, double x , double y)
    {
        this.email=email;
        this.nome=nome;
        this.password=password;
        this.morada=morada;
        this.dataN=dataN;
        this.x=x;
        this.y=y;
        this.historico= new Historico();
    }
    public Ator(Ator ator)
    {
        this.email=getEmail();
        this.nome=getNome();
        this.password=getPassword();
        this.morada=getPassword();
        this.dataN=getDataN();
        this.x=getX();
        this.y=getY();
        this.historico= getHistorico();

    }

    //getters
    public LocalDateTime getDataN() { return dataN; }

    public String getEmail() { return email; }

    public String getMorada() { return morada; }

    public String getNome() { return nome; }

    public String getPassword() { return password; }

    public double getX() { return x; }

    public double getY() { return y; }

    public Historico getHistorico() {
        return this.historico;
    }


    //setters

    public void setDataN(LocalDateTime dataN) { this.dataN = dataN; }

    public void setEmail(String email) { this.email = email; }

    public void setMorada(String morada) { this.morada = morada; }

    public void setNome(String nome) { this.nome = nome; }

    public void setPassword(String password) { this.password = password; }

    public void setY(double y) { this.y = y; }

    public void setX(double x) { this.x = x; }

    public void setHistorico(Historico historico) {
        this.historico = historico;
    }
    //metodos
    public Ator clone(){ return new Ator(this); }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("E-mail: ").append(this.email);
        sb.append("\nNome: ").append(this.nome);
        sb.append("\nPassword: ").append(this.password);
        sb.append("\nMorada: ").append(this.morada);
        sb.append("\nData de Nascimento: ").append(this.dataN);
        sb.append("\nPosição X: ").append(this.x).toString();
        sb.append("\nPosição Y: ").append(this.y).toString();
        return sb.toString();
    }



    public boolean equals(Object obj) {
        if(this == obj) return true;

        if((obj == null ) || (obj.getClass() != this.getClass())) return false;

        Ator a = (Ator) obj;

        return  a.getEmail().equals(this.email) &&
                a.getNome().equals(this.nome) &&
                a.getPassword().equals(this.password) &&
                a.getMorada().equals(this.morada) &&
                a.getDataN().equals(this.dataN) &&
                a.getX() == this.x &&
                a.getY() == this.y;
    }
}


