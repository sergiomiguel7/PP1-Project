

public class Testes {
    public static void main(String[] args)
    {
       Login login = new Login();
       Transportes p1 = new Transportes("sergio", "Sergio", "a", "03", "rua", 1 , 2, 3, 2);
       login.Add(p1.nome, p1.password);
       System.out.println(p1.toString());



    }


}
