
package laivanupotus.kielet;

import java.util.HashMap;
import java.util.Map;

/**
 * Tämän keskeneräisen luokan tarkoituksena on tulevaisuudessa mahdollistaa 
 * ohjelman kääntäminen eri kielille siten, että käyttöliittymä pyytää eri 
 * tilanteissa tulostettavat merkkijonot luokan instanssilta. Käyttämättömät 
 * kielet säilytetään tiedostojärjestelmässä keskusmuistin säästämiseksi.
 *
 * @author John Lång
 * @see Tallennettava
 * @see Tallentaja
 */
public final class Kieli {
    
    private static final int PELITILANNE_VUORO  = 1;
    
    protected static final Map<Integer, String> DATA = new HashMap<>();
    
    public Kieli() {
    }
    
    public String haeMerkkijono(int avain) {
        return DATA.get(avain);
    }

}
