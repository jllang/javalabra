/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.tyypit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author johnny
 */
public class SaannotTest {
    
    private Saannot         saannot;
    private static Random   arpoja;
    private static String[] saantojenNimet;
    
    public SaannotTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        arpoja = new Random();
        saantojenNimet = new String[] {"leveydeksi", "korkeudeksi", "vuorojen määräksi"};
    }
    
    @AfterClass
    public static void tearDownClass() {
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
        Integer[] saantojenArvot = arvoSaantojenArvot();
        Saannot saannot2 = new Saannot(saantojenArvot[0], saantojenArvot[1], saantojenArvot[2] * -1);
    }
    
    @Test
    public void testSaannotOletusarvoilla() {
        System.out.println("Testataan säännöt oletusarvoilla...\n");
        Integer[] saantojenArvot = {10, 10, 0};
        List<Object> saadutArvot = luoListaKentista(saannot);
        vertaile(saantojenArvot, saadutArvot);
    }
    
    @Test
    public void testSaannotSatunnaisillaArvoilla() {
        System.out.println("Testataan säännöt satunnaisilla arvoilla...\n");
        Integer[] saantojenArvot = arvoSaantojenArvot();
        Saannot saannot2 = new Saannot(saantojenArvot[0], saantojenArvot[1], saantojenArvot[2]);
        List<Object> saadutArvot = luoListaKentista(saannot2);
        vertaile(saantojenArvot, saadutArvot);
    }
    
    @Test
    public void testAnnaSisaltoSatunnaisillaArvoilla() {
        System.out.println("Testataan metodi annaSisalto satunnaisilla arvoilla...\n");
        Integer[] saantojenArvot = arvoSaantojenArvot();
        Saannot saannot2 = new Saannot(saantojenArvot[0], saantojenArvot[1], saantojenArvot[2]);
        List<Object> sisalto = saannot2.annaSisalto();
        vertaile(saantojenArvot, sisalto);
    }
    
    private List<Object> luoListaKentista(Saannot olio) {
        List<Object> saadutArvot = new ArrayList<>();
        saadutArvot.add(olio.leveys());
        saadutArvot.add(olio.korkeus());
        saadutArvot.add(olio.vuoroja());
        return saadutArvot;
    }
    
    private void vertaile(Integer[] saantojenArvot, List<Object> saadutArvot) {
        for (int i = 0; i < 3; i++) {
            System.out.println("Odotettu arvo: " + saantojenArvot[i] + ", saatu arvo: " + saadutArvot.get(i));
            assertEquals(saantojenArvot[i], saadutArvot.get(i));
        }
    }
    
    private Integer[] arvoSaantojenArvot() {
        Integer[] saantojenArvot = new Integer[3];
        
//        for (int i = 0; i < 3; i++) {
//            Integer maara = arpoja.nextInt(Integer.MAX_VALUE);
//            saantojenArvot[i] = maara;
//            System.out.println("Testattavaksi " + saantojenNimet[i] + " arvottiin " + maara + ".");
//        }
        int maara = arpoja.nextInt(15) + 5;
        saantojenArvot[0] = maara;
        System.out.println("Testattavaksi " + saantojenNimet[0] + " arvottiin " + maara + ".");
        maara = arpoja.nextInt(15) + 5;
        saantojenArvot[1] = maara;
        System.out.println("Testattavaksi " + saantojenNimet[1] + " arvottiin " + maara + ".");
        maara = arpoja.nextInt(Integer.MAX_VALUE);
        saantojenArvot[2] = maara;
        System.out.println("Testattavaksi " + saantojenNimet[2] + " arvottiin " + maara + ".");
        
        System.out.println();
        
        return saantojenArvot;
    }

}
