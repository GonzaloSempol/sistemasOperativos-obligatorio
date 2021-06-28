
import java.net.*;
import java.io.*;
import java.util.PriorityQueue;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloUsuario implements Runnable {

    private Socket clientSocket;

    public HiloUsuario(Socket socket) {
        this.clientSocket = socket;

    }

    @Override
    public void run() {
        try {

            
            ///Creamos objetos para manejar input y output 
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); //Para enviar texto al cliente 
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Para leer un string del cliente

            //Hablamos con el cliente
            out.println("Ingrese su ci:");
            String ci = in.readLine();
            out.println("Ingrese su departamento:");
            String departamento = in.readLine();

              
            //Validar CI con respecto a cedulas habilitadas
            if (Server.personasHabilitadas.containsKey(ci)) {

                Persona p = Server.personasHabilitadas.get(ci);
                p.getSemPersona().acquire();//Aquirimos el semaforo de persona
                //Manejamos mensajes de respuesta
                if ((p.getDosis() > 0)) {
                    out.println(p.getCI() + ": INFO: Ya vacunado");
                    p.getSemPersona().release();
                } else if (p.getEstaEnEspera()) {
                    out.println(p.getCI() + ": INFO: Ya esta en espera");
                    p.getSemPersona().release();
                } else if ((p.getEdad() < Server.rangoActual) && (!p.getEsDeRiesgo())) {
                    out.println(p.getCI() + ": ERROR: No está en el rango de edad habilitado y no es de riesgo");
                    p.getSemPersona().release();
                } else if (p.getEstaAgendada()) {
                    out.println(p.getCI() + ": INFO: Ya tiene fecha para: " + p.getFechaDosis1() + "  y " + p.getFechaDosis2() + " en el vacunatorio: " + p.getVacunatorio().getNombre());
                    p.getSemPersona().release();
                } else {

                    out.println(p.getCI() + ": CORRECTO: Su solicitud será procesada..."); //tengo ya las dosis? estoy en espera?

                    try {
                        
                        Semaphore semDepartamento = Server.departamentos.get(departamento).getSemPersonasxDepartamento(); 
                        semDepartamento.acquire();//Adquirimos el semaforo de la lista de personas a agendar del depto
                        PriorityQueue<Persona> colaDelDepartamento = Server.paraAgendar.get(departamento);
                        colaDelDepartamento.add(p); //Añadimos a la persona a la cola de prioridad del departamento
                        semDepartamento.release(); //Liberamos el semaforo
                        p.setEstaEnEspera(true); //Seteamos a la persona como en espera

                        p.getSemPersona().release();//Liberamos el semaforo de la persona

                    } catch (InterruptedException ex) {
                        Logger.getLogger(HiloUsuario.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
            {
                out.println(ci + ": Su ci no está habilitada");
            }

            clientSocket.close();// Se cierra el socket
            

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + clientSocket.getLocalPort() + " or listening for a connection");
            System.out.println(e.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
