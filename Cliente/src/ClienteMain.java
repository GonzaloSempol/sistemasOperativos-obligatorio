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
                String lineas = h[i];
                String[] parts = lineas.trim().split(";");
                ThreadCliente cliente = new ThreadCliente(parts[0],parts[1],Integer.parseInt(parts[2]));
                Thread hilo = new Thread(cliente);
                hilo.start();
                
            }
    }
    
}
