
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloRepartirVacunas implements Runnable {

    private int cantVacunas;
    private int mes;

    public HiloRepartirVacunas(int cantVacunas, int mes) {
        this.cantVacunas = cantVacunas;
        this.mes = mes;
    }

    @Override
    public void run() {

        int aRepartir = cantVacunas; //aRepartir comienza con la cantidad de vacunas que entraron
        for (Departamento d : Server.departamentos.values()) { //Para cada depto

            try {
                d.getSemNumVacunas().acquire(); //Se toma el semaforo para modificar las vacunas dentro del departamento
                aRepartir -= (int) Math.floor(d.getDensidadPoblacional() * cantVacunas); //Disminuye repartir de acuerdo a la cantidad de vacunas a asignar al depto
                int vacunasAsignadas = d.getVacunasDisponibles() + (int) Math.floor((d.getDensidadPoblacional() * cantVacunas));
                d.setVacunasDisponibles(vacunasAsignadas); //Se asignan las vacunas correspondientes
                d.getSemNumVacunas().release(); //Se libera el semaforo
                
                //Guardamos al log adquiriendo el semaforo
                Server.log.getSemLogVac().acquire();
                    Server.log.addVacunasDepartamentoMes(d.getNombre(), mes, (int) Math.floor(d.getDensidadPoblacional() * cantVacunas));
                Server.log.getSemLogVac().release();

            } catch (InterruptedException ex) {
                Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (aRepartir > 0) { //Se le da las vacunas sobrantes a Montevideo
            Departamento d = Server.departamentos.get("Montevideo");
            try {
                d.getSemNumVacunas().acquire();
                d.setVacunasDisponibles(aRepartir + d.getVacunasDisponibles());
                d.getSemNumVacunas().release();

                Server.log.getSemLogVac().acquire();
                Server.log.addVacunasDepartamentoMes(d.getNombre(), mes, aRepartir);
                Server.log.getSemLogVac().release();

            } catch (InterruptedException ex) {
                Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
