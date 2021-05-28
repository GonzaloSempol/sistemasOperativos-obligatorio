
import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.PriorityQueue;

public class Server {
    
    public static Map<String, Persona> personas = new HashMap<>();
    
    public static Map<String, PriorityQueue> paraAgendar = new HashMap<>();
    
    
    
    
    
    
    
    public static void main(String[] args) throws IOException {
        
        //Datos de prueba
        Vacunatorio vac1 = new Vacunatorio("Vacunatorio Antel Arena");
        Vacunatorio vac2 = new Vacunatorio("Vacunatorio Pando");
        LinkedList<Vacunatorio> vacunatoriosMvd = new LinkedList<>();
        LinkedList<Vacunatorio> vacunatoriosCanelones = new LinkedList<>();
        vacunatoriosMvd.add(vac1);
        vacunatoriosCanelones.add(vac2);
        Departamento depMontevideo = new Departamento("Montevideo", vacunatoriosMvd);
        Departamento depCanelones = new Departamento("Canelones", vacunatoriosCanelones);
        
        
        
        //Cargamos personas en el hashmap
        String[] h = ManejadorArchivosGenerico.leerArchivo("src/BDpersonas.csv");
        for (int i=0; i<h.length; i++){
            String lineas = h[i];
            String[] parts = lineas.trim().split(";");

            Persona p = new Persona(parts[0],Integer.parseInt(parts[1]));
            personas.put(parts[0],p);


        }
       
        


        
        
          
        
        //Cargamos los departamentos
        paraAgendar.put(depMontevideo.getNombre(), new PriorityQueue<>());
        paraAgendar.put(depCanelones.getNombre(), new PriorityQueue<>());
        
        
        
        
        
        //Fin datos prueba
        
        //Para agendar
        

        try(           
            ServerSocket AppSocket = new ServerSocket(81); //Creamos una instancia de serverSocket con el puerto deseado
            ){
            
                LinkedList<Thread> colaApp;
                colaApp = new LinkedList<>();

                System.out.println("El server está escuchando en el puerto: " + 81);
                while(true){
                    Socket clientSocket = AppSocket.accept();   //Crea el socket, se bloquea y queda en LISTENING   
                    Thread Hilo = new Thread(new HiloUsuario(clientSocket));
                    Hilo.start(); //Esto lo haria el planificador luego
                    colaApp.add(Hilo);
                }

            }
    }
}
