
package laivanupotus.kontrolli;

/**
 * Mittaa ajan kulumista.
 *
 * @author John Lång
 */
public class Kello implements Runnable {
    
    private long alkuhetki;
    
    public Kello() {}
    
    public synchronized void tyhjenna() {
        alkuhetki       = System.currentTimeMillis();
    }

    public synchronized long aika() {
        return System.currentTimeMillis() - alkuhetki;
    }

    @Override
    public void run() {
    }
    
}
