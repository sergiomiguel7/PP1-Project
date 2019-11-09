import java.time.LocalDateTime;
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

    public static void main(String[] args) {

        ProgramController aux = new ProgramController();
        AtorDB db = new AtorDB();
        Scanner ler = new Scanner(System.in);
        Ator ator = new Ator();

        ator = Menus.menuLogin(db, aux);
        if(aux.isExistente)
        {
            if (ator instanceof Cliente){       //compara se ator e de tipo Cliente ou Transportes
               // menuCliente();

            }
            else if(ator instanceof Transportes) {

            }
        }


    }


}








