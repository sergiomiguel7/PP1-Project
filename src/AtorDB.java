
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AtorDB {
    private Map<Ator,String> utilizadores;
    private boolean logInfo;


    public AtorDB() {
        this.utilizadores = new HashMap<>();
        logInfo=false;
    }

    public Map<Ator,String> getUtilizadores()
    {
        return this.utilizadores;

    }

    public void setUtilizadores(HashMap<Ator,String> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public boolean isLogInfo() {
        return logInfo;
    }

    public void setLogInfo(boolean logInfo) {
        this.logInfo = logInfo;
    }

    public void Add(Ator a, String password) {
        utilizadores.put(a , password);
    }


    public boolean verificaLogin(String user, String pass) {
        Ator ator = new Ator();
        for(Entry a : utilizadores.entrySet())
        {
            ator= (Ator) a.getKey();
            if(ator.nome.equals(user) && ator.password.equals(pass))
            {
                logInfo=true;
                break;
            }
            else
                logInfo=false;
        }

        return logInfo;
    }


}
