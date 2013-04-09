
package laivanupotus.poikkeukset;

/**
 *
 * @author John Lång
 */
public final class RuutuunOnJoAmmuttuException extends Exception {
    
    public RuutuunOnJoAmmuttuException() {
        super("Sääntörikkomus: Ruutuun yritettiin ampua uudelleen.");
    }
    
}
