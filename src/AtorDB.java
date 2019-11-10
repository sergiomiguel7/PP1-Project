
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AtorDB {
    private Map<String,Ator> utilizadores;
    private boolean logInfo;


    public AtorDB() {
        this.utilizadores = new HashMap<>();
        logInfo=false;
    }

    public Map<String,Ator> getUtilizadores()
    {
        return this.utilizadores;

    }

    public Ator getAtor(String user) { return this.utilizadores.get(user); }

    public Transportes getTransportes(String user)
    {

        return (Transportes) this.utilizadores.get(user.toLowerCase());
    }

    public void setUtilizadores(HashMap<String,Ator> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public boolean isLogInfo() {
        return logInfo;
    }

    public void setLogInfo(boolean logInfo) {
        this.logInfo = logInfo;
    }

    public void Add(String user, Ator a) {

        this.utilizadores.put(user.toLowerCase(), a);
    }

    public int getQuantidade() {
        return utilizadores.size();
    }

    public boolean estaVazio() {
        return utilizadores.isEmpty();
    }



    public boolean verificaLogin(String name, String pass) {


        if(this.utilizadores.containsKey(name)) {
            if(this.utilizadores.get(name).getPassword().equals(pass))
                this.logInfo=true;
        }
        else
            this.logInfo=false;



        return this.logInfo;
    }

}
