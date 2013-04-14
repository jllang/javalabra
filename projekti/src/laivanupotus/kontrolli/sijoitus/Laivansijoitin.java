
package laivanupotus.kontrolli.sijoitus;

import laivanupotus.kayttajat.Pelaaja;
import laivanupotus.poikkeukset.SaantojenvastainenSijoitusException;
import laivanupotus.tietorakenteet.Pelialue;
import laivanupotus.tietorakenteet.enumit.Ruutu;

/**
 * Vastaa laivan sijoittamisesta pelialueelle sääntöjen mukaisesti.
 *
 * @author John Lång
 * @see Saannot
 * @see Pelaaja
 * @see Pelialue
 */
public final class Laivansijoitin {
    
    public static final int              HORISONTAALINEN_ORIENTAATIO = 0;
    public static final int              VERTIKAALINEN_ORIENTAATIO   = 1;
    
    private Pelaaja     pelaaja;
    private Pelialue    pelialue;
    
    public Laivansijoitin() {}
    
    /**
     * Valmistelee olion sisäiset kentät laivan sijoittamista varten.
     * @param laivanOmistaja Pelaaja jonka laivaa ollaan sijoittamassa.
     */
    public void asetaPelaaja(Pelaaja laivanOmistaja) {
        this.pelaaja = laivanOmistaja;
        this.pelialue = laivanOmistaja.annaPelialue();
    }
    
    /**
     * Sijoittaa laivan parametrien mukaisesti. Sijoitus tapahtuu lisäämällä 
     * pelialueen ruutuihin laivan lähtöruudusta laskien pituuden ilmoittaman 
     * ruutumäärän joko oikealle tai alaspäin.
     * 
     * @param x             Lähtöruudun x-koordinaatti ruudukossa.
     * @param y             Lähtöruudun y-koordinaatti ruudukossa.
     * @param orientaatio   Sallitut arvot ovat 0 ja 1 (horisontaalinen tai 
     * vertikaalinen).
     * @param pituus        Laivan pituus.
     * 
     * @throws Exception    Mahdollinen virheellisten parametrien tai sääntöjen 
     * vastaisen sijoittamisyrityksen aiheuttama poikkeus.
     */
    public void sijoitaLaiva(int x, int y, int orientaatio,
            int pituus) throws Exception {
//        System.err.println("Sijoitettavan laivan koordinaatit olivat ("
//                + x + "," + y + ").");
        tarkastaOrientaatio(orientaatio);
        if (!laivalleOnTilaa(x, y, orientaatio, pituus)) {
            throw new SaantojenvastainenSijoitusException("Sääntörikkomus: "
                    + "Laiva yritettiin sijoittaa toisen laivan päälle tai "
                    + "pelialueen ulkopuolelle.");
        }
        if (!laivanYmparistoOnTyhja(x, y, orientaatio, pituus)) {
            throw new SaantojenvastainenSijoitusException("Sääntörikkomus: "
                    + "Laiva yritettiin sijoittaa kiinni toisen laivan kylkeen.");
        }
        switch (orientaatio) {
            default:
                for (int i = 0; i < pituus; i++) {
                    pelialue.lisaaLaiva(pelaaja, x + i, y);
                }
                return;
            case VERTIKAALINEN_ORIENTAATIO:
                for (int i = 0; i < pituus; i++) {
                    pelialue.lisaaLaiva(pelaaja, x, y + i);
                }
        }
        
    }
    
    private void tarkastaOrientaatio(int orientaatio)
            throws IllegalArgumentException {
        if(orientaatio < 0 || orientaatio > 1) {
            throw new IllegalArgumentException("Orientaation sallitut arvot "
                    + "ovat 0 (oikealle) tai 1 (alas).");
        }
    }
    
    private boolean laivalleOnTilaa(int x, int y, int orientaatio,
            int pituus) {
        switch (orientaatio) {
            default:
                for (int i = 0; i < pituus; i++) {
                    if (!ruutuOnIdenttinen(x + i, y, Ruutu.TYHJA_EI_OSUMAA)) {
                        return false;
                    }
                }
                break;
            case VERTIKAALINEN_ORIENTAATIO:
                for (int i = 0; i < pituus; i++) {
                    if (!ruutuOnIdenttinen(x, y + i, Ruutu.TYHJA_EI_OSUMAA)) {
                        return false;
                    }
                }
        }
        return true;
    }
    
    private boolean laivanYmparistoOnTyhja(int x, int y, int orientaatio,
            int pituus) {
        switch (orientaatio) {
            default:
                if (ruutuOnIdenttinen(x - 1, y, Ruutu.LAIVA_EI_OSUMAA)) {
                    return false;
                }
                for (int i = 0; i < pituus; i++) {
                    if (ruutuOnIdenttinen(x + i, y - 1, Ruutu.LAIVA_EI_OSUMAA)
                            || ruutuOnIdenttinen(x + i, y + 1, Ruutu.LAIVA_EI_OSUMAA)) {
                        return false;
                    }
                }
                if (ruutuOnIdenttinen(x + pituus + 1, y, Ruutu.LAIVA_EI_OSUMAA)) {
                    return false;
                }
                break;
            case VERTIKAALINEN_ORIENTAATIO:
                if (ruutuOnIdenttinen(x, y - 1, Ruutu.LAIVA_EI_OSUMAA)) {
                    return false;
                }
                for (int i = 0; i < pituus; i++) {
                    if (ruutuOnIdenttinen(x - 1, y + i, Ruutu.LAIVA_EI_OSUMAA)
                            || ruutuOnIdenttinen(x + 1, y + i, Ruutu.LAIVA_EI_OSUMAA)) {
                        return false;
                    }
                }
                if (ruutuOnIdenttinen(x, y + pituus + 1, Ruutu.LAIVA_EI_OSUMAA)) {
                    return false;
                }
        }
        return true;
    }
    
    private boolean ruutuOnIdenttinen(int x, int y, Ruutu vertailtava) {
        Ruutu ruutu = null;
        try {
            ruutu = pelialue.haeRuutu(pelaaja, x, y);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // Jatketaan normaalisti
        }
        return ruutu == vertailtava;
    }
    
}
