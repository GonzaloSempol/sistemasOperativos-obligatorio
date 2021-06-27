
import java.util.PriorityQueue;
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
public class HiloAgendar implements Runnable{

    String dep;
   

    public HiloAgendar(String departamento) {
        this.dep = departamento;
    }
    
    @Override
    public void run() {
       
        
        try {
            
            
            
            Server.departamentos.get(dep).getSemNumVacunas().acquire();
            Departamento departamento = Server.departamentos.get(dep); 
            
                  
            
            while(departamento.getVacunasDisponibles() > 1) {
                
                departamento.getSemPersonasxDepartamento().acquire();
                
                if (!Server.paraAgendar.get(departamento.getNombre()).isEmpty())
                {                  
                     Persona p = (Persona)Server.paraAgendar.get(departamento.getNombre()).remove();
                     
                     
                     departamento.getSemPersonasxDepartamento().release();
                     departamento.agendar(p);               
                     //System.out.println("Antes: DEp:" + departamento.getNombre() + ":" + departamento.getVacunasDisponibles());
                     departamento.substractVacunas();
                     //System.out.println("Despues: DEp:" + departamento.getNombre() + ":" + departamento.getVacunasDisponibles());
                     
                }else{
                    departamento.getSemPersonasxDepartamento().release();
                    break;
                }
                
                  
             
            }
            
            departamento.getSemNumVacunas().release();
            
            
        
        
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloAgendar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
   
    
}
