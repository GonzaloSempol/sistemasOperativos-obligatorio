
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

    Departamento departamento;

    public HiloAgendar(Departamento departamento) {
        this.departamento = departamento;
    }
    
    @Override
    public void run() {
        
        PriorityQueue<Persona> paraAgendarDpto = Server.paraAgendar.get(departamento.getNombre());
        try {
            
          
            departamento.getSemNumVacunas().acquire();
            
            Boolean necesitoAgendar = false;      
            Persona p = null;
            while(departamento.getVacunasDisponibles() > 1){
                
                departamento.getSemPersonasxDepartamento().acquire();
                if (!paraAgendarDpto.isEmpty())
                {                  
                     p = paraAgendarDpto.remove();
                     necesitoAgendar=true;
                }
                departamento.getSemPersonasxDepartamento().release();
                if (necesitoAgendar)
                {
                    departamento.agendar(p);               
                    departamento.setVacunasDisponibles(departamento.getVacunasDisponibles() - 2);
                    necesitoAgendar=false;
                }    
             
            }
            
            departamento.getSemNumVacunas().release();
            
            
        
        
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloAgendar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
   
    
}
