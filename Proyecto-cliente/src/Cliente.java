
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Cliente {
     public static void main(String[] args) throws IOException 
     {
        
       
        String hostName = "PC3310";
        int portNumber = 81;

        try (
            Socket serverSocket = new Socket(hostName, portNumber);
            PrintWriter out =  new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Conectado al servidor: " + serverSocket.getRemoteSocketAddress() );
            String userInput;
            
                
                System.out.println("El server dice: " + in.readLine());
                userInput = stdIn.readLine();
                out.println(userInput); //Mando info
                System.out.println("El server dice: " + in.readLine());
                userInput = stdIn.readLine();
                out.println(userInput); //Mando info
                System.out.println("El server dice: " + in.readLine());
                out.println(userInput); //Mando info
                System.out.println("El server dice: " + in.readLine());
                
            
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
         
           }
     
     }
}
