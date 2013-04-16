
package laivanupotus.poikkeukset;

/**
 * Tämä poikkeus heitetään jos jokin pelaaja yrittää ampua sellaiseen ruutuun 
 * pelialueella, johon on jo kertaalleen ammuttu.
 *
 * @author John Lång
 */
public final class RuutuunOnJoAmmuttuException extends Exception {
    
    public RuutuunOnJoAmmuttuException() {
        super("Sääntörikkomus: Ruutuun yritettiin ampua uudelleen.");
    }
    
}
