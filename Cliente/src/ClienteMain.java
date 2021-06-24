
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Usuario
 */
public class ClienteMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
           //Cargamos personas con departamento
            String[] h = ManejadorArchivosGenerico.leerArchivo("src/Entradas.csv");
            for (int i=0; i<h.length; i++){
               
                try {
                    
                    if(i % 1000 == 0){
                        Thread.sleep(1000);
                    }
                
                
                    String lineas = h[i];
                    String[] parts = lineas.trim().split(";");
                    ThreadCliente cliente = new ThreadCliente(parts[0],parts[1],Integer.parseInt(parts[2]));
                    Thread hilo = new Thread(cliente);
                    hilo.start();

                    } catch (InterruptedException ex) {
                        Logger.getLogger(ClienteMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
                
            }
    }
    
}
