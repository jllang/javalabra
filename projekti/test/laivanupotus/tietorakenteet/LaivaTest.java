
package laivanupotus.tietorakenteet;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author John Lång
 */

public class LaivaTest {
    
    private Laiva           laiva;
    private Piste           a, b, c, d;
    
    public LaivaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Testataan luokka Laiva...");
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("==================================================="
                + "=============================\n");
    }
    
    @Before
    public void setUp() {
        List<Piste> pistelista          = new ArrayList<>();
        List<Integer> koordinaattilista = new ArrayList<>();
        a = new Piste();
        b = new Piste();
        c = new Piste();
//        d = a;
        pistelista.add(a);
        pistelista.add(b);
        pistelista.add(c);
//        pistelista.add(d);
        koordinaattilista.add(0);
        koordinaattilista.add(0);
        koordinaattilista.add(4);
        koordinaattilista.add(7);
        koordinaattilista.add(9);
        koordinaattilista.add(1);
        this.laiva = new Laiva(pistelista, koordinaattilista);
    }
    
    @After
    public void tearDown() {
        System.out.println("---------------------------------------------------"
                + "-----------------------------\n");
    }

    @Test
    public void testUpposi1() {
        System.out.println("Testataan metodia upposi...");
        a.onAmmuttu = true;
        assertEquals(false, laiva.upposi());
    }
    
    @Test
    public void testUpposi2() {
        System.out.println("Testataan uudelleen metodia upposi...");
        a.onAmmuttu = true;
        b.onAmmuttu = true;
        c.onAmmuttu = true;
        assertEquals(true, laiva.upposi());
    }
}
