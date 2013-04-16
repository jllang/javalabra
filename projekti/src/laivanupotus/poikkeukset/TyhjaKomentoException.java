
package laivanupotus.poikkeukset;

/**
 * Pelikierros heittää tämän poikkeuksen jos se vastaanottaa pelaajalta tyhjän 
 * komennon. Tällä tarkoitetaan luokan <tt>Komento</tt> instanssia jonka kentän 
 * <tt>KOMENTOTYYPPI</tt> enumeroitu arvo on <tt>Komentotyyppi.TYHJA</tt>.
 * 
 * @see Komento
 * @see Komentotyyppi
 *
 * @author John Lång
 */
public final class TyhjaKomentoException extends Exception {
    
    public TyhjaKomentoException() {
        super("Mitään komentoa ei annettu");
    }

}
