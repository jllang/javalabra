/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.kontrolli;

import java.util.HashMap;
import java.util.Map;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Komentotyyppi;

/**
 *
 * @author John Lång
 */
public class Komentotulkki {
    
    private final Map<String, Integer> KIRJAINKARTTA;
    
    public Komentotulkki() {
        this.KIRJAINKARTTA = new HashMap<>();
        KIRJAINKARTTA.put("A", 0);
        KIRJAINKARTTA.put("B", 1);
        KIRJAINKARTTA.put("C", 2);
        KIRJAINKARTTA.put("D", 3);
        KIRJAINKARTTA.put("E", 4);
        KIRJAINKARTTA.put("F", 5);
        KIRJAINKARTTA.put("G", 6);
        KIRJAINKARTTA.put("H", 7);
        KIRJAINKARTTA.put("I", 8);
        KIRJAINKARTTA.put("J", 9);
        KIRJAINKARTTA.put("K", 10);
        KIRJAINKARTTA.put("L", 11);
        KIRJAINKARTTA.put("M", 12);
        KIRJAINKARTTA.put("N", 13);
        KIRJAINKARTTA.put("O", 14);
        KIRJAINKARTTA.put("P", 15);
        KIRJAINKARTTA.put("Q", 16);
        KIRJAINKARTTA.put("R", 17);
        KIRJAINKARTTA.put("S", 18);
        KIRJAINKARTTA.put("T", 19);
    }
    
    public Komento tulkitse(String syote) throws Exception {
        String[] syotteenOsat = syote.split(" ");
        Komento tulkittuKomento;
        switch (syotteenOsat[0]) {
            case "":
                tulkittuKomento = new Komento(Komentotyyppi.TYHJA);
                break;
            case "LOPETA":
                tulkittuKomento = new Komento(Komentotyyppi.LOPETA);
                break;
            case "PAIVITA":
                tulkittuKomento = new Komento(Komentotyyppi.PAIVITA_KAYTTOLIITTYMA);
                break;
            case "AMMU":
                tarkistaKoordinaattiParametrit(syotteenOsat);
                tulkittuKomento = new Komento(Komentotyyppi.AMMU,
                    KIRJAINKARTTA.get(syotteenOsat[1]),
                    Integer.parseInt(syotteenOsat[2]) - 1);
                break;
            default:
                tulkittuKomento = new Komento(Komentotyyppi.TUNTEMATON);
        }
        return tulkittuKomento;
    }

    private void tarkistaKoordinaattiParametrit(String[] syotteenOsat) throws IllegalArgumentException {
        // Tarkastetaan vain koordinaattien muoto, ei oikeellisuutta:
        // (Oikeellisuuden tarkastaminen kuuluu luokalle Pelialue.)
        if (syotteenOsat[1].matches("[^A-T]")
                || syotteenOsat[2].matches("[^0-9]")) {
            throw new IllegalArgumentException("Ampumiskomennon"
                    + "koordinaatit olivat virheelliset");
        }
    }

}
