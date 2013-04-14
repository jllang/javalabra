
package laivanupotus.kontrolli.sijoitus;

import java.util.Random;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.kontrolli.Pelikierros;
import laivanupotus.kontrolli.Poikkeustenkasittelija;
import laivanupotus.poikkeukset.SaantojenvastainenSijoitusException;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.tietorakenteet.Saannot;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LaivansijoitinTest {
    
    private static Poikkeustenkasittelija   poikkeustenkasittelija;
    private static Random                   arpoja;
    private static Saannot                  s;
    private static Kayttoliittyma           kayttoliittyma;

    private static Pelaaja                  p1, p2;

    private Pelikierros                     pk;
    private Pelialue                        pa1, pa2;
    private Laivansijoitin                  ls;
    
    @BeforeClass
    public static void setUpClass() {
        System.out.println("Testataan luokka Laivansijoitin...");
        kayttoliittyma          = new Tekstikayttoliittyma(false);
        poikkeustenkasittelija  = new Poikkeustenkasittelija(kayttoliittyma, false, false);
        arpoja                  = new Random();
        p1                      = new Ihmispelaaja("Uolevi");
        p2                      = new Ihmispelaaja("Hilkka");
        s                       = new Saannot();
        kayttoliittyma.asetaKatsoja(p1);
    }
    
    @AfterClass
    public static void tearDownClass() {
        System.out.println("================================================================================\n");
    }
    
    @Before
    public void setUp() {
        pk  = new Pelikierros(kayttoliittyma, poikkeustenkasittelija, s, p1, p2);
        pa1 = new Pelialue(pk, p1);
        pa2 = new Pelialue(pk, p2);
        ls  = new Laivansijoitin();
        kayttoliittyma.asetaPelikierros(pk);
        kayttoliittyma.alusta();
    }
    
    @After
    public void tearDown() {
        System.out.println("--------------------------------------------------------------------------------\n");
    }

    @Test
    public void testSijoitaLaiva() throws Exception {
        System.out.println("Testataan metodi sijoitaLaiva...");
        int x = arpoja.nextInt(s.leveys() - 4);
        int y = arpoja.nextInt(s.korkeus() - 4);
        int o = arpoja.nextInt(2);
        System.out.println("Sijoitettavan laivan koordinaateiksi arvottiin ("
                + x + "," + y + ").");
        
        ls.asetaPelaaja(p1);
        ls.sijoitaLaiva(x, y, o, 4);
//        kayttoliittyma.alusta();
//        kayttoliittyma.tulostaPelitilanne();
    }
    
    @Test(expected = SaantojenvastainenSijoitusException.class)
    public void testSijoitaLaivaPelialueenUlkopuolelle1() throws Exception {
        System.out.println("Testataan sijoittaa laiva pelialueen ulkopuolelle...");
    
        ls.asetaPelaaja(p1);
        ls.sijoitaLaiva(-1, 13, 1, 3);
    }
    
    @Test(expected = SaantojenvastainenSijoitusException.class)
    public void testSijoitaLaivaPelialueenUlkopuolelle2() throws Exception {
        System.out.println("Testataan sijoittaa laiva pelialueen ulkopuolelle...");
    
        ls.asetaPelaaja(p1);
        ls.sijoitaLaiva(3, 9, 1, 3);
    }
    
    @Test(expected = SaantojenvastainenSijoitusException.class)
    public void testSijoitaKaksiLaivaaPaallekkain1() throws Exception {
        System.out.println("Testataan sijoittaa laiva pelialueen ulkopuolelle...");
    
        ls.asetaPelaaja(p1);
        ls.sijoitaLaiva(3, 9, 0, 3);
        ls.sijoitaLaiva(3, 9, 0, 3);
    }
    
    @Test(expected = SaantojenvastainenSijoitusException.class)
    public void testSijoitaKaksiLaivaaPaallekkain2() throws Exception {
        System.out.println("Testataan sijoittaa laiva pelialueen ulkopuolelle...");
    
        ls.asetaPelaaja(p1);
        ls.sijoitaLaiva(3, 6, 0, 3);
        ls.sijoitaLaiva(4, 5, 0, 1);
    }
}
