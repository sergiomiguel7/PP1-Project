public class ProgramController {
    private boolean isExistente;
    private static Menus menus;

    public ProgramController() {
        this.isExistente = false;
    }

    public boolean isExistente() {
        return isExistente;
    }

    public void setExistente(boolean existente) {
        isExistente = existente;
    }

    public static void main(String[] args)  {
        menus = new Menus();
        ProgramController aux = new ProgramController();
        AtorDB db = new AtorDB();
        db.leFicheiro();
        while(true) {
            aux.setExistente(false);
            Ator ator = menus.menuLogin(db, aux);
                if (aux.isExistente) {
                    if (ator instanceof Cliente) {       //compara se ator e de tipo Cliente ou Transportes
                        menus.menuCliente(db, ator);
                    } else if (ator instanceof Transportes) {
                        menus.menuTransportes(db, ator);
                    }
                }
            }

    }

    }









