import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AtorDB {
    private Map<String,String> utilizadores;
    private boolean logInfo;


    public AtorDB() {
        this.utilizadores = new HashMap<>();
        logInfo=false;
    }

    public Map<String,String> getUtilizadores()
    {
        return this.utilizadores;

    }

    public void setUtilizadores(HashMap<String,String> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public boolean isLogInfo() {
        return logInfo;
    }

    public void setLogInfo(boolean logInfo) {
        this.logInfo = logInfo;
    }

    public void Add(String nome, String password) {
        utilizadores.put(nome , password);
    }


    public boolean verificaLogin(String user, String pass) {
        for(Entry a : utilizadores.entrySet())
        {
            if(a.getKey().equals(user) && a.getValue().equals(pass))
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
