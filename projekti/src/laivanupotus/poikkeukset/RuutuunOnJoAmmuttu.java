/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.poikkeukset;

/**
 *
 * @author John Lång
 */
public class RuutuunOnJoAmmuttu extends Exception {
    
    public RuutuunOnJoAmmuttu() {
        super("Sääntörikkomus: Ruutuun yritettiin ampua uudelleen.");
    }
    
}
