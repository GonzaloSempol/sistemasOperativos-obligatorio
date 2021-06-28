
import java.util.Date;
import java.util.concurrent.Semaphore;

public class Persona implements Comparable {

    private String CI;
    private int edad;
    private Boolean esDeRiesgo;
    private Vacunatorio vacunatorio;
    private Boolean estaEnEspera;
    private Boolean estaAgendada;
    private int dosis;
    private Semaphore semPersona;
    private Date fechaDosis1;
    private Date fechaDosis2;

    public Date getFechaDosis2() {
        return fechaDosis2;
    }

    public void setFechaDosis2(Date fechaDosis2) {
        this.fechaDosis2 = fechaDosis2;
    }

    public void setEstaAgendada(Boolean estaAgendada) {
        this.estaAgendada = estaAgendada;
    }

    public Boolean getEstaAgendada() {
        return estaAgendada;
    }

    public Vacunatorio getVacunatorio() {
        return vacunatorio;
    }

    public void setVacunatorio(Vacunatorio vacunatorio) {
        this.vacunatorio = vacunatorio;
    }

    public Boolean getEsDeRiesgo() {
        return esDeRiesgo;
    }

    public void setEsDeRiesgo(Boolean esDeRiesgo) {
        this.esDeRiesgo = esDeRiesgo;
    }

    public Persona(String CI, int edad, Boolean nivelDeRiesgo) {
        this.CI = CI;
        this.edad = edad;
        this.esDeRiesgo = nivelDeRiesgo;
        this.dosis = 0;
        this.semPersona = new Semaphore(1);
        this.estaEnEspera = false;
        this.estaAgendada = false;
    }

    public Date getFechaDosis1() {
        return fechaDosis1;
    }

    public void setFechaDosis1(Date fechaDosis1) {
        this.fechaDosis1 = fechaDosis1;
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
    //Override el comparator para poder ordenar a las personas en la colas de prioridad segun los criterios de riesgo y edad
    @Override
    public int compareTo(Object o) {
        Persona otra = (Persona) o;
        if (!this.esDeRiesgo && otra.getEsDeRiesgo()) {
            return 1;
        } else if (this.esDeRiesgo && !otra.getEsDeRiesgo()) {
            return -1;
        } else {
            if (this.edad < otra.getEdad()) {
                return 1;
            }
            if (this.edad == otra.getEdad()) {
                return 0;
            } else {
                return -1;
            }
        }
    }

}
