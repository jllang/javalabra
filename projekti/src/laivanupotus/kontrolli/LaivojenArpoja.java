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
 * @author John Lång
 */
public class LaivojenArpoja {
    
    private static final int    POHJOINEN   = 0;
    private static final int    ITA         = 1;
    private static final int    ETELA       = 2;
    private static final int    LANSI       = 3;
    private static final int    ETEENPAIN   = 1;
    private static final int    TAAKSEPAIN  = -1;
    
    private final Saannot       SAANNOT;
    private final Random        ARPOJA;
    private Pelaaja             pelaaja;
    private Pelialue            pelialue;
    private Stack<Integer>      koordinaattipino;
    
    public LaivojenArpoja(Saannot saannot, Random arpoja) {
        this.SAANNOT            = saannot;
        this.ARPOJA             = arpoja;
    }
    
    public void arvoLaivat(Pelaaja pelaaja) throws Exception {
        this.pelaaja    = pelaaja;
        this.pelialue   = pelaaja.annaPelialue();
        Map<Integer, Integer> laivojenMitatJaMaarat = SAANNOT.annaLaivojenMitatJaMaarat();
        boolean onnistuikoLisays = false;
        
        for (Integer laivanPituus : laivojenMitatJaMaarat.keySet()) {
            for (int i = 0; i < (int) laivojenMitatJaMaarat.get(laivanPituus); i++) {
                while (!onnistuikoLisays) {                    
                    onnistuikoLisays = arvoLaiva((int) laivanPituus);
                }
 
            }
        }
    }
    
    private boolean arvoLaiva(int laivanPituus) throws Exception {
        boolean lisaysOnnistui = false;
            
        //Tulevat lisättävät laivaruudut asetetaan pinoon "transaktiossa"
        //joka viedään loppuun kokonaisuudessa ennen kun jatketaan.
        while (!lisaysOnnistui) {
            koordinaattipino = new Stack<>();
            int[] sijaintiJaSuunta = arvoSijaintiJaSuunta();
                
            if (takanaOnLaiva(sijaintiJaSuunta)) {
                continue;
            }
            lisaysOnnistui = lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
//            if (!lisaysOnnistui) {
//                System.out.println("Laiva upposi satamassa!");
//            }
        }
        
        for (int j = 0; j < laivanPituus; j++) {
            try {
                pelialue.lisaaLaiva(pelaaja, koordinaattipino.pop(), koordinaattipino.pop());
            } catch (Exception poikkeus) {
                return false;
            }
        }
        return true;
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
        
        if (!ruutuunVoiLisataLaivan(sijaintiJaSuunta[0], sijaintiJaSuunta[1])) {
            return false;
        }
        
        if (vieressaOnLaiva(sijaintiJaSuunta)) {
             return false;
        }
        
        koordinaattipino.add(sijaintiJaSuunta[0]);
        koordinaattipino.add(sijaintiJaSuunta[1]);
        
        if (laivanPituus == 1) {
            //On päästy keulaan, joten tarkastetaan vielä mitä edessä näkyy:
            if (edessaOnLaiva(sijaintiJaSuunta)) {
                return false;
            }

            return true;
        }
        
        laivanPituus--; //Yksi ruutu on jo tutkittu
        return seuraavaRuutu(laivanPituus, sijaintiJaSuunta);
        
    }
    
    private boolean seuraavaRuutu(int laivanPituus, int[] sijaintiJaSuunta) {
        switch (sijaintiJaSuunta[2]) {
            default:    //pohjoinen
                sijaintiJaSuunta[1]--;
                return lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
            case ITA:     //itä
                sijaintiJaSuunta[0]++;
                return lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
            case ETELA:     //etelä
                sijaintiJaSuunta[1]++;
                return lisaaLaivaOptimistisesti(laivanPituus, sijaintiJaSuunta);
            case LANSI:     //länsi
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
    
    private boolean ruudussaOnLaiva(int x, int y) {
        //System.out.println("Tutkitaan ruutua (" + x + ", " + y + ")...");
        Ruutu ruutu;
        try {
            ruutu = pelialue.haeRuutu(pelaaja, x, y);
        } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return false;
        }
//        if (ruutu == Ruutu.LAIVA_EI_OSUMAA) {
//            System.out.println("Törmäyskurssi!");
//        }
        return ruutu == Ruutu.LAIVA_EI_OSUMAA;
    }
    
    private boolean lisaaminenOnSallittu(int[] sijaintiJaSuunta) {
        return false;
    }

    private boolean vieressaOnLaiva(int[] sijaintiJaSuunta) {
        //Tämän metodin tarkoitus on estää laivojen koskeminen toisiinsa muuten
        //kuin kulmittain.
                
        switch (sijaintiJaSuunta[2]) {
            default:    //pohjoinen ja etelä
                if (ruudussaOnLaiva(sijaintiJaSuunta[0] + 1, sijaintiJaSuunta[1])
                    || ruudussaOnLaiva(sijaintiJaSuunta[0] - 1, sijaintiJaSuunta[1])) {
                    return true;
                }
                return false;
            case ITA:     //itä (jatketaan koodin suoritusta alaspäin)
            case LANSI:     //länsi
                if (ruudussaOnLaiva(sijaintiJaSuunta[0], sijaintiJaSuunta[1] + 1)
                    || ruudussaOnLaiva(sijaintiJaSuunta[0], sijaintiJaSuunta[1] - 1)) {
                    return true;
                }
                return false;
        }
        
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
        return vastassaOnLaiva(sijaintiJaSuunta, ETEENPAIN);
    }
    
    private boolean takanaOnLaiva(int[] sijaintiJaSuunta) {
        return vastassaOnLaiva(sijaintiJaSuunta, TAAKSEPAIN);
    }
    
}
