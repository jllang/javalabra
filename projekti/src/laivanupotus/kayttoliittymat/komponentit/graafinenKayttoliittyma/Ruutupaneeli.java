
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
    
    private Ruutu[][]   ruudukko;
    private int         x, y;
    
    public Ruutupaneeli(Ruutu[][] ruudukko, int x,int y) {
        this.ruudukko = ruudukko;
        this.x = x;
        this.y = y;
//        super.setBounds(x, y, ruudukko[0].length * 16 + 2, ruudukko.length * 16 + 2); // Miksei toimi???
//        super.setBackground(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color c;
        for (int i = 0; i < ruudukko.length - 1; i++) {
            for (int j = 0; j < ruudukko[j].length - 1; j++) {
                switch (ruudukko[i][j]) {
                    case LAIVA_EI_OSUMAA:
                        c = new Color(160, 160, 160);
                        break;
                    case LAIVA_OSUMA:
                        c = new Color(255, 0, 0);
                        break;
                    default:
                        c = new Color(24, 32, 128);
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
