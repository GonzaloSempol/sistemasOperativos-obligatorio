import java.util.LinkedList;
import java.util.concurrent.Semaphore;

public class Departamento {
    private String nombre;
    private LinkedList<Vacunatorio> vacunatorios;
    private Semaphore semDepartamento;

    public Departamento(String n, LinkedList v){
        this.nombre= n;
        this.vacunatorios = v;
        this.semDepartamento = new Semaphore(1);
    }
        public Departamento(String n){
        this.nombre= n;
        this.vacunatorios = new LinkedList<>();
        this.semDepartamento = new Semaphore(1);
    }

    public String getNombre() {
        return nombre;
    }

    public Semaphore getSemDepartamento() {
        return semDepartamento;
    }
    
    
    public void agendar(Persona p){
        Vacunatorio v = this.vacunatorios.getFirst();
        v.agendar(p);
        
        
    }
        
}
    
    
    
    


