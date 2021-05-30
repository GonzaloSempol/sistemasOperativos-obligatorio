
import java.util.Date;
import java.util.LinkedList;
import java.util.SortedMap;

public class Vacunatorio {
    private String nombre;
    private int capacidadPorTurno;
    private LinkedList<Persona> agenda;
    
    /*
    private punteroAPrimerDiaConCapacidadDisponible;
    
    Horarios
            Hora
            Lista Personas
                    
    Fecha actual
            tengo alguno con disponible?
            Si --> inserto en ese
    
    24/4-->23/4-->22/4                
    Elem-->Elem-->
    
    22/4
    lista de personas
    
           
    
                    
    Dia Hora (listas de personas).lenght >
    */

    public String getNombre() {
        return nombre;
    }

    public int getCapacidadPorTurno() {
        return capacidadPorTurno;
    }

        
    
    
    public Vacunatorio(String nombre) {
        this.nombre = nombre;
        this.agenda = new LinkedList<>(); 
    }
    
    
    public void agendar(Persona p){
        this.agenda.add(p);
        System.out.println("Se agenda: " + p.getCI() + " de edad: " + p.getEdad() + " en:" + this.nombre);
        
    }
    //public getTengoLibre(date)
            
    //public insertarEnDia(date)
    
    
}
