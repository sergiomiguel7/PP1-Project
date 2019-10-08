public class Ator {

    private String email, nome,password,morada,dataN;
    private int x, y;

    //construtores
    public Ator()
    {
        this.dataN= " ";
        this.email=" ";
        this.morada=" ";
        this.nome=" ";
        this.password=" ";
        this.x=0;
        this.y=0;
    }
    public Ator (String email, String nome , String password, String morada, String dataN, int x , int y)
    {
        this.email=email;
        this.nome=nome;
        this.password=password;
        this.morada=morada;
        this.dataN=dataN;
        this.x=x;
        this.y=y;
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


    }

    //getters
    public String getDataN() { return dataN; }

    public String getEmail() { return email; }

    public String getMorada() { return morada; }

    public String getNome() { return nome; }

    public String getPassword() { return password; }

    public int getX() { return x; }

    public int getY() { return y; }
    //setters

    public void setDataN(String dataN) { this.dataN = dataN; }

    public void setEmail(String email) { this.email = email; }

    public void setMorada(String morada) { this.morada = morada; }

    public void setNome(String nome) { this.nome = nome; }

    public void setPassword(String password) { this.password = password; }

    public void setY(int y) { this.y = y; }

    public void setX(int x) { this.x = x; }

    //metodos
public Ator clone(){

        return new Ator(this);
}



}
