
package laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import laivanupotus.rajapinnat.Grafiikkakomponentti;
import laivanupotus.tietorakenteet.enumit.Ruutu;

/**
 *
 * @author John Lång
 */
public final class Ruutupaneeli extends JPanel implements Grafiikkakomponentti {
    
    private static final Color VARI_OLETUS          = new Color(128, 0, 0);
    private static final Color VARI_LAIVA_EI_OSUMAA = new Color(160, 160, 160);
    private static final Color VARI_LAIVA_OSUMA     = new Color(255, 0, 0);
    private static final Color VARI_OSUI_JA_UPPOSI  = new Color(128, 0, 0);
    private static final Color VARI_TYHJA_EI_OSUMAA = new Color(32, 64, 128);
    private static final Color VARI_TYHJA_OSUMA     = new Color(64, 128, 255);
    private static final Color VARI_TUNTEMATON      = new Color(128, 128, 128);
    
    static int          sarakkeita, riveja, ruudunLeveys, ruudunKorkeus;
    int                 siirtoX, siirtoY;
    private Ruutu[][]   ruudukko;
    
    public Ruutupaneeli(Ruutu[][] ruudukko, int siirtoX, int siirtoY) {
        this.ruudukko       = ruudukko;
        this.siirtoX        = siirtoX;
        this.siirtoY        = siirtoY;        
        super.setBounds(siirtoX, siirtoY, sarakkeita * ruudunLeveys, riveja * ruudunKorkeus);
        super.setSize(sarakkeita * ruudunLeveys, riveja * ruudunKorkeus);
//        super.setBackground(Color.BLACK);
    }
    
    public static void asetaMitat(int sarakkeita, int riveja, int ruudunLeveys, int ruudunKorkeus) {
        Ruutupaneeli.sarakkeita     = sarakkeita;
        Ruutupaneeli.riveja         = riveja;
        Ruutupaneeli.ruudunLeveys   = ruudunLeveys + 2;
        Ruutupaneeli.ruudunKorkeus  = ruudunKorkeus + 2;
    }
    
    public void tulostaRuudut() {
        // Debuggaukseen
        for (Ruutu[] ruutus : ruudukko) {
            for (Ruutu ruutu : ruutus) {
                switch (ruutu) {
                    case LAIVA_EI_OSUMAA:
                        System.out.print("L ");
                        break;
                    case LAIVA_OSUMA:
                        System.out.print("X ");
                        break;
                    case TYHJA_EI_OSUMAA:
                        System.out.print("~ ");
                        break;
                    case TYHJA_OSUMA:
                        System.out.print("O ");
                        break;
                    default:
                        System.out.print("? ");
                        break;
                }
            }
            System.out.println();
        }
    }
    
    public Ruutu[][] annaRuudut() {
        return ruudukko;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c;
        Ruutu r;
        for (int i = 0; i < riveja; i++) {
            for (int j = 0; j < sarakkeita; j++) {
//                r = ruudukko[i][j];
//                System.out.println(r);
                switch (ruudukko[i][j]) {
                    case LAIVA_EI_OSUMAA:
                        c = VARI_LAIVA_EI_OSUMAA;
                        break;
                    case LAIVA_OSUMA:
                        c = VARI_LAIVA_OSUMA;
                        break;
                    case LAIVA_UPONNUT:
                        c = VARI_OSUI_JA_UPPOSI;
                        break;
                    case TYHJA_EI_OSUMAA:
                        c = VARI_TYHJA_EI_OSUMAA;
                        break;
                    case TYHJA_OSUMA:
                        c = VARI_TYHJA_OSUMA;
                        break;
                    case TUNTEMATON:
                        c = VARI_TUNTEMATON;
                        break;
                    default:
                        c = VARI_OLETUS;
                        break;
                }
                g.setColor(c);
                g.fillRect(j * ruudunLeveys + siirtoX,
                        i * ruudunKorkeus + siirtoY,
                        ruudunLeveys - 2, ruudunKorkeus - 2);
            }
        }
    }

    @Override
    public void tulosta() {
        repaint();
    }

}
