
package laivanupotus.tietorakenteet;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        Queue<Piste> pistejono = new ArrayDeque<>();
        a = new Piste();
        b = new Piste();
        c = new Piste();
        d = a;
        pistejono.add(a);
        pistejono.add(b);
        pistejono.add(c);
        pistejono.add(d);
        this.laiva = new Laiva(pistejono);
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
        System.out.println(a == null);
        System.out.println(b == null);
        System.out.println(c == null);
        System.out.println(laiva == null);
        a.onAmmuttu = true;
        b.onAmmuttu = true;
        c.onAmmuttu = true;
        assertEquals(true, laiva.upposi());
    }
}
