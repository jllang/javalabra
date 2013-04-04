/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.tietorakenteet;

import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 *
 * @author John Lång
 */
public class Komento {
    
    // Maagisia lukuja liittyen enumiin Komentotyyppi.TILAKYSELY:
    public static final int 
            TILAKYSELY_MONESKO_VUORO                = 1,
            TILAKYSELY_VUOROJA_JALJELLA             = 2,
            TILAKYSELY_OMIA_LAIVOJA_JALJELLA        = 3, 
            TILAKYSELY_VASTUSTAJAN_LAIVOJA_JALJELLA = 4
            ;
    
    public final Komentotyyppi  KOMENTOTYYPPI;
    public final int[]          PARAMETRIT;
    
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
