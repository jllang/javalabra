
package laivanupotus.kontrolli;

import laivanupotus.kayttajat.Tekoalypelaaja;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import java.util.Random;
import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.kontrolli.sijoitus.LaivastonSijoitus;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Saannot;

/**
 * Laivanupotuspelin pääluokka, joka vastaa komentoriviparametrien tulkinnasta 
 * sekä ohjelman suorituksesta.
 *
 * @author John Lång
 */
public final class Laivanupotus {
    
    private static boolean varitOnKaytossa;

    /**
     * Metodin vastuulla on ohjelman keskeisimpien komponenttien luominen sekä 
     * pelin aikana mahdollisti ilmenevien fataalien virheiden käsittelyn 
     * ohjaaminen poikkeustenkäsittelijälle.
     * 
     * @param parametrit Käyttöjärjestelmän komentotulkissa ohjelmalle annetut 
     * valinnaiset parametrit.
     */
    public static void main(String[] parametrit) {
        kasitteleParametrit(parametrit);
        
        Kayttoliittyma kl = new Tekstikayttoliittyma(varitOnKaytossa);
        kl.run();
        Poikkeustenkasittelija poka = new Poikkeustenkasittelija(kl, true, false);
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
            ls.sijoitaLaivasto(p1, true);
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
    private static boolean kasitteleParametrit(String[] parametrit) {
        varitOnKaytossa = false;
        if (parametrit != null && parametrit.length > 0 && parametrit[0] != null
                && !parametrit[0].isEmpty()) {
            for (int i = 0; i < parametrit.length; i++) {
                switch (parametrit[i]) {
                    default:
                        varitOnKaytossa = false;
                        break;
                    case "varit":
                        varitOnKaytossa = true;
                }
            }
        }
        return varitOnKaytossa;
    }
}
