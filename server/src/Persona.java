
import java.util.concurrent.Semaphore;

public class Persona implements Comparable{
    private String CI;
    private int edad;
    private Vacunatorio vacunatorio;
    private Boolean estaEnEspera;
    private int dosis;
    private Semaphore semPersona;
    

    
     public Persona(String CI, int edad) {
        this.CI = CI;
        this.edad = edad;
        this.dosis = 0;
        this.semPersona = new Semaphore(1);
        this.estaEnEspera=false;
        
    }

    public Semaphore getSemPersona() {
        return semPersona;
    }

    public Boolean getEstaEnEspera() {
        return estaEnEspera;
    }

    public int getDosis() {
        return dosis;
    }

    public void setEstaEnEspera(Boolean estaEnEspera) {
        this.estaEnEspera = estaEnEspera;
    }
     
    

    public String getCI() {
        return CI;
    }

    public int getEdad() {
        return edad;
    }

   
    
    
    
    
  

    @Override
    public int compareTo(Object o) {
        Persona otra = (Persona)o;
        if(this.edad < otra.getEdad())
            return 1;
        if(this.edad == otra.getEdad())
            return 0;
        else
            return -1;
    }
    

}
