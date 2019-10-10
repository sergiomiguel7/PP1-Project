import java.util.ArrayList;

public class Login {
    private ArrayList<String> utilizadores;


    public Login()
    {
        utilizadores=new ArrayList<String>();
    }

    public void Add(String nome, String password)
    {
        String utilizador = new String(nome+"/"+password);
        utilizadores.add(utilizador);

    }


    //incompleto falta as comparaçoes
    public boolean isExistente()
    {
        for(String a : utilizadores)
        {
            String[] parts = a.split("\");

        }

        return false;
    }






}
