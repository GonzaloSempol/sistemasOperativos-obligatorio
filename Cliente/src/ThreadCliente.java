
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ThreadCliente implements Runnable {
     
    public String hostName = "PC3310";
    private String ci;
    private String dep;
    private int portNumber;
    
    public ThreadCliente(String ci, String dep, int portNumber) 
    {
        this.ci=ci;
        this.dep=dep;
        this.portNumber=portNumber;
    
    }
       
        

       

    @Override
    public void run() {
        try (
            Socket serverSocket = new Socket(hostName, portNumber);
            PrintWriter out =  new PrintWriter(serverSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Conectado al servidor: " + serverSocket.getRemoteSocketAddress() );
            String userInput;
            
           
           
                //Thread.sleep(2000);
               /* while(in.readLine() == null)
                {
                   
                }*/
                System.out.println("El server dice: " + in.readLine());
                out.println(this.ci); //Mando info
                
                System.out.println("El server dice: " + in.readLine());
                out.println(this.dep); //Mando info
                
                System.out.println("El server dice: " + in.readLine());
                
               /* while(in.readLine() != null)
                {
                    
                }
                while(in.readLine() != null)
                {                     
                    
                }*/
                
                           
            
                /*
                System.out.println("El server dice: " + in.readLine());
                userInput = stdIn.readLine();
                out.println(userInput); //Mando info
                System.out.println("El server dice: " + in.readLine());
                userInput = stdIn.readLine();
                out.println(userInput); //Mando info
                System.out.println("El server dice: " + in.readLine());
                out.println(userInput); //Mando info
                System.out.println("El server dice: " + in.readLine());
                */
            
            
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


