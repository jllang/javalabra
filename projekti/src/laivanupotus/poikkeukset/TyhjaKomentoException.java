/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.poikkeukset;

/**
 *
 * @author John Lång
 */
public class TyhjaKomentoException extends Exception {
    
    public TyhjaKomentoException() {
        super("Mitään komentoa ei annettu");
    }

}
