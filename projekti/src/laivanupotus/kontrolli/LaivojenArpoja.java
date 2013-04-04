package laivanupotus.kontrolli;

import laivanupotus.kayttajat.Pelaaja;
import java.util.Map;
import laivanupotus.tietorakenteet.Pelialue;
import java.util.Random;
import java.util.Stack;
import laivanupotus.tietorakenteet.Piste;
import laivanupotus.tietorakenteet.enumit.Ruutu;
import laivanupotus.tietorakenteet.Saannot;
import laivanupotus.tietorakenteet.enumit.Orientaatio;

/**
*
* @author John Lång
*/
public class LaivojenArpoja {
    
    private final Saannot   SAANNOT;
    private final int       LEVEYS, KORKEUS;
    private final Random    ARPOJA;
    
    private Pelaaja         pelaaja;
    private Pelialue        pelialue;
    
    public LaivojenArpoja(Saannot saannot, Random arpoja) {
        this.SAANNOT    = saannot;
        this.LEVEYS     = SAANNOT.leveys();
        this.KORKEUS    = SAANNOT.korkeus();
        this.ARPOJA     = arpoja;
    }
    
    public void sijoitaLaivasto(Pelaaja pelaaja) throws Exception {
        this.pelaaja    = pelaaja;
        this.pelialue   = pelaaja.annaPelialue();
        Map<Integer, Integer> laivojenMitatJaMaarat;
        laivojenMitatJaMaarat = SAANNOT.annaLaivojenMitatJaMaarat();
        
        for (Integer laivanPituus : laivojenMitatJaMaarat.keySet()) {
            arvoAlusluokanLaivat((int) laivanPituus, (int) laivojenMitatJaMaarat.get(laivanPituus));
        }
    }
    
    private void arvoAlusluokanLaivat(int luokka, int maara) throws Exception {        
        for (int i = 0; i < maara; i++) {
            arvoLaiva(luokka);
        }
    }
    
    private void arvoLaiva(int pituus) throws Exception {
        boolean laivanVoiLisata = false;
        int x = 0, y = 0;
        Orientaatio orientaatio = Orientaatio.HORISONTAALINEN;
        
        while (!laivanVoiLisata) {
            // x ja y -koordinaatit voisi arpoa paremminkin mutta menköön näin
            orientaatio = arvoOrientaatio();
            
            switch (orientaatio) {
                default:
                    x = ARPOJA.nextInt(LEVEYS - pituus);
                    y = ARPOJA.nextInt(KORKEUS);
                    break;
                case VERTIKAALINEN:
                    x = ARPOJA.nextInt(LEVEYS);
                    y = ARPOJA.nextInt(KORKEUS - pituus);
            }
            
            if (laivalleOnTilaa(x, y, orientaatio, pituus)
                    && laivanYmparistoOnTyhja(x, y, orientaatio, pituus)) {
                laivanVoiLisata = true;
            }
        }
        sijoitaLaiva(x, y, orientaatio, pituus);
    }
    
    private Orientaatio arvoOrientaatio() {
        int suuntanumero = ARPOJA.nextInt(2);
        if (suuntanumero == 0) {
            return Orientaatio.HORISONTAALINEN;
        }
        return Orientaatio.VERTIKAALINEN;
    }
    
    private boolean laivalleOnTilaa(int x, int y, Orientaatio orientaatio,
            int pituus) {
        switch (orientaatio) {
            default:
                for (int i = 0; i < pituus; i++) {
                    if (!ruutuOnIdenttinen(x + i, y, Ruutu.TYHJA_EI_OSUMAA)) {
                        return false;
                    }
                }
                break;
            case VERTIKAALINEN:
                for (int i = 0; i < pituus; i++) {
                    if (!ruutuOnIdenttinen(x, y + i, Ruutu.TYHJA_EI_OSUMAA)) {
                        return false;
                    }
                }
        }
        return true;
    }
    
    private boolean laivanYmparistoOnTyhja(int x, int y, Orientaatio orientaatio,
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
                if (ruutuOnIdenttinen(x + 1, y, Ruutu.LAIVA_EI_OSUMAA)) {
                    return false;
                }
                break;
            case VERTIKAALINEN:
                if (ruutuOnIdenttinen(x, y - 1, Ruutu.LAIVA_EI_OSUMAA)) {
                    return false;
                }
                for (int i = 0; i < pituus; i++) {
                    if (ruutuOnIdenttinen(x - 1, y + i, Ruutu.LAIVA_EI_OSUMAA)
                            || ruutuOnIdenttinen(x + 1, y + i, Ruutu.LAIVA_EI_OSUMAA)) {
                        return false;
                    }
                }
                if (ruutuOnIdenttinen(x, y + 1, Ruutu.LAIVA_EI_OSUMAA)) {
                    return false;
                }
        }
        return true;
    }
    
    private boolean ruutuOnIdenttinen(int x, int y, Ruutu vertailtava) {
        Ruutu ruutu;
        try {
            ruutu = pelialue.haeRuutu(pelaaja, x, y);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return false;
        }
        return ruutu == vertailtava;
    }
    
    private void sijoitaLaiva(int x, int y, Orientaatio orientaatio,
            int pituus) throws Exception {
        switch (orientaatio) {
            default:
                for (int i = 0; i < pituus; i++) {
                    pelialue.lisaaLaiva(pelaaja, x + i, y);
                }
                return;
            case VERTIKAALINEN:
                for (int i = 0; i < pituus; i++) {
                    pelialue.lisaaLaiva(pelaaja, x, y + i);
                }
        }
        
    }
    

}