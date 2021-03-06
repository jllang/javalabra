
package laivanupotus.kontrolli;

import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import laivanupotus.kayttajat.*;
import laivanupotus.kayttoliittymat.*;
import laivanupotus.kontrolli.sijoitus.LaivastonSijoituttaja;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Saannot;

/**
 * Laivanupotuspelin pääluokka, joka vastaa komentoriviparametrien tulkinnasta 
 * sekä ohjelman suorituksesta.
 *
 * @author John Lång
 */
public final class Laivanupotus {
    
    public static final long KAYNNISTYSAIKA = System.currentTimeMillis();
    
    private static boolean[] asetukset;

    /**
     * Metodin vastuulla on ohjelman keskeisimpien komponenttien luominen sekä 
     * pelin aikana mahdollisti ilmenevien fataalien virheiden käsittelyn 
     * ohjaaminen poikkeustenkäsittelijälle.
     * 
     * @param argumentit Käyttöjärjestelmän komentotulkissa ohjelmalle annetut 
     * valinnaiset parametrit.
     */
    public static void main(String[] argumentit) {
        alustaAsetuksetOletusarvoilla();
        kasitteleArgumentit(argumentit);
        if (asetukset[2]) {
            tulostaOhje();
            System.exit(0);
        }
        
        Kayttoliittyma kl;
        if (asetukset[5]) {
            kl = new GraafinenKayttoliittyma();
        } else {
            kl = new Tekstikayttoliittyma(asetukset[1]);
        }
        Poikkeustenkasittelija poka = new Poikkeustenkasittelija(kl, asetukset[3], asetukset[4]);
        Random arpoja               = new Random();
        Saannot s                   = luoSaannot();
        Pelaaja p1                  = new Ihmispelaaja("Käyttäjä");
        Pelaaja p2                  = new Tekoalypelaaja(arpoja, s);
        LaivastonSijoituttaja ls    = new LaivastonSijoituttaja(poka, kl);
        ls.asetaSaannot(s);
        
        Pelikierros peki = new Pelikierros(kl, poka, s, p1, p2);
        kl.asetaPelikierros(peki);
        kl.asetaKatsoja(p1);
        if (asetukset[5]) {
            try {
                SwingUtilities.invokeAndWait(kl);
            } catch (Exception poikkeus) {
                if (asetukset[3]) {
                    Logger.getLogger(Laivanupotus.class.getName()).log(Level.SEVERE, null, poikkeus);
                }
            }
        }
        kl.alusta();

        try {
            if (asetukset[0]) {
                kl.tulostaViesti("Laivojen sijoittaminen alkoi.\n");
                ls.sijoitaLaivasto(p1, true);
            } else {
                ls.sijoitaLaivasto(p1, false);
            }
            ls.sijoitaLaivasto(p2, false);
            
            peki.aloita();
        } catch (Exception poikkeus) {
            poka.kasittele(poikkeus);
        }
    }
    
    private static void alustaAsetuksetOletusarvoilla() {
        asetukset = new boolean[7];
        asetukset[0] = true;    // Laivojen sijoitus käsin
        asetukset[1] = false;   // Värit (vain cli)
        asetukset[2] = false;   // Tulostetaanko ohje
        asetukset[3] = false;   // Debuggausviestit
        asetukset[4] = false;   // Debuggauksen aikaleimat
        asetukset[5] = true;    // Graafinen käyttöliittymä
        asetukset[6] = true;    // Käytetäänkö oikeaoppisia sääntöjä
    }
    
    /**
     * Käsittelee ohjelmalle komentorivillä annetut valinnaiset parametrit.
     * 
     */
    private static void kasitteleArgumentit(String[] argumentit) {        
        if (argumentit != null && argumentit.length > 0 && argumentit[0] != null
                && !argumentit[0].isEmpty()) {
            for (int i = 0; i < argumentit.length; i++) {
                switch (argumentit[i]) {
                    default:
                        System.err.println("Tuntematon argumentti \""
                                + argumentit[i].trim() + " \".");
                        break;
                    case "a":
                    case "automaattisijoitus":
                        asetukset[0] = false;
                        break;
                    case "v":
                    case "varit":
                        asetukset[1] = true;
                        break;
                    case "o":
                    case "ohje":
                        asetukset[2] = true;
                        break;
                    case "d":
                    case "debuggaus":
                        asetukset[3] = true;
                        break;
                    case "da":
                    case "aikaleimat":
                        asetukset[4] = true;
                        break;
                    case "c":
                    case "cli":
                        asetukset[5] = false;
                        break;
                    case "vs":
                    case "vaihtoehtosaannot":
                        asetukset[6] = false;
                        break;
                }
            }
        }
    }

    private static void tulostaOhje() {
        System.out.println("Peli voidaan käynnistää komentotulkissa seuraavalla"
                + " komennolla:");
        System.out.println("    java -jar projekti.jar [<argumentti 1>]"
                + "[ <argumentti 2>][... <argumentti n>]");
        System.out.println("Huom. Argumentit pitää erottaa välilyönneillä.");
        System.out.println();
        System.out.println("Taulukko pelin käynnistysargumenteista:");
        System.out.println("------------------------------------");
        System.out.println("    Argumentti          Lyh.    Selitys");
        System.out.println();
        System.out.println("    ohje                o       Näyttää tämän "
                + "ohjeen.");
        System.out.println("    vaihtoehtosaannot   vs      Peli käynnistetään "
                + "toisilla säännöillä.");
        System.out.println("    varit               v       Värilliset tekstit "
                + "käyttöön (vain) terminaalissa.");
        System.out.println("    automaattisijoitus  a       Pelaajan laivat "
                + "asetetaan automaattisesti.");
        System.out.println("    debuggaus           d       (Debuggaus) "
                + "Tulostetaanko debuggausviestit.");
        System.out.println("    aikaleimat          da      (Debuggaus) "
                + "Käytetäänkö aikaleimoja");
//        System.out.println("    gui                 g       Graafinen "
//                + "käyttöliittymä");
        System.out.println("    cli                 c       Tekstikäyttöliittymä");
        System.out.println();
    }

    private static Saannot luoSaannot() {
        //        Saannot s = new Saannot(20, 10, 0, laivojenMitatjaMaarat); //Kaatuu; mutta miksi?
        Saannot saannot;
        if (asetukset[6]) {
            saannot = new Saannot();
        } else {
            TreeMap<Integer, Integer> laivojenMitatJaMaarat = new TreeMap<>();
        
            laivojenMitatJaMaarat.put(5, 1);
            laivojenMitatJaMaarat.put(4, 1);
            laivojenMitatJaMaarat.put(3, 2);
            laivojenMitatJaMaarat.put(2, 1);
            laivojenMitatJaMaarat.put(1, 1);
                    
            saannot = new Saannot(10, 10, 0, laivojenMitatJaMaarat, true);
        }
        
        return saannot;
    }

}
