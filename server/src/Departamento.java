
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.Semaphore;

public class Departamento {

    private String nombre;
    private LinkedList<Vacunatorio> vacunatorios;
    private Semaphore semPersonasxDepartamento;
    private Semaphore semNumVacunas;
    private double densidadPoblacional;
    private int vacunasDisponibles;
    

    
    
    
    public Departamento(String n, LinkedList v, float d) {
        this.nombre = n;
        this.vacunatorios = v;
        this.densidadPoblacional = d;
        this.semNumVacunas = new Semaphore(1);
        this.semPersonasxDepartamento = new Semaphore(1);
    }

    public LinkedList<Vacunatorio> getVacunatorios() {
        return vacunatorios;
    }
    
    
    
    public Semaphore getSemNumVacunas() {
        return semNumVacunas;
    }

        
    
    public int getVacunasDisponibles() {
        return vacunasDisponibles;
    }

    public void setVacunasDisponibles(int vacunasDisponibles) {
        this.vacunasDisponibles = vacunasDisponibles;
    }
    
    

    public double getDensidadPoblacional() {
        return densidadPoblacional;
    }

    public void setDensidadPoblacional(float densidadPoblacional) {
        this.densidadPoblacional = densidadPoblacional;
    }
    
    
    
    


    public String getNombre() {
        return nombre;
    }

    public Semaphore getSemPersonasxDepartamento() {
        return semPersonasxDepartamento;
    }

    public void agendar(Persona p) {

        
        Date fechaActual = Date.from(Server.r.instant());
        Vacunatorio v = getVacunatorioMinDates(fechaActual);
        v.agendar(p, new Date(fechaActual.getTime() + (1000 * 60 * 60 * 24 * 5)), this.getNombre());
        

    }

    private Vacunatorio getVacunatorioMinDates(Date fechaActual) {
        
        
        int i = Integer.MAX_VALUE;

        Vacunatorio min = vacunatorios.getFirst(); //Agarramos uno cualquiera
        for (Vacunatorio v : vacunatorios) { //Buscamos el que tenga menos dias
            
           
            SortedMap<Date, LinkedList<Persona>> AgendaConfechasMayoresAHoy = v.getAgenda().tailMap(fechaActual);
            int personasAnotadas=0;
            for( Map.Entry<Date, LinkedList<Persona>> f : AgendaConfechasMayoresAHoy.entrySet())
            {
                personasAnotadas += f.getValue().size();
                      
            }   
            
            if (personasAnotadas < i) {
                min = v;
                i = personasAnotadas;
            }
        }
        return min;

    }
    
    
}
