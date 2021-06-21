
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
        Vacunatorio vac1 = new Vacunatorio("Vacunatorio Antel Arena", 2);
        Vacunatorio vac2 = new Vacunatorio("Vacunatorio Pando", 2);
        Vacunatorio vac3 = new Vacunatorio("Vacunatorio Antel Arena 2", 2);
        LinkedList<Vacunatorio> vacunatoriosMvd = new LinkedList<>();
        LinkedList<Vacunatorio> vacunatoriosCanelones = new LinkedList<>();
        vacunatoriosMvd.add(vac1);
        vacunatoriosMvd.add(vac3);
        vacunatoriosCanelones.add(vac2);
        Departamento depMontevideo = new Departamento("Montevideo", vacunatoriosMvd, 0.9f);
        Departamento depCanelones = new Departamento("Canelones", vacunatoriosCanelones, 0.1f);
        departamentos.put(depCanelones.getNombre(), depCanelones);
        departamentos.put(depMontevideo.getNombre(), depMontevideo);
        log = new Log();
        //Cargamos personas en el hashmap
        String[] h = ManejadorArchivosGenerico.leerArchivo("src/BDpersonas.csv");
        for (int i = 0; i < h.length; i++) {
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");

            Persona p = new Persona(parts[0], Integer.parseInt(parts[1]),Boolean.parseBoolean(parts[2]) );
            personasHabilitadas.put(parts[0], p);

        }

        //Cargamos los departamentos
        paraAgendar.put(depMontevideo.getNombre(), new PriorityQueue<>());
        paraAgendar.put(depCanelones.getNombre(), new PriorityQueue<>());

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
