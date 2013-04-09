
package laivanupotus.poikkeukset;

/**
 *
 * @author John Lång
 */
public final class TyhjaKomentoException extends Exception {
    
    public TyhjaKomentoException() {
        super("Mitään komentoa ei annettu");
    }

}
