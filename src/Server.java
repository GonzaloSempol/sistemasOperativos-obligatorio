/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gonzalo.sempol
 */
import java.net.*;
import java.io.*;

public class Server {
    public static void main(String[] args) throws IOException {
        
                
        int portNumber = 8888; //Definimos el puerto donde escucharemos
        
        try(
            ServerSocket serverSocket = new ServerSocket(portNumber); //Creamos una instancia de serverSocket con el puerto deseado
            ){
            
            
            System.out.println("El server está escuchando en el puerto: " + portNumber);
         
            Socket clientSocket = serverSocket.accept();   //Crea el socket, se bloquea y queda en LISTENING   
            System.out.println("Se conectó:" + clientSocket.getRemoteSocketAddress()); //Si pasamos a esta linea es porque un nuevo cliente se conectó

            ///Creamos objetos para manejar input y output 
            PrintWriter out =  new PrintWriter(clientSocket.getOutputStream(), true); //Para enviar texto al cliente 
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Para leer un string del cliente
                        
            String inputLine;
            
            do 
            {
                inputLine = in.readLine();
                out.println(inputLine  + "  <-- Te escucho");//Respondemos al cliente un mensaje
                System.out.println("El Cliente dice:" + inputLine);//Mostramos en pantalla lo que el cliente dice    
            }while ((!inputLine.equals("fin"))); //Hasta que el cliente diga fin.
            
            serverSocket.close();
            System.out.println("Se cierra el socket");
            
        } catch (IOException e) 
          {
            System.out.println("Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
          }
    }
}
