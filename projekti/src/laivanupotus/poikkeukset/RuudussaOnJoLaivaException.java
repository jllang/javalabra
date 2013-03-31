/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.poikkeukset;

/**
 *
 * @author John Lång
 */
public class RuudussaOnJoLaivaException extends Exception {

    public RuudussaOnJoLaivaException() {
        super("Sääntörikkomus: Ruutuun yritettiin asettaa uudelleen laiva.");
    }
    
}
