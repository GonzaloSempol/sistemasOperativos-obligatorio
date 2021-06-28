
import java.io.*;
import java.lang.Thread;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Server {

    public static Map<String, Persona> personasHabilitadas = new HashMap<>();
    public static Map<String, PriorityQueue> paraAgendar = new HashMap<>();
    public static Map<String, Departamento> departamentos = new HashMap<>();
    public static Log log;
    public static Reloj r = new Reloj();
    public static int[] rangos = {90, 80, 75, 60, 40, 18};
    public static int rangoActual = rangos[0];
    public static int indiceRango = 0;

    
    //Se aumenta el rango habilitado
    public static void aumentarRango() {
        if (indiceRango < Server.rangos.length - 1) {
            indiceRango++;
            Server.rangoActual = Server.rangos[indiceRango];
            System.out.println("El rango habilitado es mayores de: " + Server.rangoActual + " años.");

        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        //Cargamos los departamentos del csv
        String[] h = ManejadorArchivosGenerico.leerArchivo("src/Departamentos.csv");

        for (int i = 0; i < h.length; i++) {
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");
            Departamento dep = new Departamento(parts[0], Float.parseFloat(parts[1]));
            departamentos.put(dep.getNombre(), dep);
            paraAgendar.put(dep.getNombre(), new PriorityQueue<>());
        }

        //Cargamos los Vacunatorios del csv
        h = ManejadorArchivosGenerico.leerArchivo("src/Vacunatorios.csv");

        for (int i = 0; i < h.length; i++) {
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");
            Vacunatorio vac = new Vacunatorio(parts[1], Integer.parseInt(parts[2]));
            departamentos.get(parts[0]).getVacunatorios().add(vac);

        }

        //Cargamos personas del csv al hashmap de personas habilitadas
        h = ManejadorArchivosGenerico.leerArchivo("src/BDpersonas.csv");
        for (int i = 0; i < h.length; i++) {
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");
            Persona p = new Persona(parts[0], Integer.parseInt(parts[1]), Boolean.parseBoolean(parts[2]));
            personasHabilitadas.put(parts[0], p);

        }
        
        log = new Log();//Se crea el log
        HiloReloj hilor = new HiloReloj(r);//Se crea el hilo reloj
        Thread threadHilor = new Thread(hilor);
        threadHilor.setPriority(Thread.MAX_PRIORITY);
        threadHilor.start();//Se inicia el reloj

        
        //Se imprime el rango habilitado al inicio de la simulacion
        System.out.println("El rango habilitado es mayores de: " + Server.rangoActual + " años.");
        
        //Se crean los hilos canal para todos los puertos donde estaremos escuchando
        for (int p = 81; p <= 84; p++) {

            HiloCanal hc = new HiloCanal(p); //Se crea el hilo canal
            Thread thc = new Thread(hc);
            thc.setPriority(Thread.MAX_PRIORITY);
            thc.start(); //Se inicia
        }

    }
}
