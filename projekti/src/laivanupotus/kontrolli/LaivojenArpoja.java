/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import java.util.Map;
import laivanupotus.tietorakenteet.Pelialue;
import java.util.Random;
import java.util.Stack;
import laivanupotus.tietorakenteet.Piste;
import laivanupotus.tietorakenteet.Ruutu;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author johnny
 */
public class LaivojenArpoja {
    
    private final Saannot   SAANNOT;
    private final Random    ARPOJA;
    private Pelaaja         pelaaja;
    private Pelialue        pelialue;
    private Stack<Integer>  koordinaattipino;
    
    public LaivojenArpoja(Saannot saannot) {
        this.SAANNOT = saannot;
        this.ARPOJA = new Random();
    }
    
    public void arvoLaivat(Pelaaja pelaaja) throws Exception {
        this.pelaaja    = pelaaja;
        this.pelialue   = pelaaja.annaPelialue();
        Map<Integer, Integer> laivojenMitatJaMaarat = SAANNOT.annaLaivojenMitatJaMaarat();
        
        for (Integer laivanPituus : laivojenMitatJaMaarat.keySet()) {
            arvoLaiva((int) laivanPituus, (int) laivojenMitatJaMaarat.get(laivanPituus));
        }
    }
    
    private void arvoLaiva(int laivanPituus, int maara) throws Exception {        
        for (int i = 0; i < maara; i++) {
            boolean lisaysOnnistui = false;
            
            //Tulevat lisättävät laivaruudut asetetaan pinoon "transaktiossa"
            //joka viedään loppuun kokonaisuudessa ennen kun jatketaan.
            while (!lisaysOnnistui) {
                koordinaattipino = new Stack<>();
                int[] sijaintiJaSuunta = arvoSijaintiJaSuunta();
                
                if (laivanPituus == 1) {
                    //System.out.println("Sijoitetaan tykkivene ruutuun (" + sijaintiJaSuunta[0] + "," + sijaintiJaSuunta[1] + ")...");
                    //Yhden ruudun mittaiset "tykkiveneet" vaativat erilaista
                    //käsittelyä koska niiden kaikki kyljet pitää tarkastaa:
                    if (ruutuunVoiSijoittaaTykkiveneen(sijaintiJaSuunta)) {
                        koordinaattipino.add(sijaintiJaSuunta[0]);
                        koordinaattipino.add(sijaintiJaSuunta[1]);
                        break;
                    }
                } else {
                    //Tarkastetaan ensin ettei arvotun ruudun takana ole laivaa:
                    //System.out.println("Rakennetaan laiva...");
                    if (takanaOnLaiva(sijaintiJaSuunta)) {
                        lisaysOnnistui = false;
                    }
                    lisaysOnnistui = lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
                    if (!lisaysOnnistui) {
                        //System.out.println("Laiva upposi satamassa!");
                    }
                }
            }
            
            for (int j = 0; j < laivanPituus; j++) {
                pelialue.lisaaLaiva(pelaaja, koordinaattipino.pop(), koordinaattipino.pop());
            }
        }
    }
    
    private int[] arvoSijaintiJaSuunta() {
        // Tämän voisi myös kuvata olioilla mutta nähdäkseni se on tarpeetonta:
        // sijaintiJaSuunta[0] merkitsee laivan perän x-koordinaattia.
        // sijaintiJaSuunta[1] merkitsee laivan perän y-koordinaattia.
        // sijaintiJaSuunta[2] arvolla 0 merkitsee "pohjoista".
        // sijaintiJaSuunta[2] arvolla 1 merkitsee "itää".
        // sijaintiJaSuunta[2] arvolla 2 merkitsee "etelää".
        // sijaintiJaSuunta[2] arvolla 3 merkitsee "länttä".
        int[] sijaintiJaSuunta = new int[3];
        sijaintiJaSuunta[0] = ARPOJA.nextInt(SAANNOT.leveys());
        sijaintiJaSuunta[1] = ARPOJA.nextInt(SAANNOT.korkeus());
        sijaintiJaSuunta[2] = ARPOJA.nextInt(4);
        
        return sijaintiJaSuunta;
    }
    
    private boolean lisaaLaivaOptimistisesti(int laivanPituus, int[] sijaintiJaSuunta) {
        //Tämä metodi tarkastaa laivan mahtumisen ruutuun sekä laittaa laivan
        //tutkittuun ruutuun. Jos päädytään ruudukon ulkopuolelle, palauttaa me-
        //todi booleanin false, jolloin muutokset tulee perua.
        boolean ruutuunVoiLisataLaivan = ruutuunVoiLisataLaivan(sijaintiJaSuunta[0], sijaintiJaSuunta[1]);
        
        if (vieressaOnLaiva(sijaintiJaSuunta)) {
             return false;
        }
        
        if (!ruutuunVoiLisataLaivan) {
            return ruutuunVoiLisataLaivan;
        } else if (laivanPituus == 1) {
            //On päästy keulaan, joten tarkastetaan vielä mitä edessä näkyy:
            if (edessaOnLaiva(sijaintiJaSuunta)) {
                return false;
            }
            koordinaattipino.add(sijaintiJaSuunta[0]);
            koordinaattipino.add(sijaintiJaSuunta[1]);
            return ruutuunVoiLisataLaivan;
        }
        
        laivanPituus--; //Yksi ruutu on jo tutkittu
        koordinaattipino.add(sijaintiJaSuunta[0]);
        koordinaattipino.add(sijaintiJaSuunta[1]);
        
        switch (sijaintiJaSuunta[2]) {
            default:    //pohjoinen
                sijaintiJaSuunta[1]--;
                return lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
            case 1:     //itä
                sijaintiJaSuunta[0]++;
                return lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
            case 2:     //etelä
                sijaintiJaSuunta[1]++;
                return lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
            case 3:     //länsi
                sijaintiJaSuunta[0]--;
                return lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
        }
        
    }
    
    private boolean ruutuunVoiLisataLaivan(int x, int y) {
        Ruutu ruutu;
        try {
            ruutu = pelialue.haeRuutu(pelaaja, x, y);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return false;
        }
        return ruutu == Ruutu.TYHJA_EI_OSUMAA;
    }

    private boolean vieressaOnLaiva(int[] sijaintiJaSuunta) throws AssertionError {
        //Tämän metodin tarkoitus on estää laivojen koskeminen toisiinsa muuten
        //kuin kulmittain.
                
        switch (sijaintiJaSuunta[2]) {
            default:    //pohjoinen ja etelä
                if (ruudussaOnLaiva(sijaintiJaSuunta[0] + 1, sijaintiJaSuunta[1])
                    || ruudussaOnLaiva(sijaintiJaSuunta[0] - 1, sijaintiJaSuunta[1])) {
                    return true;
                }
                return false;
            case 1:     //itä (jatketaan koodin suoritusta alaspäin)
            case 3:     //länsi
                if (ruudussaOnLaiva(sijaintiJaSuunta[0], sijaintiJaSuunta[1] + 1)
                    || ruudussaOnLaiva(sijaintiJaSuunta[0], sijaintiJaSuunta[1] - 1)) {
                    return true;
                }
                return false;
        }
        
    }
    
    private boolean ruudussaOnLaiva(int x, int y) {
        //System.out.println("Tutkitaan ruutua (" + x + ", " + y + ")...");
        Ruutu ruutu;
        try {
            ruutu = pelialue.haeRuutu(pelaaja, x, y);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return false;
        }
        if (ruutu == Ruutu.LAIVA_EI_OSUMAA) {
            //System.out.println("Törmäyskurssi!");
        }
        return ruutu == Ruutu.LAIVA_EI_OSUMAA;
    }
    
    private boolean vastassaOnLaiva(int[] sijaintiJaSuunta, int eteenTaiTaakse) {
        // Jos siirtymä on 1, niin katsotaan keulan eteen;
        // Jos puolestaan siirtymä on -1, niin katsotaan perän taakse.
        switch (sijaintiJaSuunta[2]) {
            default:    //pohjoinen
                return ruudussaOnLaiva(sijaintiJaSuunta[0], sijaintiJaSuunta[1] - eteenTaiTaakse);
            case 1:     //itä
                return ruudussaOnLaiva(sijaintiJaSuunta[0] + eteenTaiTaakse, sijaintiJaSuunta[1]);
            case 2:     //etelä
                return ruudussaOnLaiva(sijaintiJaSuunta[0], sijaintiJaSuunta[1] + eteenTaiTaakse);
            case 3:     //länsi
                return ruudussaOnLaiva(sijaintiJaSuunta[0] - eteenTaiTaakse, sijaintiJaSuunta[1]);
        }
    }
    
    private boolean edessaOnLaiva(int[] sijaintiJaSuunta) {
        return vastassaOnLaiva(sijaintiJaSuunta, 1);
    }
    
    private boolean takanaOnLaiva(int[] sijaintiJaSuunta) {
        return vastassaOnLaiva(sijaintiJaSuunta, -1);
    }

    private boolean ruutuunVoiSijoittaaTykkiveneen(int[] sijaintiJaSuunta) {
        if (!ruudussaOnLaiva(sijaintiJaSuunta[0], sijaintiJaSuunta[1])
                && !vieressaOnLaiva(sijaintiJaSuunta)
                && !edessaOnLaiva(sijaintiJaSuunta)
                && !takanaOnLaiva(sijaintiJaSuunta)) {
            return true;
        }
        return false;
    }
    
}
