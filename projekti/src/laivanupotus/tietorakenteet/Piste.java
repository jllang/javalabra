
package laivanupotus.tietorakenteet;

/**
 * Mallintaa yhtä pistettä koordinaatistossa. Pisteellä on tasan neljä 
 * mahdollista erilaista aina tunnettua diskreettiä tilaa (, joten se voitaisiin
 * myös mallintaa enumina,) jotka on tallennettu kahten julkiseen kenttään. 
 * Suorituskyvyn parantamiseksi ja tarpeettoman monimutkaisuuden välttämiseksi 
 * olen katsonut parhaaksi määrittää nämä kentät julkisiksi.
 *
 * @author John Lång
 * @see Ruutu
 * @see Pelialue
 */
final class Piste {
    
    /**
     * Tosi jos ja vain jos pisteessä on laiva.
     */
    boolean onOsaLaivaa;
    
    /**
     * Tosi jos ja vain jos pisteeseen on ammuttu.
     */
    boolean onAmmuttu;
    
    /**
     * Konstruktori joka luo aina tyhjän ruudun.
     */
    Piste() {
        onOsaLaivaa = false;
        onAmmuttu   = false;
    }
    
}
