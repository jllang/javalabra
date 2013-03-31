/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.tietorakenteet;

/**
 *
 * @author John Lång
 */
public class Komento {
    
    public final Komentotyyppi  KOMENTOTYYPPI;
    public final int[]       PARAMETRIT;
    
    public Komento(Komentotyyppi tyyppi, int... parametrit) {
        this.KOMENTOTYYPPI  = tyyppi;
        if (parametrit == null) {
            this.PARAMETRIT = null;
        } else {
            this.PARAMETRIT     = parametrit;
        }
    }
    
    public Komento() {
        this.KOMENTOTYYPPI  = Komentotyyppi.TYHJA;
        this.PARAMETRIT     = null;
    }

}
