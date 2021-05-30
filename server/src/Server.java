
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

    public static Reloj r = new Reloj();

    public static void main(String[] args) throws IOException, InterruptedException {

        //Datos de prueba
        Vacunatorio vac1 = new Vacunatorio("Vacunatorio Antel Arena", 2);
        Vacunatorio vac2 = new Vacunatorio("Vacunatorio Pando", 2);
        Vacunatorio vac3 = new Vacunatorio("Vacunatorio Antel Arena 2", 3);
        LinkedList<Vacunatorio> vacunatoriosMvd = new LinkedList<>();
        LinkedList<Vacunatorio> vacunatoriosCanelones = new LinkedList<>();
        vacunatoriosMvd.add(vac1);
        vacunatoriosMvd.add(vac3);
        vacunatoriosCanelones.add(vac2);
        Departamento depMontevideo = new Departamento("Montevideo", vacunatoriosMvd);
        Departamento depCanelones = new Departamento("Canelones", vacunatoriosCanelones);
        departamentos.put(depCanelones.getNombre(), depCanelones);
        departamentos.put(depMontevideo.getNombre(), depMontevideo);

        //Cargamos personas en el hashmap
        String[] h = ManejadorArchivosGenerico.leerArchivo("src/BDpersonas.csv");
        for (int i = 0; i < h.length; i++) {
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");

            Persona p = new Persona(parts[0], Integer.parseInt(parts[1]));
            personasHabilitadas.put(parts[0], p);

        }

        //Cargamos los departamentos
        paraAgendar.put(depMontevideo.getNombre(), new PriorityQueue<>());
        paraAgendar.put(depCanelones.getNombre(), new PriorityQueue<>());

        HiloReloj hilor = new HiloReloj(r);
        Thread threadHilor = new Thread(hilor);
        threadHilor.start(); //cada 5 segundos abrimos la agenda

        //Fin datos prueba
        //Para agendar
       
        for(int p=81; p<=84; p++){
        
            HiloCanal hc = new HiloCanal(p);
            Thread thc = new Thread(hc);
            thc.start();
        }
        
        
    }
}
