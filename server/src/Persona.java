public class Persona {
    private String CI;
    private int edad;
    private Departamento departamento; 
    private Vacunatorio vacunatorio;
    private int Dosis;

    public Persona(String CI, Departamento departamento, int edad) {
        this.CI = CI;
        this.edad = edad;
        this.departamento = departamento;
        this.Dosis = 0;
    }

    public String getCI() {
        return CI;
    }
    
    

}
