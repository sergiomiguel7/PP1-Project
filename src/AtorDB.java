
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

    public Ator getAtor(String user, String pass) {
        Ator ator = new Ator();

        for (Entry a : this.utilizadores.entrySet()) {
            ator = (Ator) a.getKey();
            if(ator.nome.equals(user) && ator.password.equals(pass))
                return ator;
        }
        return ator;
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
        this.utilizadores.put(a , password);
    }

    public int getQuantidade() {
        return utilizadores.size();
    }

    public boolean estaVazio() {
        return utilizadores.isEmpty();
    }
    public boolean verificaLogin(String user, String pass) {
        Ator ator = new Ator();
        for(Entry a : this.utilizadores.entrySet())
        {
            ator= (Ator) a.getKey();
            if(ator.nome.equals(user) && ator.password.equals(pass))
            {
                this.logInfo=true;
                break;
            }
            else
                this.logInfo=false;
        }

        return this.logInfo;
    }


}
