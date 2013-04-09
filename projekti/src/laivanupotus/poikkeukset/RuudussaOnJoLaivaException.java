
package laivanupotus.poikkeukset;

/**
 *
 * @author John Lång
 */
public final class RuudussaOnJoLaivaException extends Exception {

    public RuudussaOnJoLaivaException() {
        super("Sääntörikkomus: Ruutuun yritettiin asettaa uudelleen laiva.");
    }
    
}
