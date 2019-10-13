import java.util.Scanner;

public class Testes {

    public static void main(String[] args) {
        AtorDB login = new AtorDB();
        int selection=0;
        Scanner input = new Scanner(System.in);

        System.out.println("1-Clientes\n2-Fornecedores\n0-Sair");
        selection = input.nextInt();

        do {
            if (selection == 1) {
                System.out.println(login.getUtilizadores());
                System.out.println("1-Login\n2-Registar\n0-Sair");
                int op = input.nextInt();
                if (op==1){
                    System.out.println("Nome:");
                    String nome = input.next();
                    System.out.println("Password:");
                    String pass = input.next();
                    if(login.verificaLogin(nome,pass))
                    {
                        System.out.println("Login Efetuado");
                    }
                    else
                        System.out.println("Login inválido");

                }
                else if (op==2)
                {
                    Cliente a1 = new Cliente("Sergio", "Sergio", "vitoria", "rua", "03/06/2000", 1 ,2 );
                    Cliente a2 = new Cliente("Sergio@gmail.com", "Miguel", "vitoriag", "rua", "03/06/2000", 1 ,2 );
                    login.Add(a1, a1.getPassword());
                    login.Add(a2 , a2.getPassword());
                }
            }
            else if (selection ==2)
            {

            }
        }while(selection!=0);

    }

}








