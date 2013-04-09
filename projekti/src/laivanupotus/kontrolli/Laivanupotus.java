
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
        
        Kayttoliittyma kayttoliittyma = new Tekstikayttoliittyma(varitOnKaytossa);
        kayttoliittyma.run();
        Poikkeustenkasittelija poikkeustenkasittelija = new Poikkeustenkasittelija(kayttoliittyma, true, false);
        Random arpoja = new Random();
//        Saannot saannot = new Saannot(20, 10, 0, laivojenMitatjaMaarat); //Kaatuu; mutta miksi?
        Saannot saannot = new Saannot();
        Pelaaja pelaaja1 = new Ihmispelaaja("Käyttäjä");
        Pelaaja pelaaja2 = new Tekoalypelaaja(arpoja, saannot);
        LaivastonSijoitus laivastonSijoitus = new LaivastonSijoitus(poikkeustenkasittelija, kayttoliittyma);
        laivastonSijoitus.asetaSaannot(saannot);
        
        Pelikierros pelikierros = new Pelikierros(kayttoliittyma, poikkeustenkasittelija, saannot, pelaaja1, pelaaja2);
        kayttoliittyma.asetaPelikierros(pelikierros);
        kayttoliittyma.asetaKatsoja(pelaaja1);
        kayttoliittyma.alusta();
        try {
            laivastonSijoitus.sijoitaLaivasto(pelaaja1, false);
            laivastonSijoitus.sijoitaLaivasto(pelaaja2, false);
            pelikierros.aloita();
        } catch (Exception poikkeus) {
            // Vain fataalien virheiden pitäisi päästä tänne asti.
            poikkeustenkasittelija.kasittele(poikkeus);
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
