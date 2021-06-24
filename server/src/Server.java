
import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;

public class Server {

    public static Map<String, Persona> personasHabilitadas = new HashMap<>();

    public static Map<String, PriorityQueue> paraAgendar = new HashMap<>();

    public static Map<String, Departamento> departamentos = new HashMap<>();
    public static Log log;  
    
    
    
    public static Reloj r = new Reloj();
    public static int[] rangos = {90,80,75,60,40,18};
    public static int rangoActual=rangos[0];
    public static int i=0;
    
    public static void aumentarRango()
    {
        if(i < Server.rangos.length -1 ){
                i++;
                Server.rangoActual=Server.rangos[i];
                System.out.println("El rango habilitado es mayores de: "+ Server.rangoActual + " años.");

        }
    }
    
    
    public static void main(String[] args) throws IOException, InterruptedException {

        //Datos de prueba
        //Cargamos los departamentos
        String[] h = ManejadorArchivosGenerico.leerArchivo("src/Departamentos.csv");
        
        for (int i = 0; i < h.length; i++) {
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");
            Departamento dep = new Departamento(parts[0], Float.parseFloat(parts[1]));
            departamentos.put(dep.getNombre(), dep);
            paraAgendar.put(dep.getNombre(), new PriorityQueue<>());
        }  
       
        //Cargamos los Vacunatorios
        h = ManejadorArchivosGenerico.leerArchivo("src/Vacunatorios.csv");
        
        for (int i = 0; i < h.length; i++) {
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");
            Vacunatorio vac = new Vacunatorio(parts[1], Integer.parseInt(parts[2]));
            departamentos.get(parts[0]).getVacunatorios().add(vac);
        
        }      
        
         
        
        //Cargamos personas en el hashmap
        h = ManejadorArchivosGenerico.leerArchivo("src/BDpersonas.csv");
        for (int i = 0; i < h.length; i++) {
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");

            Persona p = new Persona(parts[0], Integer.parseInt(parts[1]),Boolean.parseBoolean(parts[2]) );
            personasHabilitadas.put(parts[0], p);

        }

        
        log = new Log();
        HiloReloj hilor = new HiloReloj(r);
        Thread threadHilor = new Thread(hilor);
        threadHilor.setPriority(Thread.MAX_PRIORITY);
        threadHilor.start(); //cada 5 segundos abrimos la agenda
        

        //Fin datos prueba
        //Para agendar
        System.out.println("El rango habilitado es mayores de: "+ Server.rangoActual + " años.");
        for(int p=81; p<=84; p++){
        
            HiloCanal hc = new HiloCanal(p);
            Thread thc = new Thread(hc);
            thc.setPriority(Thread.MAX_PRIORITY);
            thc.start();
        }
        
        
    }
}
