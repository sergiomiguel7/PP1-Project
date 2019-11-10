import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Ator {

    private String email, nome,password,morada;
    private LocalDate dataN;
    private Historico historico;
    private double x, y;

    //construtores
    public Ator()
    {
        this.dataN= LocalDate.now();
        this.email=" ";
        this.morada=" ";
        this.nome=" ";
        this.password=" ";
        this.x=0;
        this.y=0;
        this.historico= new Historico();
    }
    public Ator (String email, String nome , String password, String morada, LocalDate dataN, double x , double y)
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
    public LocalDate getDataN() { return dataN; }

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

    public void setDataN(LocalDate dataN) { this.dataN = dataN; }

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
        sb.append("\nData: ").append(this.dataN.toString());
        sb.append("\nX: ").append(this.x).toString();
        sb.append("\nY: ").append(this.y).toString();
        return sb.toString();
    }

    public void alteraDados(String a, Scanner ler)
    {
        a= a.toLowerCase();
        boolean continuar=true;
        while(continuar) {
            switch (a) {
                case "e-mail": {
                    String novo = ler.next();
                    this.setEmail(novo);
                    continuar=false;
                    break;
                }
                case "nome": {
                    System.out.println("Introduza novo:");
                    String novo = ler.next();
                    this.setNome(novo);
                    continuar=false;
                    break;
                }
                case "password": {
                    String novo = ler.next();
                    this.setPassword(novo);
                    continuar=false;
                    break;
                }
                case "morada": {
                    String novo = ler.next();
                    this.setMorada(novo);
                    continuar=false;
                    break;
                }
                case "data": {
                    System.out.println("Dia:");
                    int dia = ler.nextInt();
                    System.out.println("Mês:");
                    int mes= ler.nextInt();
                    System.out.println("Ano:");
                    int ano= ler.nextInt();
                    this.setDataN((LocalDate.of(ano,mes,dia)));
                    continuar=false;
                    break;
                }
                case "x": {
                    int novo = ler.nextInt();
                    this.setX(novo);
                    continuar=false;
                    break;
                }
                case "y": {
                    int novo = ler.nextInt();
                    this.setY(novo);
                    continuar=false;
                    break;
                }
                case "serviço":{
                    if(this instanceof Transportes)
                    {
                        System.out.println("Escolha o seu novo tipo de serviço: Pessoas, Bus, Big, Urgentes ou Refeições");
                        String escolhido = ler.next();
                        ((Transportes) this).setServico(Transportes.escolherServicoT(escolhido));
                    }

                }
                case "sair":{ continuar=false; break;}
                default: {
                    System.out.println("Input invalido");
                    a=ler.next();
                }

            }
        }


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


