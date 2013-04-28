
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
    
    private static final char[] AAKKOSET = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
        'O', 'P', 'Q', 'R', 'S', 'T'
    };
    
    static final Color VARI_OLETUS          = new Color(128, 0, 0);
    static final Color VARI_LAIVA_EI_OSUMAA = new Color(160, 160, 160);
    static final Color VARI_LAIVA_OSUMA     = new Color(255, 0, 0);
    static final Color VARI_OSUI_JA_UPPOSI  = new Color(128, 0, 0);
    static final Color VARI_TYHJA_EI_OSUMAA = new Color(32, 64, 128);
    static final Color VARI_TYHJA_OSUMA     = new Color(64, 128, 255);
    static final Color VARI_TUNTEMATON      = new Color(128, 128, 128);
    
    static int                  sarakkeita, riveja, ruudunLeveys, ruudunKorkeus,
                                siirtoX, siirtoY;
    private Ruutu[][]           ruudukko;
    private Hiirenkuuntelija    hiirenkuuntelija;
    
    public Ruutupaneeli(Hiirenkuuntelija hiirenkuuntelija) {
        this.hiirenkuuntelija   = hiirenkuuntelija;
        this.addMouseListener(hiirenkuuntelija);
//        super.setBounds(0, 0, sarakkeita * ruudunLeveys, riveja * ruudunKorkeus);
//        super.setSize(sarakkeita * ruudunLeveys, riveja * ruudunKorkeus);
//        super.setBackground(Color.BLACK);
    }
    
    public void asetaRuudukko(Ruutu[][] ruudukko) {
        this.ruudukko = ruudukko;
    }
    
    public static void asetaMitat(int sarakkeita, int riveja, int ruudunLeveys, int ruudunKorkeus) {
        Ruutupaneeli.sarakkeita     = sarakkeita;
        Ruutupaneeli.riveja         = riveja;
        Ruutupaneeli.ruudunLeveys   = ruudunLeveys + 2;
        Ruutupaneeli.ruudunKorkeus  = ruudunKorkeus + 2;
        Ruutupaneeli.siirtoX        = 12;
        Ruutupaneeli.siirtoY        = 12;
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
        piirraKoordinaatit(g);
        piirraRuudut(g);
    }

    @Override
    public void tulosta() {
        repaint();
    }

    private void piirraKoordinaatit(Graphics g) {
        for (int i = 1; i <= sarakkeita; i++) {
            g.drawString(AAKKOSET[i - 1] + "", i * ruudunLeveys + 6, 12);
        }
        for (int i = 1; i <= riveja; i++) {
            g.drawString(i + "", 0, i * ruudunKorkeus + siirtoY);
        }
    }

    private void piirraRuudut(Graphics g) {
        Color c;
        for (int i = 0; i < riveja; i++) {
            for (int j = 0; j < sarakkeita; j++) {
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
                g.fillRect((j + 1) * ruudunLeveys, (i + 1) * ruudunKorkeus,
                        ruudunLeveys - 2, ruudunKorkeus - 2);
            }
        }
    }

}
