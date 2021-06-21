
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gonzalo.sempol
 */
public class HiloCanal implements Runnable {

    int portNumber;
   

    public HiloCanal(int portNumber) {
        this.portNumber = portNumber;
    }
    
    
    
    @Override
    public void run() {
         try (
                 ServerSocket Socket = new ServerSocket(portNumber); //Creamos una instancia de serverSocket con el puerto deseado
             ) {

            System.out.println("El server está escuchando en el puerto: " + portNumber);
            while (true) {

                Socket clientSocket = Socket.accept();   //Crea el socket, se bloquea y queda en LISTENING   
                Thread Hilo = new Thread(new HiloUsuario(clientSocket));
                Hilo.setPriority(Thread.MAX_PRIORITY -1);
                Hilo.start(); //Esto lo haria el planificador luego
                

                
            }

        } catch (IOException ex) {
            Logger.getLogger(HiloCanal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
}
