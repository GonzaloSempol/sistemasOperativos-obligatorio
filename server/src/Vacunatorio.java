
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
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
        Date fechaActual = Date.from(Server.r.instant());
        for(int i=1; i<365; i++)
        {
            agenda.put(fechaActual, new LinkedList<>());
            Date fecha = new Date(fechaActual.getTime()+ (1000 * 60 * 60 * 24) );
            fechaActual=fecha;
            System.out.println(fechaActual);
        }
         //  System.out.println(agenda.size());
    }
    
    
    public void agendar(Persona p, Date fechaActual){
              
        
        
        SortedMap<Date, LinkedList<Persona>> AgendaConfechasMayoresAHoy = this.agenda.tailMap(fechaActual);
        
        Date fechaDisponible=null;
        for( Map.Entry<Date, LinkedList<Persona>> f : AgendaConfechasMayoresAHoy.entrySet())
        {
            if (f.getValue().size() < this.capacidadPorTurno)
            {
                fechaDisponible=f.getKey();
                this.agenda.get(fechaDisponible).add(p);
                break;
            }
            
        }
        if(fechaDisponible != null)
        {
            long dias28 =((long)1000 * (long)60 * (long)60 * (long)24 * (long)28);
            Date fechaDosis2 = new Date(fechaDisponible.getTime() + dias28 );
            
            System.out.println("Se agenda: " + p.getCI() + " de edad: " + p.getEdad() + " en:" + this.nombre + " fecha Dosis 1: " + fechaDisponible + " fecha Dosis 2: " + fechaDosis2 );
            try {
                p.getSemPersona().acquire();
                    p.setFechaDosis1(fechaDisponible);
                    p.setFechaDosis2(fechaDosis2);
                    p.setEstaAgendada(true);
                    p.setEstaEnEspera(false);
                    p.setVacunatorio(this);
                p.getSemPersona().release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Vacunatorio.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        }
        
        
        
        
           
    }
    
    
    
}
