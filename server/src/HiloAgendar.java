
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
            
          
            while(!paraAgendarDpto.isEmpty()){
                departamento.getSemDepartamento().acquire();
                Persona p = paraAgendarDpto.remove();
                departamento.getSemDepartamento().release();
                departamento.agendar(p);
            }
        
        
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloAgendar.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    
    
   
    
}
