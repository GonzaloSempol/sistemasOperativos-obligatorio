import java.util.LinkedList;

public class Departamento {
    private String nombre;
    private LinkedList<Vacunatorio> vacunatorios;

    public Departamento(String n, LinkedList v){
        this.nombre= n;
        this.vacunatorios = v;
    }

    public String getNombre() {
        return nombre;
    }
    
    

}
