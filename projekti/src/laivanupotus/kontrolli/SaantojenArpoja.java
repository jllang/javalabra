
package laivanupotus.kontrolli;

import java.util.Random;
import java.util.TreeMap;
import laivanupotus.tietorakenteet.Saannot;

/**
 * Tämä luokka on lähinnä tarkoitettu apuvälineeksi luokan <tt>Saannot</tt> 
 * automaattiseen yksikkötestaukseen.
 *
 * @author John Lång
 */
public final class SaantojenArpoja {
    
    private final Random ARPOJA;
    
    public SaantojenArpoja(Random arpoja) {
        ARPOJA = arpoja;
    }
    
    public Saannot arvoSaannot() {
        int leveys = ARPOJA.nextInt(13) + 7, korkeus = ARPOJA.nextInt(13) + 7, vuoroja = 0;
        System.out.println("Pelikentän leveydeksi arvottiin " + leveys + ".");
        System.out.println("Pelikentän korkeudeksi arvottiin " + korkeus + ".");
        System.out.println("Pelin kestoksi arvottiin " + vuoroja + ".");
        System.out.println("Laivojen mitoiksi ja määriksi valittiin 1*3, 1*2 ja 1*1");
        return new Saannot(leveys, korkeus, vuoroja, luoVakiotLaivojenMitatJaMaarat(), true);
    }
        
    public TreeMap<Integer, Integer> luoVakiotLaivojenMitatJaMaarat() {
        TreeMap<Integer, Integer> laivojenMitatJaMaarat = new TreeMap<>();
        
        laivojenMitatJaMaarat.put(3, 1);
        laivojenMitatJaMaarat.put(2, 1);
        laivojenMitatJaMaarat.put(1, 1);
        
        return laivojenMitatJaMaarat;
    }
    
    //Nopea viritelmä SaantoTestia varten:
    public int[] arvoTauluSaannoista() {
        int[] saantoTaulu = new int[8];
        
        Saannot s = arvoSaannot();
        saantoTaulu[0] = s.leveys();
        saantoTaulu[1] = s.korkeus();
        saantoTaulu[2] = s.vuoroja();
        saantoTaulu[3] = 3;
        saantoTaulu[4] = 1;
        saantoTaulu[5] = 2;
        saantoTaulu[6] = 3;
        saantoTaulu[7] = 1;
        
        return saantoTaulu;
    }
    
}