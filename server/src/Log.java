
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author gonzalo.sempol
 */
public class Log {

    private Map<Integer, Map<String, Map<String, Registro>>> log;
    private long solRecibidas; 
    private Semaphore semLog;

    public Log() {
        semLog = new Semaphore(1);
        this.log = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            Map<String, Map<String, Registro>> nivel1 = new HashMap<>();
            for (Map.Entry<String, Departamento> d : Server.departamentos.entrySet()) {
                Map<String, Registro> nivel0 = new HashMap<>();

                for (Vacunatorio v : d.getValue().getVacunatorios()) {
                    nivel0.put(v.getNombre(), new Registro());
                }

                nivel1.put(d.getKey(), nivel0);
                log.put(i, nivel1);
            }
        }
    }

    public void registrar(Integer mes1, Integer mes2, String departamento, String vacunatorio, Persona p) {
        Registro reg1 = (((log.get(mes1)).get(departamento)).get(vacunatorio));
        Registro reg2 = (((log.get(mes2)).get(departamento)).get(vacunatorio));

        if (p.getEsDeRiesgo()) {
            reg1.addDeRiesgo();
            reg2.addDeRiesgo();
        } else {
            int i = 0;
            while (p.getEdad() < Server.rangos[i]) {
                i++;
            }
            reg1.addPersonaPorRango(i);
            reg2.addPersonaPorRango(i);

        }

        reg1.addCantDosis1();
        reg2.addCantDosis2();

    }

    public Semaphore getSemLog() {
        return semLog;
    }
    
    //Se imprimen DOSIS ADMINISTRADAS EN EL MES
    public void imprimirCSV(int mes)
    {
        int cantRangos = Server.rangos.length;
        String[] out = new String[6+cantRangos];
        
        Map<String, Map<String, Registro>> logMes = log.get(mes);
        out[0]=String.valueOf(mes) + "; ";
        out[1] += " ; "; 
        out[2] += " ;de Riesgo"; 
        
        
        for(int j=0; j<cantRangos; j++)
        {
            out[j+3] += " " +";"+"RANGO > " + Server.rangos[j] + " anos"; 
        }
        
        out[3+cantRangos] += " " +";" + "Total 1era Dosis";
        out[4+cantRangos] += " ;Total 2da Dosis";
        out[5+cantRangos] += " ;Total Solicitudes Recibidas: " + this.solRecibidas;
        
        
        for (Map.Entry<String, Map<String, Registro>> d : logMes.entrySet()) {
                out[0] += ";" + d.getKey();
                
                for (Map.Entry<String, Registro> v : d.getValue().entrySet()){
                    
                    out[1] += ";" + v.getKey();
                    out[2] += ";" + v.getValue().getDeRiesgo();
                    
                    for(int j=0; j<v.getValue().getPersonasPorRangos().length; j++)
                    {
                        out[3+j] += ";" + v.getValue().getPersonasPorRangos()[j];
                    
                    }
                    
                    
                    out[3+cantRangos] += ";" + v.getValue().getCantDosis1();
                    out[4+cantRangos] += ";" + v.getValue().getCantDosis2();
                }

               
            }
        
        ManejadorArchivosGenerico.escribirArchivo("C:\\git\\Server\\Obligatorio-Sistemas-Operativos-\\server\\src\\log.csv", out);
    
    }

    public long getSolRecibidas() {
        return solRecibidas;
    }

    public void addSolRecibidas() {
        this.solRecibidas++;
    }

}
