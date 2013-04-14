
package laivanupotus.kontrolli;

import laivanupotus.kayttajat.Tekoalypelaaja;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import java.util.Random;
import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.kontrolli.sijoitus.LaivastonSijoitus;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.rajapinnat.Tallentaja;
import laivanupotus.tallentajat.Muistitallentaja;
import laivanupotus.tallentajat.Tiedostotallentaja;
import laivanupotus.tietorakenteet.Saannot;

/**
 * Laivanupotuspelin pääluokka, joka vastaa komentoriviparametrien tulkinnasta 
 * sekä ohjelman suorituksesta.
 *
 * @author John Lång
 */
public final class Laivanupotus {
    
//    private static boolean varitOnKaytossa;
    private static boolean[] asetukset; // Tilapäinen ratkaisu.
    // 0: Asetetaanko laivat käsin (oletus true); 1: Käytetäänkö värejä (false)
    // 2: Jatketaanko ohjelman suoritusta (true)
    // Pitänee myös lisätä parametrit debuggausasetuksille (ks. Poikkeustenkasittelija).
    // Tulevaisuudessa laivojen käsin asettamisen voi valita joka pelikierroksen 
    // alussa.

    /**
     * Metodin vastuulla on ohjelman keskeisimpien komponenttien luominen sekä 
     * pelin aikana mahdollisti ilmenevien fataalien virheiden käsittelyn 
     * ohjaaminen poikkeustenkäsittelijälle.
     * 
     * @param parametrit Käyttöjärjestelmän komentotulkissa ohjelmalle annetut 
     * valinnaiset parametrit.
     */
    public static void main(String[] parametrit) {
        // VAROITUS: Siivoamatonta koodia
        kasitteleParametrit(parametrit);
        if (!asetukset[2]) {
            return;
        }
        
        Kayttoliittyma kl = new Tekstikayttoliittyma(asetukset[1]);
        kl.run();
        Poikkeustenkasittelija poka = new Poikkeustenkasittelija(kl, true, false);
//        Tallentaja t1 = new Tiedostotallentaja();
//        Tallentaja t2 = new Muistitallentaja();
//        t1.tallenna();
        Random arpoja = new Random();
//        Saannot s = new Saannot(20, 10, 0, laivojenMitatjaMaarat); //Kaatuu; mutta miksi?
        Saannot s = new Saannot();
        Pelaaja p1 = new Ihmispelaaja("Käyttäjä");
        Pelaaja p2 = new Tekoalypelaaja(arpoja, s);
        LaivastonSijoitus ls = new LaivastonSijoitus(poka, kl);
        ls.asetaSaannot(s);
        
        Pelikierros peki = new Pelikierros(kl, poka, s, p1, p2);
        kl.asetaPelikierros(peki);
        kl.asetaKatsoja(p1);
        kl.alusta();
        try {
            ls.sijoitaLaivasto(p1, asetukset[0]);
            ls.sijoitaLaivasto(p2, false);
            peki.aloita();
        } catch (Exception poikkeus) {
            // Vain fataalien virheiden pitäisi päästä tänne asti.
            poka.kasittele(poikkeus);
        }
        
    }

    /**
     * Käsittelee ohjelmalle komentorivillä annetut valinnaiset parametrit.
     * 
     * @param parametrit Tällä hetkellä ainoa käytössäoleva parametri on 
     * <tt>varit</tt>, jonka tarkoituksena on ottaa käyttöön (unixtyyppisten 
     * käyttöjärjestelmien) terminaalissa toimivat tekstin värit.
     * @return Palautetaan <tt>true</tt>, jos värit otetaan käytöön; muutoin 
     * <tt>false</tt>.
     */
    private static void kasitteleParametrit(String[] parametrit) {
//        varitOnKaytossa = false;
        asetukset = new boolean[3];
        //Alustetaan oletusarvot:
        asetukset[0] = true;
        asetukset[1] = false;
        asetukset[2] = true;
        
        if (parametrit != null && parametrit.length > 0 && parametrit[0] != null
                && !parametrit[0].isEmpty()) {
            for (int i = 0; i < parametrit.length; i++) {
                switch (parametrit[i]) {
                    default:
//                        varitOnKaytossa = false;
                        break;
                    case "automaattisijoitus":
                        asetukset[0] = false;
                        break;
                    case "varit":
//                        varitOnKaytossa = true;
                        asetukset[1] = true;
                        break;
                    case "ohje":
                        // Kesken. Tarkoituksena tulostaa tieto mahdollisista 
                        // käynnistysparametreista ja lopettaa ohjelman suoritus.
                        asetukset[2] = false;
                        break;
                }
            }
        }
//        return varitOnKaytossa;
    }
}
