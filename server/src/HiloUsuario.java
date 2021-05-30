
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

    public HiloUsuario(Socket socket) {
        this.clientSocket = socket;

    }

    @Override
    public void run() {
        try {

            System.out.println("Se conectó:" + clientSocket.getRemoteSocketAddress()); //Si pasamos a esta linea es porque un nuevo cliente se conectó

            ///Creamos objetos para manejar input y output 
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); //Para enviar texto al cliente 
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //Para leer un string del cliente

            String inputLine;

            out.println("Ingrese su ci:");
            String ci = in.readLine();
            out.println("Ingrese su departamento:");
            String departamento = in.readLine();

            out.println(ci + departamento + "  <-- Te escucho");//Respondemos al cliente un mensaje

            System.out.println("[Puerto:" + clientSocket.getLocalPort() + "] El Cliente dice:" + ci + " : " + departamento);//Mostramos en pantalla lo que el cliente dice   

            //Validar CI
            if (Server.personasHabilitadas.containsKey(ci)) {

                Persona p = Server.personasHabilitadas.get(ci);
                p.getSemPersona().acquire();

                if ((p.getDosis() > 0)) {
                    out.println(p.getCI() + ": Ya vacunado");
                    p.getSemPersona().release();
                } else if (p.getEstaEnEspera()) {
                    out.println(p.getCI() + ": Ya esta en espera");
                    p.getSemPersona().release();

                } else {

                    out.println(p.getCI() + ": Su solicitud será procesada..."); //tengo ya las dosis? estoy en espera?

                    try {
                        //Semaforo-Mutex!!!
                        //Uno por cada departamento, para que no escriban en la cola por dep a la vez.
                        Semaphore semDepartamento = Server.departamentos.get(departamento).getSemDepartamento();
                        semDepartamento.acquire();
                        PriorityQueue<Persona> colaDelDepartamento = Server.paraAgendar.get(departamento);
                        colaDelDepartamento.add(p);
                        semDepartamento.release();
                        p.setEstaEnEspera(true);

                        p.getSemPersona().release();

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
                }

            } else {
                out.println(ci + ": Su ci no está habilitada");
            }

            clientSocket.close();
            System.out.println("Se cierra el socket");

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port " + 81 + " or listening for a connection");
            System.out.println(e.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
