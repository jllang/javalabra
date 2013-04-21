
package laivanupotus.tietorakenteet;

import java.util.Iterator;
import java.util.Queue;

/**
 * Mallintaa yhtä laivaa pelialueella. Laiva on joukko pisteitä ja pelialueen 
 * koordinaatiston osajoukko. Tämän luokan on tarkoitus mahdollistaa pelaajalle 
 * annettava ilmoitus "osui ja upposi" jos kaikkiin laivan pisteisiin on osuttu.
 *
 * @author John Lång
 */
final class Laiva {

    private Piste[] pisteet;
    
    Laiva(Queue<Piste> pisteet) {
        this.pisteet = new Piste[pisteet.size()];
        Iterator iteraattori = pisteet.iterator();
        for (int i = 0; i < pisteet.size(); i++) {
            this.pisteet[i] = (Piste) iteraattori.next();
        }
    }

    boolean upposi() {
        for (Piste piste : pisteet) {
            if (!piste.onAmmuttu) {
                return false;
            }
        }
        return true;
    }
    
    Piste[] annaPisteet() {
        return pisteet;
    }
    
}