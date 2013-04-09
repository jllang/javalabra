
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
public final class Piste {
    
    /**
     * Tosi jos ja vain jos pisteessä on laiva.
     */
    public boolean onOsaLaivaa;
    
    /**
     * Tosi jos ja vain jos pisteeseen on ammuttu.
     */
    public boolean onAmmuttu;
    
    /**
     * Konstruktori joka luo aina tyhjän ruudun.
     */
    public Piste() {
        onOsaLaivaa = false;
        onAmmuttu   = false;
    }
    
}
