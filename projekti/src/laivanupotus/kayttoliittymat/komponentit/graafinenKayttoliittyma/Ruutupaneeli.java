
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
//    private static final Color VARI_OLETUS          = new Color(24, 32, 128);
    private static final Color VARI_LAIVA_EI_OSUMAA = new Color(160, 160, 160);
//    private static final Color VARI_LAIVA_EI_OSUMAA = new Color(0, 255, 0);
    private static final Color VARI_LAIVA_OSUMA     = new Color(255, 0, 0);
    private static final Color VARI_MERI            = new Color(32, 64, 128);
    private static final Color VARI_TUNTEMATON      = new Color(128, 128, 128);
    
    private Ruutu[][]   ruudukko;
    private int         x, y;
    
    public Ruutupaneeli(Ruutu[][] ruudukko, int x,int y) {
        this.ruudukko = ruudukko;
        this.x = x;
        this.y = y;
        super.setBounds(x, y, ruudukko[0].length * 16 + 2, ruudukko.length * 16 + 2);
//        super.setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c;
        Ruutu r;
        for (int i = 0; i < ruudukko.length - 1; i++) {
            for (int j = 0; j < ruudukko[0].length - 1; j++) {
//                r = ruudukko[i][j];
//                System.out.println(r);
                switch (ruudukko[i][j]) {
                    case LAIVA_EI_OSUMAA:
                        c = VARI_LAIVA_EI_OSUMAA;
                        break;
                    case LAIVA_OSUMA:
                        c = VARI_LAIVA_OSUMA;
                        break;
                    case TYHJA_EI_OSUMAA:
                    case TYHJA_OSUMA:
                        c = VARI_MERI;
                        break;
                    case TUNTEMATON:
                        c = VARI_TUNTEMATON;
                        break;
                    default:
                        c = VARI_OLETUS;
                        break;
                }
                g.setColor(c);
                g.fillRect(j * 18 + x + 1, i * 18 + y + 1, 16, 16);
            }
        }
    }

    @Override
    public void tulosta() {
        repaint();
    }

}
