
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
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

        Boolean pudeAgendar = false;
        Date fechaActual = Date.from(Server.r.instant());

        int i = 0;

        while (!pudeAgendar && i < vacunatorios.size()) {
            Vacunatorio v = vacunatorios.get(i);
            if (v.hayDisponibilidadMayorA(fechaActual)) {
                v.agendar(p);
                pudeAgendar = true;
            }
            i++;

        }
        if (!pudeAgendar) {
            Vacunatorio vacLibre = getVacunatorioMinDates();
            vacLibre.crearFecha(fechaActual);
            vacLibre.agendar(p);
        }

    }

    private Vacunatorio getVacunatorioMinDates() {
        int i = Integer.MAX_VALUE;

        Vacunatorio min = vacunatorios.getFirst(); //Agarramos uno cualquiera
        for (Vacunatorio v : vacunatorios) { //Buscamos el que tenga menos dias

            if (v.getAgenda().size() < i) {
                min = v;
                i = v.getAgenda().size();
            }
        }
        return min;

    }

}
