
package laivanupotus.tietorakenteet.enumit;

/**
 * Esittää tiedon koordinaatiston pisteen tilan (, lisäyksellä että se voi olla 
 * tuntematon,) tulkinnasta käyttöliittymää varten.
 *
 * @author John Lång
 * @see Piste
 * @see Pelialue
 */
public enum Ruutu {
    
    /**
     * Jos piste sijaitsee katsojan pelialueella, siinä ei ole laivaa eikä 
     * siihen ole ammuttu.
     */
    TYHJA_EI_OSUMAA,
    
    /**
     * Jos piste sijaitsee katsojan pelialueella, siinä on laiva eikä siihen 
     * ole ammuttu.
     */
    LAIVA_EI_OSUMAA,
    
    /**
     * Jos pisteessä ei ole laivaa ja siihen on ammuttu.
     */
    TYHJA_OSUMA,
    
    /**
     * Jos pisteessä on laiva ja siihen on ammuttu.
     */
    LAIVA_OSUMA,
    
    /**
     * Jos pisteessä on laiva ja siihen on ammuttu sekä laiva on uponnut.
     */
    LAIVA_UPONNUT,
    
    /**
     * Jos piste ei sijaitse katsojan pelialueella eikä siihen ole ammuttu.
     */
    TUNTEMATON;
    
}
