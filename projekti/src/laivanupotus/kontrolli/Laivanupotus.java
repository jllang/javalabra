/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import laivanupotus.kayttajat.Tekoalypelaaja;
import laivanupotus.kayttajat.Ihmispelaaja;
import laivanupotus.kayttajat.Pelaaja;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tietorakenteet.Piste;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author John Lång
 */
public final class Laivanupotus {
    
    private static boolean varitOnKaytossa;

    /**
     * @param parametrit Käyttöjärjestelmän komentotulkissa ohjelmalle annetut
     * parametrit.
     */
    public static void main(String[] parametrit) {
        kasitteleParametrit(parametrit);
        
        Kayttoliittyma kayttoliittyma = new Tekstikayttoliittyma(varitOnKaytossa);
        kayttoliittyma.run();
        Poikkeustenkasittelija pk = new Poikkeustenkasittelija(kayttoliittyma, true, false);
        Random arpoja = new Random();
//        Map<Integer, Integer> laivojenMitatjaMaarat = new TreeMap<>();
////        laivojenMitatjaMaarat.put(1, 4);
////        laivojenMitatjaMaarat.put(2, 3);
////        laivojenMitatjaMaarat.put(3, 2);
////        laivojenMitatjaMaarat.put(4, 1);
//        laivojenMitatjaMaarat.put(1, 2);
//        laivojenMitatjaMaarat.put(2, 1);
//        laivojenMitatjaMaarat.put(3, 1);
//        Saannot saannot = new Saannot(5, 5, 0, laivojenMitatjaMaarat);
//        Saannot saannot = new Saannot(20, 10, 0, laivojenMitatjaMaarat); //Kaatuu
        Saannot saannot = new Saannot();
        Pelaaja p1 = new Ihmispelaaja();
        Pelaaja p2 = new Tekoalypelaaja(arpoja, saannot);
        Pelikierros pelikierros = new Pelikierros(kayttoliittyma, pk, arpoja, saannot, p1, p2);
        try {
            pelikierros.aloita();
        } catch (Exception poikkeus) {
            // Vain fataalien virheiden pitäisi päästä tänne asti.
            pk.kasittele(poikkeus);
        }
        
    }

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
