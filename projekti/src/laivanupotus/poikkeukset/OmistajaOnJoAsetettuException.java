
package laivanupotus.poikkeukset;

/**
 *
 * @author John Lång
 */
public final class OmistajaOnJoAsetettuException extends Exception {
    
    public OmistajaOnJoAsetettuException() {
        super("Sääntörikkomus: Ruudukon omistaja yritettiin vaihtaa.");
    }
    
}
