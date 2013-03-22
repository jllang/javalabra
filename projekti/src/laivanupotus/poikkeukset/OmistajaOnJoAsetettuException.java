/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.poikkeukset;

/**
 *
 * @author johnny
 */
public class OmistajaOnJoAsetettuException extends Exception {
    
    public OmistajaOnJoAsetettuException() {
        super("Sääntörikkomus: Ruudukon omistaja yritettiin vaithaa.");
    }
    
}
