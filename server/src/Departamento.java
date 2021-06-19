
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.Semaphore;

public class Departamento {

    private String nombre;
    private LinkedList<Vacunatorio> vacunatorios;
    private Semaphore semDepartamento;

    public Departamento(String n, LinkedList v) {
        this.nombre = n;
        this.vacunatorios = v;
        this.semDepartamento = new Semaphore(1);
    }

    public Departamento(String n) {
        this.nombre = n;
        this.vacunatorios = new LinkedList<>();
        this.semDepartamento = new Semaphore(1);
    }

    public String getNombre() {
        return nombre;
    }

    public Semaphore getSemDepartamento() {
        return semDepartamento;
    }

    public void agendar(Persona p) {

        
        Date fechaActual = Date.from(Server.r.instant());
        Vacunatorio v = getVacunatorioMinDates(fechaActual);
        v.agendar(p, new Date(fechaActual.getTime() + (1000 * 60 * 60 * 24 * 5)));
        

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
