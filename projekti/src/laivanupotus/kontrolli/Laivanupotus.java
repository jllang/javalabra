
package laivanupotus.kontrolli;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.kayttajat.Tekoalypelaaja;
import laivanupotus.kayttoliittymat.GraafinenKayttoliittyma;
import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.kontrolli.sijoitus.LaivastonSijoituttaja;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Saannot;
import laivanupotus.tietorakenteet.enumit.Ruutu;

/**
 * Laivanupotuspelin pääluokka, joka vastaa komentoriviparametrien tulkinnasta 
 * sekä ohjelman suorituksesta.
 *
 * @author John Lång
 */
public final class Laivanupotus {
    
    public static final long KAYNNISTYSAIKA = System.currentTimeMillis();
    
//    private static boolean varitOnKaytossa;
    private static boolean[] asetukset; // Tilapäinen ratkaisu.
    // 0: Asetetaanko laivat käsin (oletus true); 1: Käytetäänkö värejä (false)
    // 2: Jatketaanko ohjelman suoritusta (true); 3: Tulostetaanko debuggausviestit (false)
    // 4: Käytetäänkö debuggausviesteissä aikaleimaa
    // Pitänee myös lisätä parametrit debuggausasetuksille (ks. Poikkeustenkasittelija).
    // Tulevaisuudessa laivojen käsin asettamisen voi valita joka pelikierroksen 
    // alussa.

    /**
     * Metodin vastuulla on ohjelman keskeisimpien komponenttien luominen sekä 
     * pelin aikana mahdollisti ilmenevien fataalien virheiden käsittelyn 
     * ohjaaminen poikkeustenkäsittelijälle.
     * 
     * @param argumentit Käyttöjärjestelmän komentotulkissa ohjelmalle annetut 
     * valinnaiset parametrit.
     */
    public static void main(String[] argumentit) {
        // VAROITUS: Siivoamatonta koodia
        alustaAsetuksetOletusarvoilla();
        kasitteleArgumentit(argumentit);
        if (!asetukset[2]) {
            tulostaOhje();
            return;
        }
        
        asetukset[5] = true;
        asetukset[0] = false;
//        asetukset[3] = true;
//        asetukset[4] = true;
        
        Kayttoliittyma kl;
        if (asetukset[5]) {
            kl = new GraafinenKayttoliittyma();
        } else {
            kl = new Tekstikayttoliittyma(asetukset[1]);
        }
        Poikkeustenkasittelija poka = new Poikkeustenkasittelija(kl, asetukset[3], asetukset[4]);
        Random arpoja = new Random();
//        Saannot s = new Saannot(20, 10, 0, laivojenMitatjaMaarat); //Kaatuu; mutta miksi?
        Saannot s = new Saannot();
        Pelaaja p1 = new Ihmispelaaja("Käyttäjä");
        Pelaaja p2 = new Tekoalypelaaja(arpoja, s);
//        Pelaaja p2 = new Ihmispelaaja("Käyttäjä 2");
        LaivastonSijoituttaja ls = new LaivastonSijoituttaja(poka, kl);
        ls.asetaSaannot(s);
        
        Pelikierros peki = new Pelikierros(kl, poka, s, p1, p2);
        kl.asetaPelikierros(peki);
        kl.asetaKatsoja(p1);
//        kl.alusta();
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
        
//        GraafinenKayttoliittyma kl2 = (GraafinenKayttoliittyma) kl;
//        kl2.tulostaRuudut();
        System.out.println();

        try {
            ls.sijoitaLaivasto(p1, asetukset[0]);
//            kl.tulostaPelitilanne();
//            ls.sijoitaLaivasto(p1, false);
//            kl.tulostaPelitilanne();
            ls.sijoitaLaivasto(p2, false);
//            kl.tulostaPelitilanne();
//            Ruutu[][] r = p1.annaPelialue().annaRuudukko(p1);
//            for (Ruutu[] ruutus : r) {
//                for (Ruutu q : ruutus) {
//                    System.out.println(q);
//                }
//            }
//            kl2 = (GraafinenKayttoliittyma) kl;
//            kl2.tulostaRuudut();
            peki.aloita();
        } catch (Exception poikkeus) {
//            Logger.getLogger(Laivanupotus.class.getName()).log(Level.SEVERE, null, poikkeus);
            poka.kasittele(poikkeus);
        }
//        System.exit(0);
    }

    /**
     * Käsittelee ohjelmalle komentorivillä annetut valinnaiset parametrit.
     * 
     * @param argumentit Tällä hetkellä ainoa käytössäoleva parametri on 
     * <tt>varit</tt>, jonka tarkoituksena on ottaa käyttöön (unixtyyppisten 
     * käyttöjärjestelmien) terminaalissa toimivat tekstin värit.
     * @return Palautetaan <tt>true</tt>, jos värit otetaan käytöön; muutoin 
     * <tt>false</tt>.
     */
    private static void kasitteleArgumentit(String[] argumentit) {        
        if (argumentit != null && argumentit.length > 0 && argumentit[0] != null
                && !argumentit[0].isEmpty()) {
            for (int i = 0; i < argumentit.length; i++) {
                switch (argumentit[i]) {
                    default:
//                        varitOnKaytossa = false;
                        System.err.println("Tuntematon argumentti \""
                                + argumentit[i].trim() + " \".");
                        break;
                    case "a":
                    case "automaattisijoitus":
                        asetukset[0] = false;
                        break;
                    case "v":
                    case "varit":
//                        varitOnKaytossa = true;
                        asetukset[1] = true;
                        break;
                    case "o":
                    case "ohje":
                        asetukset[2] = false;
                        break;
                    case "d":
                    case "debuggaus":
                        asetukset[3] = true;
                        break;
                    case "da":
                    case "aikaleimat":
                        asetukset[4] = true;
                        break;
                    case "g":
                    case "gui":
                        asetukset[5] = true;
                        break;
                }
            }
        }
//        return varitOnKaytossa;
    }
    
    private static void alustaAsetuksetOletusarvoilla() {
        asetukset = new boolean[6];
        asetukset[0] = true;   // Laivojen sijoitus käsin
        asetukset[1] = false;   // Värit
        asetukset[2] = true;    // Jatketaanko suoritusta
        asetukset[3] = false;   // Debuggausviestit
        asetukset[4] = false;   // Debuggauksen aikaleimat
        asetukset[5] = false;   // Graafinen käyttöliittymä
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
        System.out.println("    varit               v       Värilliset tekstit "
                + "käyttöön terminaalissa.");
        System.out.println("    automaattisijoitus  a       Pelaajan laivat "
                + "asetetaan automaattisesti.");
        System.out.println("    debuggaus           d       (Debuggaus) "
                + "Tulostetaanko debuggausviestit.");
        System.out.println("    aikaleimat          da      (Debuggaus) "
                + "Käytetäänkö aikaleimoja");
        System.out.println("    gui                 g       Graafinen "
                + "käyttöliittymä");
        System.out.println();
    }

}
