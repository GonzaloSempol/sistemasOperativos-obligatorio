
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloReloj implements Runnable {

    private int dias = 1;
    private Reloj reloj;

    public HiloReloj(Reloj reloj) {
        this.reloj = reloj;
    }

    public void repartirVacunas(int cantVacunas, int mes) {

        HiloRepartirVacunas h = new HiloRepartirVacunas(cantVacunas, mes); //Crea el hilo repartir vacunas
        Thread th = new Thread(h);
        th.setPriority(Thread.MAX_PRIORITY - 1);
        th.start(); //Inicia el hilo
        System.out.println("Se comienza a repartir vacunas a los departamentos");
    }

    public void agendarBatch() {
        for (String d : Server.departamentos.keySet()) { //Para cada departamento

            HiloAgendar h = new HiloAgendar(d); //Crea el hilo agendar
            Thread th = new Thread(h);
            th.setPriority(Thread.MIN_PRIORITY);
            th.start(); //Inicia el hilo
            System.out.println("Se comienza a agendar en: " + d + ": " + reloj.instant().truncatedTo(ChronoUnit.DAYS));

        }

    }

    @Override
    public void run() {

        while (true) {
            try {

                Thread.sleep(1000); //Velocidad de la simulacion, equivale a un dia
                System.out.println("DIA:" + dias + " " + reloj.instant().truncatedTo(ChronoUnit.DAYS));
                dias++;

                Date fechaActual = Date.from(Server.r.instant());
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaActual);
                int vacunas = 0;

                //Cierre de agenda cada 2 semanas
                if (cal.get(Calendar.DAY_OF_MONTH) % 14 == 0) {
                    agendarBatch();//Se manda agendar batch
                    System.out.println("Agendando...");
                }

                if (cal.get(Calendar.DAY_OF_MONTH) == 1) {
                    Server.aumentarRango(); //Se aunmenta el rango cada 1ero de mes
                    Server.log.imprimirCSV(cal.get(Calendar.MONTH) - 1);

                }

                //LLEGADA DE VACUNAS
                switch (cal.get(Calendar.MONTH) + 1) {
                    //Febrero
                    case 2:
                        switch (cal.get(Calendar.DAY_OF_MONTH)) {
                            case 26:
                                vacunas = 844;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                        }
                        break;
                    //Marzo
                    case 3:
                        switch (cal.get(Calendar.DAY_OF_MONTH)) {
                            case 10:
                                vacunas = 220;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                            case 16:
                                vacunas = 6845;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                            case 17:
                                vacunas = 234;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                            case 25:
                                vacunas = 220;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                        }
                        break;
                    //Abril
                    case 4:
                        switch (cal.get(Calendar.DAY_OF_MONTH)) {
                            case 4:
                                vacunas = 211;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                        }
                        break;
                    //Mayo
                    case 5:
                        switch (cal.get(Calendar.DAY_OF_MONTH)) {
                            case 8:
                                vacunas = 4393;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                            case 14:
                                vacunas = 221;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                            case 15:
                                vacunas = 524;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                            case 29:
                                vacunas = 2416;
                                repartirVacunas(vacunas, cal.get(Calendar.MONTH));
                                System.out.println("Llegaron " + vacunas + " vacunas");
                                break;
                        }
                        break;

                }

            } catch (InterruptedException ex) {
                Logger.getLogger(HiloReloj.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
