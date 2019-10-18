import java.util.Scanner;

public class ProgramController {
    private boolean isExistente;

    public ProgramController() {
        this.isExistente = false;
    }

    public boolean isExistente() {
        return isExistente;
    }

    public void setExistente(boolean existente) {
        isExistente = existente;
    }

    private static Ator menuLogin(AtorDB db, ProgramController aux)
    {
        Scanner ler = new Scanner(System.in);
        Ator a1 = new Ator();

        int op;
        do {
            System.out.println("1-Login\n2-Registar\n0-Sair");
            op=ler.nextInt();
            switch (op) {
                case 1: {       //LOGIN!!!!!
                    String user = ler.next();
                    String pass = ler.next();
                    if (db.verificaLogin(user, pass)) {
                        System.out.println("Login valido");
                        aux.setExistente(true);
                        a1=db.getAtor(user, pass);
                        return a1;
                    } else {
                        System.out.println("Login invalido");
                        aux.setExistente(false);
                    }
                }
                break;
                case 2: {       //REGISTAR!!!!
                    System.out.println("1-Cliente\n2-Fornecedor");
                    op=ler.nextInt();
                    if(op==1) {
                        a1 = new Cliente("sergio@gmail.com", "Sergio", "vitoria", "Rua da", "03/06/2000", 1, 2);
                        db.Add(a1, a1.getPassword());
                    }
                    else if(op==2) {
                        a1 = new Transportes("sergio@gmail.com", "Sergio", "vitoria", "Rua da", "03/06/2000", 1, 2, 3, 5, 6);
                        db.Add(a1, a1.getPassword());
                    }
                    else
                        System.out.println("Invalido");

                }
                break;
                case 0: {
                    System.out.println("Sair");
                }

            }

        } while (op!=0);

return a1;
    }




    public static void main(String[] args) {
        ProgramController aux = new ProgramController();
        AtorDB db = new AtorDB();
        Scanner ler = new Scanner(System.in);
        Ator ator = new Ator();

        ator = menuLogin(db, aux);
        if(aux.isExistente)
        {
            if (ator instanceof Cliente){       //compara se ator e de tipo Cliente ou Transportes
                //menu Cliente

            }
            else if(ator instanceof Transportes) {
                //menuFornecedor
                 
            }
        }


    }


}








