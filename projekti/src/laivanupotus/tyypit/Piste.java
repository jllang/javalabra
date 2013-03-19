/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.tyypit;

/**
 *
 * @author John Lång
 */
public class Piste {
    
    //private boolean nakyvissa;
    public boolean osaLaivaa;  // Tosi jos ruutu on osa jotain laivaa.
    public boolean osuma;      // Tosi jos ruutuun on ammuttu.
    /* Muuttujat ovat toisistaan riippumattomia, joten "tyhjässä" ruudussa voi
     * olla "osuma".
     */
    
    public Piste() {
        osaLaivaa   = false;
        osuma       = false;
    }
    
}
