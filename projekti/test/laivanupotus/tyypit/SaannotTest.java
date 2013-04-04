/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.tyypit;

import laivanupotus.tietorakenteet.Saannot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import laivanupotus.kontrolli.SaantojenArpoja;

/**
 *
 * @author John Lång
 */
public class SaannotTest {
    
    private static SaantojenArpoja  saantokone;
    private Saannot                 saannot;
    
    public SaannotTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        saantokone = new SaantojenArpoja(new Random());
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("================================================================================\n");
    }
    
    @Before
    public void setUp() {
        this.saannot = new Saannot();
    }
    
    @After
    public void tearDown() {
        System.out.println("--------------------------------------------------------------------------------\n");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEpakelvotSaannot() {
        System.out.println("Testataan järjenvastaisten sääntöjen luomista...\n");
        int[] saantojenArvot = saantokone.arvoTauluSaannoista();
        Saannot saannot2 = new Saannot(saantojenArvot[0], saantojenArvot[1], saantojenArvot[2] - 1, saantokone.luoVakiotLaivojenMitatJaMaarat());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEpakelvotSaannot2() {
        System.out.println("Testataan lisää järjenvastaisten sääntöjen luomista...\n");
        Map<Integer, Integer> laivaKartta = new TreeMap<>();
        laivaKartta.put(6, 1);
        Saannot saannot2 = new Saannot(5, 5, 0, laivaKartta);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEpakelvotSaannot3() {
        System.out.println("Testataan lisää järjenvastaisten sääntöjen luomista...\n");
        Map<Integer, Integer> laivaKartta = new TreeMap<>();
        laivaKartta.put(5, 1);
        laivaKartta.put(4, 2);
        laivaKartta.put(3, 0);
        laivaKartta.put(2, 1);
        laivaKartta.put(1, 0);
        Saannot saannot2 = new Saannot(5, 5, 0, laivaKartta);
    }
    
    @Test
    public void testSaannotOletusarvoilla() {
        System.out.println("Testataan säännöt oletusarvoilla...\n");
        int[] saantojenArvot = {10, 10, 0, 4, 4, 6, 6, 4};
        List<Object> saadutArvot = luoListaKentista(saannot);
        vertaile(saantojenArvot, saadutArvot);
    }
    
    @Test
    public void testSaannotSatunnaisillaArvoilla() {
        System.out.println("Testataan säännöt satunnaisilla arvoilla...\n");
        int[] saantojenArvot = saantokone.arvoTauluSaannoista();
        Saannot saannot2 = new Saannot(saantojenArvot[0], saantojenArvot[1], saantojenArvot[2], saantokone.luoVakiotLaivojenMitatJaMaarat());
        List<Object> saadutArvot = luoListaKentista(saannot2);
        vertaile(saantojenArvot, saadutArvot);
    }
    
    @Test
    public void testAnnaSisaltoSatunnaisillaArvoilla() {
        System.out.println("Testataan metodi annaSisalto satunnaisilla arvoilla...\n");
        int[] saantojenArvot = saantokone.arvoTauluSaannoista();
        Saannot saannot2 = new Saannot(saantojenArvot[0], saantojenArvot[1], saantojenArvot[2], saantokone.luoVakiotLaivojenMitatJaMaarat());
        List<Object> sisalto = saannot2.annaSisalto();
        vertaile(saantojenArvot, sisalto);
    }
        
    @Test
    public void testRakennaSisalto() {
        
    }
    
    @Test
    public void testAnnaLaivojenMitatJaMaarat() {
        System.out.println("Testataan metodi annaLaivojenMitatJaMaarat...");
        
        Map<Integer, Integer> odotetutMitatJaMaarat = new TreeMap<>();
        odotetutMitatJaMaarat.put(4, 1);
        odotetutMitatJaMaarat.put(3, 2);
        odotetutMitatJaMaarat.put(2, 3);
        odotetutMitatJaMaarat.put(1, 4);
        
        Map<Integer, Integer> saadutMitatJaMaarat = saannot.annaLaivojenMitatJaMaarat();
        
        System.out.println("Odotettiin: " + odotetutMitatJaMaarat.toString());
        System.out.println("Saatiin:    " + saadutMitatJaMaarat.toString());
        
        assertEquals(odotetutMitatJaMaarat, saadutMitatJaMaarat);
    }
    
    private List<Object> luoListaKentista(Saannot olio) {
//        List<Object> saadutArvot = new ArrayList<>();
//        saadutArvot.add(olio.leveys());
//        saadutArvot.add(olio.korkeus());
//        saadutArvot.add(olio.vuoroja());
//        return saadutArvot;
        return olio.annaSisalto();
    }
    
    private void vertaile(int[] saantojenArvot, List<Object> saadutArvot) {
        if(saantojenArvot.length != saadutArvot.size()) {
            fail("Odotettujen ja saatujen arvojen määrät eivät täsmänneet.");
        }
        for (int i = 0; i < saantojenArvot.length; i++) {
            System.out.println("Odotettu arvo: " + saantojenArvot[i] + ", saatu arvo: " + saadutArvot.get(i));
            assertEquals(saantojenArvot[i], saadutArvot.get(i));
        }
    }

}
