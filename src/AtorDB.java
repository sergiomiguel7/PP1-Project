import java.util.ArrayList;

public class AtorDB {
    private ArrayList<String> utilizadores;
    private boolean logInfo;


    public AtorDB() {
        this.utilizadores = new ArrayList<String>();
        logInfo=false;
    }

    public ArrayList<String> getUtilizadores()
    {
        return this.utilizadores;

    }

    public void setUtilizadores(ArrayList<String> utilizadores) {
        this.utilizadores = utilizadores;
    }

    public boolean isLogInfo() {
        return logInfo;
    }

    public void setLogInfo(boolean logInfo) {
        this.logInfo = logInfo;
    }

    public void Add(String nome, String password) {
        utilizadores.add(nome+"*"+password);
    }


    public boolean verificaLogin(String user, String pass) {
        String[] parts = new String[2];
        int i=0;
        for(String a : this.utilizadores)
        {
            parts=utilizadores.get(i).split("\\*");
            i++;
            if(parts[0].equals(user) && parts[1].equals(pass)) {
                logInfo = true;
                break;
            }
            else
                logInfo=false;
        }
        return logInfo;
    }


}