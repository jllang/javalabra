/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package laivanupotus.kontrolli;

import laivanupotus.kayttoliittymat.Tekstikayttoliittyma;
import laivanupotus.rajapinnat.Kayttoliittyma;
import laivanupotus.tyypit.Piste;
import laivanupotus.tyypit.Saannot;

/**
 *
 * @author John Lång
 */
public class Laivanupotus {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Kayttoliittyma kayttoliittyma = new Tekstikayttoliittyma();
        Saannot saannot = new Saannot(10, 10, 0);
        Pelikierros pelikierros = new Pelikierros(kayttoliittyma, saannot);
    }
}
