
package laivanupotus.kayttajat;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import laivanupotus.kontrolli.sijoitus.Laivansijoitin;
import laivanupotus.tietorakenteet.Komento;
import laivanupotus.tietorakenteet.enumit.Komentotyyppi;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author John Lång
 */
public final class Tekoalypelaaja extends Pelaaja {
    
    private final Random    ARPOJA;
    private Saannot         saannot;
    private int             korkeus, leveys;
    
    public Tekoalypelaaja(Random arpoja, Saannot saannot) {
        super("Tietokone");
        this.ARPOJA     = arpoja;
        this.saannot    = saannot;
        this.korkeus    = saannot.korkeus();
        this.leveys     = saannot.leveys();
    }
    
    @Override
    public Komento annaKomento(Komento odotettu) {
        switch (odotettu.KOMENTOTYYPPI) {
            default:
                return new Komento();
            case AMMU:
                return annaSatunnainenAmpumiskomento();
            case SIJOITA_LAIVA:
                return annaSatunnainenSijoituskomento(odotettu.PARAMETRIT[0]);
        }
    }
    
    private Komento annaSatunnainenAmpumiskomento() {
        Komento komento;
        
        int x = ARPOJA.nextInt(leveys);
        int y = ARPOJA.nextInt(korkeus);
        komento = new Komento(Komentotyyppi.AMMU, x, y);
        
        try {
            Thread.sleep(100);
        } catch (InterruptedException keskeytys) {
            Logger.getLogger(Tekoalypelaaja.class.getName()).log(Level.SEVERE, null, keskeytys);
        }
        
        return komento;
    }
    
    private Komento annaSatunnainenSijoituskomento(int pituus) {
        
        int[] parametrit = new int[3];
        parametrit[2] = ARPOJA.nextInt(2);
            
            switch (parametrit[2]) {
                default:
                    parametrit[0] = ARPOJA.nextInt(leveys - pituus);
                    parametrit[1] = ARPOJA.nextInt(korkeus);
                    break;
                case Laivansijoitin.VERTIKAALINEN_ORIENTAATIO:
                    parametrit[0] = ARPOJA.nextInt(leveys);
                    parametrit[1] = ARPOJA.nextInt(korkeus - pituus);
            }
        return new Komento(Komentotyyppi.SIJOITA_LAIVA, parametrit);
    }
    
}
