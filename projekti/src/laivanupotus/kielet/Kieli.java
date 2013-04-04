/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package laivanupotus.kielet;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author John Lång
 */
public abstract class Kieli {
    
    private static final int PELITILANNE_VUORO  = 1;
    
    protected static final Map<Integer, String> DATA = new HashMap<>();
    
    public Kieli() {
    }
    
    public String haeMerkkijono(int avain) {
        return DATA.get(avain);
    }

}
