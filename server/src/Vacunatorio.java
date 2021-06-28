
import java.util.Calendar;
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
        for (int i = 1; i < 365; i++) { //Se crea un año de agenda
            agenda.put(fechaActual, new LinkedList<>());
            Date fecha = new Date(fechaActual.getTime() + (1000 * 60 * 60 * 24));
            fechaActual = fecha;
            
        }
        
    }

    //Agendar una persona en un vacunatorio
    public void agendar(Persona p, Date fechaActual, String nombreDepto) {

        SortedMap<Date, LinkedList<Persona>> AgendaConfechasMayoresAHoy = this.agenda.tailMap(fechaActual);

        Date fechaDisponible = null;
        for (Map.Entry<Date, LinkedList<Persona>> f : AgendaConfechasMayoresAHoy.entrySet()) { //Recorremos todas las fechas mayores a hoy
            if (f.getValue().size() < this.capacidadPorTurno) { //si hay espacio
                fechaDisponible = f.getKey();
                this.agenda.get(fechaDisponible).add(p); //Se agrega la persona a la agenda

                break;
            }

        }
        if (fechaDisponible != null) { //Si hay fecha disponible
            long dias28 = ((long) 1000 * (long) 60 * (long) 60 * (long) 24 * (long) 28); //Se calcula el segundo dia de agenda 28 dias despues
            Date fechaDosis2 = new Date(fechaDisponible.getTime() + dias28);

            System.out.println("Se agenda: " + p.getCI() + " de edad: " + p.getEdad() + " en:" + this.nombre + " fecha Dosis 1: " + fechaDisponible + " fecha Dosis 2: " + fechaDosis2);
            
            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaDisponible);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(fechaDosis2);

            
            try {
                //Registramos en el log
                Server.log.getSemLog().acquire();
                    Server.log.registrar(cal.get(Calendar.MONTH), cal2.get(Calendar.MONTH), nombreDepto, this.getNombre(), p);
                Server.log.getSemLog().release();
            } catch (InterruptedException ex) {
                Logger.getLogger(Vacunatorio.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                p.getSemPersona().acquire(); //Adquirimos el semaforo de persona y actulizamos su informacion
                p.setFechaDosis1(fechaDisponible);
                p.setFechaDosis2(fechaDosis2);
                p.setEstaAgendada(true);
                p.setEstaEnEspera(false);
                p.setVacunatorio(this);
                p.getSemPersona().release(); //Liberamos el semaforo de persona
            } catch (InterruptedException ex) {
                Logger.getLogger(Vacunatorio.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
