
import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.util.LinkedList;
import java.util.Queue;
import java.util.PriorityQueue;

public class Server {
    public static void main(String[] args) throws IOException {


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
