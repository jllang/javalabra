
package laivanupotus.kayttajat;

import laivanupotus.tietorakenteet.Pelialue;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.poikkeukset.OmistajaOnJoAsetettuException;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Saannot;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 *
 * @author John Lång
 * 
 * Tämän abstraktin luokan tarkoitus on mallintaa laivanupotuksen pelaajaa.
 * Luokat <tt>Ihmispelaaja</tt> ja <tt>Tekoalypelaaja</tt> perivät tämän luokan.
 */
public abstract class Pelaaja {
    
    private final String    NIMI;
    private Pelialue        pelialue;
    
    public Pelaaja(String nimi) {
        this.NIMI = nimi;
    }
    
    public void asetaPelialue(Pelialue pelialue) {
        this.pelialue = pelialue;
    }

    public Pelialue annaPelialue() {
        return pelialue;
    }
    
    public String kerroNimi() {
        return NIMI;
    }
    
    /**
     * Tämän metodin avulla tuotetaan jostakin pelin toiminnosta vastaavalle 
     * kontrollioliolle uusi luokan <tt>Komento</tt> instanssi. (Tällä hetkellä 
     * metodi toimii ainoastaan luokan <tt>Tekoalypelaaja</tt> olioilla. Ihmisen 
     * antamat komennot tuotetaan käyttöliittymän avulla paitsi jos 
     * ihmispelaajan laivat sijoitetaan automaattisesti pelialueelle.)
     * 
     * @param odotettu Haluttua paluuarvoa koskeva tieto.
     * @return uusi luokan <tt>Komento</tt> instanssi.
     */
    public abstract Komento annaKomento(Komento odotettu) throws Exception;
    
    // odotettu -parametria varten pitäisi ehkä luoda uusi luokka sekaannusten
    // välttämiseksi ja ohjelman sisäisen kommunikaation selkiyttämiseksi.
    
}
