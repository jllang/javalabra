
package laivanupotus.tietorakenteet;

import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 * Ohjelman sisäisessä kontrollissa käytettävä komento-olio, jonka tehtävänä on 
 * pitää sisällään tieto luokan Pelaaja implementaation valitsemasta seuraavaksi
 * suoritettavasta toimenpiteestä ja siihen mahdollisesti liittyvistä 
 * parametreista.
 *
 * @author John Lång
 */
public final class Komento {
    
    // Maagisia lukuja liittyen enumiin Komentotyyppi.TILAKYSELY:
    public static final int
            TILAKYSELY_VUOROT = 1,
            TILAKYSELY_LAIVAT = 2;
    
    /**
     * Komennon enumeroitu tyyppi.
     * @see Komentotyyppi
     */
    public final Komentotyyppi  KOMENTOTYYPPI;
    
    /**
     * Komentoon mahdollisesti liittyvät kokonaislukuparametrit.
     */
    public final int[]          PARAMETRIT;
    
    /**
     * Luokan Komento konstruktori. Luo uuden Komento-olion annetuilla 
     * parametreilla.
     * 
     * @see Komentotyyppi#KOMENTOTYYPPI
     * @see Komentotyyppi#PARAMETRIT
     */
    public Komento(Komentotyyppi tyyppi, int... parametrit) {
        this.KOMENTOTYYPPI  = tyyppi;
        if (parametrit == null) {
            this.PARAMETRIT = null;
        } else {
            this.PARAMETRIT = parametrit;
        }
    }
    
    /**
     * Luo oletusarvoisen (tyhjän) komennon eli komennon, jonka kentän 
     * <tt>KOMENTOTYYPPI</tt> enumeroitu arvo on <tt>Komentotyyppi.TYHJA</tt> ja 
     * kentän <tt>PARAMETRIT</tt> arvo on <tt>null</tt>.
     */
    public Komento() {
        this.KOMENTOTYYPPI  = Komentotyyppi.TYHJA;
        this.PARAMETRIT     = null;
    }

}
