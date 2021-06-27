
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
    private Map<Integer, Map<String, Integer>> logVacunas;
    private long solRecibidas; 
    private Semaphore semLog;
    private Semaphore semLogVac;

    public Log() {
        semLog = new Semaphore(1);
        semLogVac=new Semaphore(1);
        this.log = new HashMap<>();
        this.logVacunas = new HashMap<>();
        for (int i = 0; i < 12; i++) {
            
            Map<String, Integer> vacunasPorDepartamento = new HashMap<>();
            Map<String, Map<String, Registro>> nivel1 = new HashMap<>();
            for (Map.Entry<String, Departamento> d : Server.departamentos.entrySet()) {
                Map<String, Registro> nivel0 = new HashMap<>();
                
                vacunasPorDepartamento.put(d.getKey(), 0);
                
                for (Vacunatorio v : d.getValue().getVacunatorios()) {
                    nivel0.put(v.getNombre(), new Registro());
                }

                nivel1.put(d.getKey(), nivel0);
                log.put(i, nivel1);
                
                logVacunas.put(i, vacunasPorDepartamento);
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
    //AGREGAR SEMAFORO
    public void imprimirCSV(int mes)
    {
        int cantRangos = Server.rangos.length;
        String[] out = new String[7+cantRangos];
        
        Map<String, Map<String, Registro>> logMes = log.get(mes);
        out[0] = mapearMes(mes) + ";;";
        out[1] = "; Vacunas recibidas;";
        out[2] = " ; "; 
        out[3] = " ;de Riesgo"; 
        
        
        for(int j=0; j<cantRangos; j++)
        {
            out[j+4] = " " +";"+"RANGO > " + Server.rangos[j] + " anos"; 
        }
        
        out[4+cantRangos] = " " +";" + "Total 1era Dosis";
        out[5+cantRangos] = " ;Total 2da Dosis";
        out[6+cantRangos] = " ;Acumulado Solicitudes Recibidas: " + this.solRecibidas;
        
        
        for (Map.Entry<String, Map<String, Registro>> d : logMes.entrySet()) {
                out[0] += d.getKey();
                out[1] += this.logVacunas.get(mes).get(d.getKey());
                
                for (Map.Entry<String, Registro> v : d.getValue().entrySet()){
                    out[0] += ";"; 
                    out[1] += ";"; 
                    out[2] += ";" + v.getKey();
                    out[3] += ";" + v.getValue().getDeRiesgo();
                    
                    for(int j=0; j<v.getValue().getPersonasPorRangos().length; j++)
                    {
                        out[4+j] += ";" + v.getValue().getPersonasPorRangos()[j];
                    
                    }
                    
                    
                    out[4+cantRangos] += ";" + v.getValue().getCantDosis1();
                    out[5+cantRangos] += ";" + v.getValue().getCantDosis2();
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

    public Semaphore getSemLogVac() {
        return semLogVac;
    }
    
    
    
    public void addVacunasDepartamentoMes(String departamento, Integer mes, Integer cantVacunas )
    {
            Integer cantVacunasDepto = logVacunas.get(mes).get(departamento);
            cantVacunasDepto += cantVacunas;
            logVacunas.get(mes).replace(departamento, cantVacunasDepto);
           
    
    
    }
    
    public String mapearMes(int m)
    {
        switch(m)
        {
            case 0: return "Enero";
            case 1: return "Febrero";
            case 2: return "Marzo";
            case 3: return "Abril";
            case 4: return "Mayo";
            case 5: return "Junio";
            case 6: return "Julio";
            case 7: return "Agosto";
            case 8: return "Septiembre";
            case 9: return "Octubre";
            case 10: return "Noviembre";
            case 11: return "Diciembre";
            
        
        }
        return "mes invalido";
    }
            

}
