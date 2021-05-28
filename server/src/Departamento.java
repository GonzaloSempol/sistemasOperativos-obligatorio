import java.util.LinkedList;

public class Departamento {
    private String nombre;
    private LinkedList<Vacunatorio> vacunatorios;

    public Departamento(String n, LinkedList v){
        this.nombre= n;
        this.vacunatorios = v;
    }
        public Departamento(String n){
        this.nombre= n;
        this.vacunatorios = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }
    
    

}
