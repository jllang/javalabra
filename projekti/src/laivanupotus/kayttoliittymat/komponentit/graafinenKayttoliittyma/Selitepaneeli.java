
package laivanupotus.kayttoliittymat.komponentit.graafinenKayttoliittyma;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import laivanupotus.rajapinnat.Grafiikkakomponentti;

/**
 *
 * @author John Lång
 */
public class Selitepaneeli extends JPanel implements Grafiikkakomponentti{
    
    private int ruudunLeveys    = Ruutupaneeli.ruudunLeveys;
    private int ruudunKorkeus   = Ruutupaneeli.ruudunKorkeus;
    
    public Selitepaneeli() {
        JLabel otsikko = new JLabel("Selite");
        add(otsikko);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Ruutupaneeli.VARI_TYHJA_EI_OSUMAA);
        g.fillRect(16, 32, ruudunLeveys - 2, ruudunKorkeus - 2);
        g.setColor(Color.BLACK);
        g.drawString("Merta", ruudunLeveys + 18, 44);
        
        g.setColor(Ruutupaneeli.VARI_TYHJA_OSUMA);
        g.fillRect(16, ruudunKorkeus + 34, ruudunLeveys - 2, ruudunKorkeus - 2);
        g.setColor(Color.BLACK);
        g.drawString("Merta (ammuttu)", ruudunLeveys + 18, ruudunKorkeus + 46);
        
        g.setColor(Ruutupaneeli.VARI_LAIVA_EI_OSUMAA);
        g.fillRect(16, 2 * ruudunKorkeus + 34, ruudunLeveys - 2, ruudunKorkeus - 2);
        g.setColor(Color.BLACK);
        g.drawString("Laiva (ei osumaa)", ruudunLeveys + 18, 2 * ruudunKorkeus + 46);
        
        g.setColor(Ruutupaneeli.VARI_LAIVA_OSUMA);
        g.fillRect(16, 3 * ruudunKorkeus + 34, ruudunLeveys - 2, ruudunKorkeus - 2);
        g.setColor(Color.BLACK);
        g.drawString("Laiva (osuma)", ruudunLeveys + 18, 3 * ruudunKorkeus + 46);
        
        g.setColor(Ruutupaneeli.VARI_OSUI_JA_UPPOSI);
        g.fillRect(16, 4 * ruudunKorkeus + 34, ruudunLeveys - 2, ruudunKorkeus - 2);
        g.setColor(Color.BLACK);
        g.drawString("Laiva (uponnut)", ruudunLeveys + 18, 4 * ruudunKorkeus + 46);        
        
        g.setColor(Ruutupaneeli.VARI_TUNTEMATON);
        g.fillRect(16, 5 * ruudunKorkeus + 34, ruudunLeveys - 2, ruudunKorkeus - 2);
        g.setColor(Color.BLACK);
        g.drawString("Tuntematon", ruudunLeveys + 18, 5 * ruudunKorkeus + 46);

    }

    @Override
    public void tulosta() {
        repaint();
    }

}
