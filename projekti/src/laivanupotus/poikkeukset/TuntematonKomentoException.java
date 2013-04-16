
package laivanupotus.poikkeukset;

/**
 * Pelikierros heittää tämän poikkeuksen jos se vastaanottaa (ihmis)pelaajalta 
 * tuntemattoman komennon. Tällä tarkoitetaan luokan <tt>Komento</tt> instanssia 
 * jonka kentän <tt>KOMENTOTYYPPI</tt> enumeroitu arvo on 
 * <tt>Komentotyyppi.TUNTEMATON</tt>.
 *
 * @see Komento
 * @see Komentotyyppi
 * @see Komentotulkki
 * @author John Lång
 */
public final class TuntematonKomentoException extends Exception {
    
    public TuntematonKomentoException() {
        super("Tuntematon komento.");
    }

}
