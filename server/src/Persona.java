public class Persona implements Comparable{
    private String CI;
    private int edad;
    private Vacunatorio vacunatorio;
    private int Dosis;

    
     public Persona(String CI, int edad) {
        this.CI = CI;
        this.edad = edad;
        this.Dosis = 0;
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
