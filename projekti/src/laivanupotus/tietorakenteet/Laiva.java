
package laivanupotus.tietorakenteet;

import java.util.List;

/**
 * Mallintaa yhtä laivaa pelialueella. Laiva on joukko pisteitä ja pelialueen 
 * koordinaatiston osajoukko. Tämän luokan on tarkoitus mahdollistaa pelaajalle 
 * annettava ilmoitus "osui ja upposi" jos kaikkiin laivan pisteisiin on osuttu.
 *
 * @author John Lång
 */
final class Laiva {

    private final List<Piste>   PISTEET;
    private final List<Integer> KOORDINAATIT;
    
    Laiva(List<Piste> pisteet, List<Integer> koordinaatit) {
        this.PISTEET = pisteet;
        this.KOORDINAATIT = koordinaatit;
    }

    boolean upposi() {
        for (Piste piste : PISTEET) {
            if (!piste.onAmmuttu) {
                return false;
            }
        }
        return true;
    }
    
    List<Piste> annaPisteet() {
        return PISTEET;
    }
    
    List<Integer> annaKoordinaatit() {
        return KOORDINAATIT;
    }
    
}