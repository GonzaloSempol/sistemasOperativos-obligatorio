
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
        
        int aRepartir = cantVacunas;
        for(Departamento d : Server.departamentos.values())
        {

            try {
                d.getSemNumVacunas().acquire();
                aRepartir -= (int) Math.floor(d.getDensidadPoblacional() * cantVacunas);
                int vacunasAsignadas = d.getVacunasDisponibles() + aRepartir ;        
                d.setVacunasDisponibles(vacunasAsignadas);
                d.getSemNumVacunas().release();

                Server.log.getSemLogVac().acquire();
                    Server.log.addVacunasDepartamentoMes(d.getNombre(), mes, (int) Math.floor(d.getDensidadPoblacional() * cantVacunas));
                Server.log.getSemLogVac().release();

           } catch (InterruptedException ex) {
                Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if(aRepartir > 0)
        {
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
