
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloAgendar implements Runnable {

    String dep;

    public HiloAgendar(String departamento) {
        this.dep = departamento;
    }

    @Override
    public void run() {

        try {

            Server.departamentos.get(dep).getSemNumVacunas().acquire(); //Pedimos el semaforo pues trabajaremos con las vacunas del dpto
            Departamento departamento = Server.departamentos.get(dep);

            while (departamento.getVacunasDisponibles() > 1) {

                departamento.getSemPersonasxDepartamento().acquire(); //Pedimos el semaforo para quitar a la persona de la lista del dpto

                if (!Server.paraAgendar.get(departamento.getNombre()).isEmpty()) {
                    Persona p = (Persona) Server.paraAgendar.get(departamento.getNombre()).remove();

                    departamento.getSemPersonasxDepartamento().release(); //Devolvemos el semaforo
                    departamento.agendar(p); //Se agenda la persona en el departamento
                    departamento.substractVacunas(); //Se quitan 2 vacunas del departamento por persona agendada

                } else {
                    departamento.getSemPersonasxDepartamento().release();//Devolvemos el semaforo
                    break;
                }

            }

            departamento.getSemNumVacunas().release();//Devolvemos el semaforo

        } catch (InterruptedException ex) {
            Logger.getLogger(HiloAgendar.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
