
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
                    
                    for(String d : Server.departamentos.keySet()){
                        
                        HiloAgendar h = new HiloAgendar(Server.departamentos.get(d));
                        Thread th = new Thread(h);
                        th.start();
                        System.out.println("Se comienza a agendar en: "+ d +": " + reloj.instant().truncatedTo(ChronoUnit.DAYS));
                        
                        
                        
                        
                    }
                    
                    if(i < Server.rangos.length -1 ){
                            i++;
                            Server.rangoActual=Server.rangos[i];
                            System.out.println("El rango habilitado es mayores de: "+ Server.rangoActual + " años.");
                    }
                    
                    
                }
                
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
    
    
}
