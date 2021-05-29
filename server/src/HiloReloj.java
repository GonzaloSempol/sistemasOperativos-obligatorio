
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gonzalo.sempol
 */
public class HiloReloj implements Runnable {
private int cantDiasAgenda=30;
private int dias=0;
private Reloj reloj;

    public HiloReloj(Reloj reloj) {
        this.reloj = reloj;
    }


    
@Override
    public void run() {
        int i=0;
        while(true){
            try {
                Thread.sleep(1000);
                System.out.println(reloj.instant().truncatedTo(ChronoUnit.DAYS));
                dias++;
                if(dias==cantDiasAgenda){
                    dias=0;
                    
                    HiloAgendar h = new HiloAgendar(Server.departamentos.get("Montevideo"));
                    Thread th = new Thread(h);
                    th.start();
                    System.out.println("Se comienza a agendar: " + reloj.instant().truncatedTo(ChronoUnit.DAYS));
                    
                }
                
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
    
    
}
