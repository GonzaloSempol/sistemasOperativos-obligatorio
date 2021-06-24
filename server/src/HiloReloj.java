
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
private int dias=1;
private Reloj reloj;

    public HiloReloj(Reloj reloj) {
        this.reloj = reloj;
    }

public void repartirVacunas(int cantVacunas, int mes) 
{
    
            HiloRepartirVacunas h = new HiloRepartirVacunas(cantVacunas, mes);
            Thread th = new Thread(h);
            th.setPriority(Thread.MAX_PRIORITY-1);
            th.start();
            System.out.println("Se comienza a repartir vacunas a los departamentos");
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
                int vacunas=0;
                
                switch(cal.get(Calendar.MONTH) +1)
                {
                    //Febrero
                    case 2:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 26:
                                vacunas=192000;
                                repartirVacunas(vacunas,cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                agendarBatch();
                                System.out.println("Agendando...");
                                break;
                        }
                        break;
                    //Marzo
                    case 3:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            case 10:
                                vacunas=50000;
                                repartirVacunas(vacunas,cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                agendarBatch();
                                System.out.println("Agendando...");
                                break;
                             case 16:
                                vacunas=1558000;
                                repartirVacunas(vacunas,cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                agendarBatch();
                                System.out.println("Agendando...");
                                break;
                             case 17:
                                vacunas=53310;
                                repartirVacunas(vacunas,cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                agendarBatch();
                                System.out.println("Agendando...");
                                break;
                             case 25:
                                vacunas=50000;
                                repartirVacunas(vacunas,cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                agendarBatch();
                                System.out.println("Agendando...");
                                break;
                        }
                        break;
                    case 4:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            case 4:
                                vacunas=48000;
                                repartirVacunas(vacunas,cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                        }
                        break;
                    case 5:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            
                        }
                        break;
                    case 6:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            
                        }
                        break;
                    case 7:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            
                        }
                        break;
                    case 8:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            
                        }
                        break;
                    case 9:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            
                        }
                        break;
                    case 10:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            
                        }
                        break;
                    case 11:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            
                        }
                        break;
                    case 12:
                        switch(cal.get(Calendar.DAY_OF_MONTH))
                        {
                            case 1:
                                Server.aumentarRango();
                                Server.log.imprimirCSV(cal.get(Calendar.MONTH) -1);
                                break;
                            
                        }
                        break;
                
                }
                
               
                
                
                
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
    
    
    
}
