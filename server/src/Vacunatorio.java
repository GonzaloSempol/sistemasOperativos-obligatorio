
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vacunatorio {
    private String nombre;
    private int capacidadPorTurno;
    
    private SortedMap<Date, LinkedList<Persona>> agenda;
    
    /*
    private punteroAPrimerDiaConCapacidadDisponible;
    
    Horarios
            Hora
            Lista Personas
                    
    Fecha actual
            tengo alguno con disponible?
            Si --> inserto en ese
    
                    
    1/2-->3/2
    Elem-->Elem-->
    
    22/4
    lista de personas
    
           
    
                    
    Dia Hora (listas de personas).lenght >
    */

    public String getNombre() {
        return nombre;
    }

    public SortedMap<Date, LinkedList<Persona>> getAgenda() {
        return agenda;
    }

    public int getCapacidadPorTurno() {
        return capacidadPorTurno;
    }

        
    
    
    public Vacunatorio(String nombre, int capacidad) {
        this.nombre = nombre;
        this.agenda = new TreeMap<>(); 
        this.capacidadPorTurno = capacidad;
    }
    
    
    public void agendar(Persona p){
        this.agenda.get(agenda.lastKey()).add(p);
        System.out.println("Se agenda: " + p.getCI() + " de edad: " + p.getEdad() + " en:" + this.nombre + " Para la fecha: " + agenda.lastKey());
        try {
            p.getSemPersona().acquire();
                p.setFechaVacuna(agenda.lastKey());
                p.setEstaAgendada(true);
                p.setEstaEnEspera(false);
                p.setVacunatorio(this);
            p.getSemPersona().release();
        } catch (InterruptedException ex) {
            Logger.getLogger(Vacunatorio.class.getName()).log(Level.SEVERE, null, ex);
        }
           
    }
    
    public void crearFecha(Date f){
        
        Date nuevaFecha;
       
        if(agenda.isEmpty()){
            nuevaFecha = new Date(f.getTime() + (1000 * 60 * 60 * 24));
        }else{
            
               if (f.after(agenda.lastKey())){
                 nuevaFecha = new Date(f.getTime() + (1000 * 60 * 60 * 24));
                }else{
                    nuevaFecha = new Date(agenda.lastKey().getTime() + (1000 * 60 * 60 * 24));
                }
        }
        agenda.put(nuevaFecha, new LinkedList<>());
    }
    
    public Boolean hayDisponibilidadMayorA(Date f){
        if(agenda.isEmpty()){
            return false;
        }
        else{
            Date ultimaFecha = agenda.lastKey(); 
            return ((f.before(ultimaFecha) && agenda.get(ultimaFecha).size() < capacidadPorTurno ));
        }
        
    }
    //public getTengoLibre(date)
            
    //public insertarEnDia(date)
    
    
}
