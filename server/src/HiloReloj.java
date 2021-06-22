
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
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

public void repartirVacunas(int cantVacunas, int mes) 
{
    int aRepartir = cantVacunas;
    for(Departamento d : Server.departamentos.values())
    {
       
        try {
            d.getSemNumVacunas().acquire();
            aRepartir -= (int) Math.floor(d.getDensidadPoblacional() * cantVacunas);
            int vacunasAsignadas = d.getVacunasDisponibles() + aRepartir ;        
            d.setVacunasDisponibles(vacunasAsignadas);
            d.getSemNumVacunas().release();
            
            Server.log.getSemLogVac().acquire();
                Server.log.addVacunasDepartamentoMes(d.getNombre(), mes, cantVacunas);
            Server.log.getSemLogVac().release();
            
       } catch (InterruptedException ex) {
            Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    if(aRepartir > 0)
    {
        Departamento d = Server.departamentos.get("Montevideo");
        try {
            d.getSemNumVacunas().acquire();
                d.setVacunasDisponibles(aRepartir + d.getVacunasDisponibles());
            d.getSemNumVacunas().release();
            
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

public void agendarBatch()
{
     for(String d : Server.departamentos.keySet()){
                        
                        HiloAgendar h = new HiloAgendar(Server.departamentos.get(d));
                        Thread th = new Thread(h);
                        th.setPriority(Thread.MIN_PRIORITY);
                        th.start();
                        System.out.println("Se comienza a agendar en: "+ d +": " + reloj.instant().truncatedTo(ChronoUnit.DAYS));
                        
                        
                        
                        
                    }
                    
                    


}


    
@Override
    public void run() {
     
        while(true){
            try {
                
                
                
                Thread.sleep(1000);
                System.out.println( "DIA:" + dias + " " + reloj.instant().truncatedTo(ChronoUnit.DAYS));
                dias++;
                
                Date fechaActual = Date.from(Server.r.instant());
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaActual);
                
                switch(dias)
                    {
                        case 10:
                            repartirVacunas(5,cal.get(Calendar.MONTH));
                            System.out.println("Llegaron 5 vacunas");
                            break;
                        case 30:
                            agendarBatch();
                            Server.aumentarRango();
                            break;
                        case 45:
                           repartirVacunas(10,cal.get(Calendar.MONTH));
                           System.out.println("Llegaron 10 vacunas");
                           Server.log.imprimirCSV(cal.get(Calendar.MONTH));
                           break;
                        case 50:
                            repartirVacunas(10,cal.get(Calendar.MONTH));
                            System.out.println("Llegaron 10 vacunas");
                            break;
                        case 60:
                            agendarBatch();
                            Server.aumentarRango();
                            
                            break;
                        case 70:
                            Server.log.imprimirCSV(cal.get(Calendar.MONTH));
                        case 90:
                            agendarBatch();
                            Server.aumentarRango();
                            break;
                        case 95:
                            Server.log.imprimirCSV(cal.get(Calendar.MONTH));
                            break;
                        default:
                            break;
                         
                    
                    }
               
                
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
    
    
}
