/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testilogiikka;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import laivanupotus.tietorakenteet.Saannot;

/**
 *
 * @author johnny
 */
public class SaantojenArpoja {
    
    private final Random ARPOJA;
    
    public SaantojenArpoja(Random arpoja) {
        ARPOJA = arpoja;
    }
    
    public Saannot arvoSaannot() {
        int leveys = ARPOJA.nextInt(15) + 5, korkeus = ARPOJA.nextInt(15) + 5, vuoroja = 0;
        System.out.println("Pelikentän leveydeksi arvottiin " + leveys + ".");
        System.out.println("Pelikentän korkeudeksi arvottiin " + korkeus + ".");
        System.out.println("Pelin kestoksi arvottiin " + vuoroja + ".");
        System.out.println("Laivojen mitoiksi ja määriksi valittiin 1*3, 1*2 ja 1*1");
        return new Saannot(leveys, korkeus, vuoroja, luoVakiotLaivojenMitatJaMaarat());
    }
        
    public Map<Integer, Integer> luoVakiotLaivojenMitatJaMaarat() {
        Map<Integer, Integer> laivojenMitatJaMaarat = new TreeMap<>();
        
        laivojenMitatJaMaarat.put(3, 1);
        laivojenMitatJaMaarat.put(2, 1);
        laivojenMitatJaMaarat.put(1, 1);
        
        return laivojenMitatJaMaarat;
    }
    
    //Nopea viritelmä SaantoTestia varten:
    public int[] arvoTauluSaannoista() {
        int[] saantoTaulu = new int[7];
        
        Saannot s = arvoSaannot();
        saantoTaulu[0] = s.leveys();
        saantoTaulu[1] = s.korkeus();
        saantoTaulu[2] = s.vuoroja();
        saantoTaulu[3] = 3;
        saantoTaulu[4] = 1;
        saantoTaulu[5] = 2;
        saantoTaulu[6] = 3;
        
        return saantoTaulu;
    }
    
}