
package laivanupotus.poikkeukset;

/**
 * Pelialue heittää tämän poikkeuksen jos väärä käyttäjä yrittää hyödyntää sen 
 * tarjoamaa palvelua. Näin tapahtuu jos pelaaja yrittää ampua omalle 
 * pelialueelleen tai asettaa laivan vastustajan pelialueelle.
 *
 * @author John Lång
 */
public final class VaaraPelialueException extends Exception {
    
    public VaaraPelialueException(String viesti) {
        super(viesti);
    }
    
}
