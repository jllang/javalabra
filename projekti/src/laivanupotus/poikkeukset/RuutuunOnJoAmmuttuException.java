/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.poikkeukset;

/**
 *
 * @author John Lång
 */
public class RuutuunOnJoAmmuttuException extends Exception {
    
    public RuutuunOnJoAmmuttuException() {
        super("Sääntörikkomus: Ruutuun yritettiin ampua uudelleen.");
    }
    
}
