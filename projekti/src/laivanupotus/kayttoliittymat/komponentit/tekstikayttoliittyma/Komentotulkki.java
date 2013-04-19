
package laivanupotus.kayttoliittymat.komponentit.tekstikayttoliittyma;

import java.util.HashMap;
import java.util.Map;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 * Tämän luokan on tarkoitus toimia luokan <tt>Tekstikayttoliittyma</tt> 
 * komponenttina. Komentotulkki muuntaa käyttöliittymän välittämän ihmisen 
 * syöttämän merkkijonon ohjelman sisäisessä kontrollissa käytettäväksi luokan 
 * <tt>Komento</tt> instanssiksi.
 *
 * @author John Lång
 */
public final class Komentotulkki {
    
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
    
    /**
     * Tämän metodin tarkoituksena on muuntaa ihmisen antamat merkkijonot luokan
     * <tt>Komento</tt> instansseiksi.
     *
     * @param syote Syötteenä annettu tarkastettava ja tulkittava merkkijono.
     * @return Uusi, oikeanmuotoiseksi varmistettu, luokan <tt>Komento</tt> 
     * instanssi.
     * @throws IllegalArgumentException Virhe joka palautetaan jos merkkijonoa 
     * ei pystytty tulkitsemaan kelvolliseksi komennoksi.
     */
    public Komento tulkitse(String syote) throws IllegalArgumentException {
        if (syote == null || syote.isEmpty()) {
            throw new IllegalArgumentException("Pelaaja antoi tyhjän komennon.");
        }
        String[] syotteenOsat = syote.split(" ");
        switch (syotteenOsat[0].trim()) {
            case "":
                return new Komento();
            case "LOPETA":
                return new Komento(Komentotyyppi.LOPETA);
            case "OHJE":
                return new Komento(Komentotyyppi.OHJEET);
//            case "PAIVITA":
//                return new Komento(Komentotyyppi.PAIVITA_KAYTTOLIITTYMA);
            case "LAIVOJA":
                return new Komento(Komentotyyppi.TILAKYSELY,
                        Komento.TILAKYSELY_LAIVAT);
            case "VUOROJA":
                return new Komento(Komentotyyppi.TILAKYSELY,
                        Komento.TILAKYSELY_VUOROT);
            case "ASETA":
                tarkistaSyotteenOsienMaara(syotteenOsat, 4);
                tarkistaKoordinaatit(syotteenOsat);
                return new Komento(Komentotyyppi.SIJOITA_LAIVA,
                    KIRJAINKARTTA.get(syotteenOsat[1]),
                    Integer.parseInt(syotteenOsat[2]) - 1,
                        Integer.parseInt(syotteenOsat[3]));
            case "AMMU":
                tarkistaSyotteenOsienMaara(syotteenOsat, 3);
                tarkistaKoordinaatit(syotteenOsat);
                return new Komento(Komentotyyppi.AMMU,
                    KIRJAINKARTTA.get(syotteenOsat[1]),
                    Integer.parseInt(syotteenOsat[2]) - 1);
            case "LUOVUTA":
                return new Komento(Komentotyyppi.LUOVUTA);
            case "EN": // "Debuggausta" varten. :P
                if (syotteenOsat[1].trim().equals("VAIN")) {
                    if (syotteenOsat[2].trim().equals("OSAA")) {
                        return new Komento(Komentotyyppi.GOD_MODE);
                    }
                }
            default:
                return new Komento(Komentotyyppi.TUNTEMATON);
        }
    }
    
    private void tarkistaSyotteenOsienMaara(String[] syotteenOsat,
            int haluttuMaara) throws IllegalArgumentException {
        // Tarkastetaan vain koordinaattien määrä, ei oikeellisuutta:
        if (syotteenOsat[1].matches("[^A-T]")
                || syotteenOsat[2].matches("[^0-9]")) {
            throw new IllegalArgumentException("Annettujen parametrien määrä "
                    + "oli virheellinen");
        }
    }

    private void tarkistaKoordinaatit(String[] koordionaatit)
            throws IllegalArgumentException {
        // Tarkastetaan vain koordinaattien muoto, ei oikeellisuutta:
        // (Oikeellisuuden tarkastaminen kuuluu luokalle Pelialue.)
        if (koordionaatit[1].matches("[^A-T]")
                || koordionaatit[2].matches("[^0-9]")) {
            throw new IllegalArgumentException("Komennon "
                    + "koordinaatit olivat virheelliset.");
        }
    }

}
