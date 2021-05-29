
import java.net.*;
import java.io.*;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
public class HiloUsuario implements Runnable {

    private String CI;
    private Departamento departamento;
    private Socket clientSocket;
    private Semaphore semEscribirCanal;
 
    public HiloUsuario(Socket socket, Semaphore semEscribirCanal) {
        this.clientSocket = socket;
        this.semEscribirCanal = semEscribirCanal;
    }

    
    
    
    @Override
    public void run() {
        try{

        System.out.println("Se conectó:" + clientSocket.getRemoteSocketAddress()); //Si pasamos a esta linea es porque un nuevo cliente se conectó

        ///Creamos objetos para manejar input y output 
        PrintWriter out =  new PrintWriter(clientSocket.getOutputStream(), true); //Para enviar texto al cliente 
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Para leer un string del cliente
                    
        String inputLine;
        
        
            out.println("Ingrese su ci:");
            String ci = in.readLine();
            out.println("Ingrese su departamento:");
            String departamento = in.readLine();
            
            out.println(ci  + departamento + "  <-- Te escucho");//Respondemos al cliente un mensaje
            
            System.out.println("El Cliente dice:" + ci +" :" + departamento);//Mostramos en pantalla lo que el cliente dice   
            
            
            //Validar CI
            if (Server.personas.containsKey(ci))
            {
                out.println("Estas validado");
                
                
            try {
                //Semaforo-Mutex!!!
                //Uno por cada departamento, para que no escriban en la cola por dep a la vez.
                Semaphore semDepartamento = Server.departamentos.get(departamento).getSemDepartamento();
                semDepartamento.acquire();
                    PriorityQueue<Persona> colaDelDepartamento = Server.paraAgendar.get(departamento);
                    colaDelDepartamento.add(Server.personas.get(ci));
                semDepartamento.release();
                
                //Hardcodeado cantidad
                /*
                if(colaDelDepartamento.size() == 6)
                {
                    while(!colaDelDepartamento.isEmpty())
                    {
                        Persona p1 = colaDelDepartamento.poll();
                        System.out.println(p1.getCI() + "-" + p1.getEdad());

                    }
                }
                */
                
            } catch (InterruptedException ex) {
                Logger.getLogger(HiloUsuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
                out.println("Estas DENEGADO");
            }
            
            
            
        
        clientSocket.close();
        System.out.println("Se cierra el socket");
        
    } catch (IOException e) 
      {
        System.out.println("Exception caught when trying to listen on port " + 81 + " or listening for a connection");
        System.out.println(e.getMessage());
      }


    }
}    
