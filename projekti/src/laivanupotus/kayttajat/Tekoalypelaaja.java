
package laivanupotus.kayttajat;

import java.util.Random;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.Saannot;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;

/**
 *
 * @author John Lång
 */
public final class Tekoalypelaaja extends Pelaaja {
    
    private static Random   arpoja;
    private static int      korkeus, leveys;
    
    public Tekoalypelaaja(Random arpoja, Saannot saannot) {
        super("Tietokone");
        Tekoalypelaaja.arpoja   = arpoja;
        Tekoalypelaaja.korkeus  = saannot.korkeus();
        Tekoalypelaaja.leveys   = saannot.leveys();
    }
    
    @Override
    public Komento annaKomento(Komento odotettu) {
        switch (odotettu.KOMENTOTYYPPI) {
            default:
                // Palautetaan tyhjä komento jos pyydettiin tekoälypelaajan 
                // kannalta "tuntematonta" komentoa kuten tilakysely.
                // Tällaista ei-tuettua tekoälyominaisuutta varten pitäisi ehkä 
                // tehdä uusi poikkeus.
                return new Komento();
            case AMMU:
                return annaAmpumiskomento();
            case SIJOITA_LAIVA:
                return annaSijoituskomento(odotettu.PARAMETRIT[0]);
        }
    }
    
    private Komento annaAmpumiskomento() {
        Komento komento;
        
        int x = arpoja.nextInt(leveys);
        int y = arpoja.nextInt(korkeus);
        komento = new Komento(Komentotyyppi.AMMU, x, y);
        
        return komento;
    }
    
    /**
     * Metodi on staattinen jotta Ihmispelaaja voisi lainata sitä automaattiseen 
     * laivojen sijoitukseen.
     * 
     * @param pituus    Pelialueelle sijoitetetavan laivan pituus.
     * @return          Sijoituskomento uudelle laivalle.
     */
    static Komento annaSijoituskomento(int pituus) {
        int[] parametrit = arvoSijoitusparametrit(pituus);
        return new Komento(Komentotyyppi.SIJOITA_LAIVA, parametrit);
    }
    
    private static int[] arvoSijoitusparametrit(int pituus) {
        int[] parametrit = new int[3];
        parametrit[2] = arpoja.nextInt(2);
        switch (parametrit[2]) {
            default:
                parametrit[0] = arpoja.nextInt(leveys - pituus);
                parametrit[1] = arpoja.nextInt(korkeus);
                break;
            case 1: //Laivansijoitin.VERTIKAALINEN_ORIENTAATIO
                parametrit[0] = arpoja.nextInt(leveys);
                parametrit[1] = arpoja.nextInt(korkeus - pituus);
        }
        return parametrit;
    }
    
}
