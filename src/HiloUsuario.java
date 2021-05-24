
import java.net.*;
import java.io.*;
import java.net.*;
import java.io.*;
public class HiloUsuario implements Runnable {

    private String CI;
    private Departamento departamento;
    private Socket clientSocket;
 
    public HiloUsuario(Socket socket) {
        this.clientSocket = socket;
    }

    
    
    
    @Override
    public void run() {
        try{

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
        
        clientSocket.close();
        System.out.println("Se cierra el socket");
        
    } catch (IOException e) 
      {
        System.out.println("Exception caught when trying to listen on port " + 81 + " or listening for a connection");
        System.out.println(e.getMessage());
      }


    }
}    
