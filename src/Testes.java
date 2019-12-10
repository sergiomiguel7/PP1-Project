import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Testes
{
        AtorDB nova = new AtorDB();
    /**
     * Default constructor for test class teste
     */
    public Testes()
    {
        String escolhido = "Pessoas";
        Servico a= new SPessoas(5, true);

       Ator a1 = new Cliente("sergio@gmail.com", "Sergio", "vitoria", "Rua da", LocalDate.now(), 1, 2);
       Ator a2 = new Transportes("uber@gmail.com", "Uber", "vitoriag", "Rua da", LocalDate.now(),a, 7, 2, 7,1,0);
       nova.Add(a1.getEmail(), a1);
       nova.Add(a2.getEmail(), a2);
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {

    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    @Test
        public void testeRegisto()
    {
        Ator teste = new Cliente("a","Teste", "passteste", "a", LocalDate.now(), 1 , 3);
        nova.Add(teste.getEmail(), teste);
        assertTrue(nova.getUtilizadores().containsValue(teste));
    }
    @Test
    public void testeLogin()
    {
        boolean a = nova.verificaLogin("sergio@gmail.com", "vitoria");
        assertTrue(a);
        boolean b = nova.verificaLogin("uber@gmail.com", "vitoria");
        assertFalse(b);
    }
    @Test
    public void testeEfetuarPedido()
    {
        Ator teste = nova.getAtor("sergio@gmail.com");
        Servico testeS = new SPessoas();
        ((Cliente)teste).AddPedido(nova.getTransportes("uber@gmail.com"), testeS);
        assertEquals(1, teste.getHistorico().getPedidos().size());
        assertEquals(1, nova.getTransportes("uber@gmail.com").getHistorico().getPedidos().size());
    }

}
