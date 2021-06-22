/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gonzalo.sempol
 */
public class Registro {
    
    private int deRiesgo;
    private int[] personasPorRangos;
    private int cantDosis1;
    private int cantDosis2;
    
    
    
    
    public Registro() {
        this.deRiesgo = 0;
        this.personasPorRangos = new int[] {0,0,0,0,0,0};
        this.cantDosis1 = 0;
        this.cantDosis2 = 0;
       
    }

    public int getDeRiesgo() {
        return deRiesgo;
    }

    public void addDeRiesgo() {
        this.deRiesgo ++;
    }

    public int[] getPersonasPorRangos() {
        return personasPorRangos;
    }

    public void addPersonaPorRango(int rango) {
        this.personasPorRangos[rango] ++;
    }

    public int getCantDosis1() {
        return cantDosis1;
    }

    public void addCantDosis1() {
        this.cantDosis1 ++;
    }

    public int getCantDosis2() {
        return cantDosis2;
    }

    public void addCantDosis2() {
        this.cantDosis2 ++;
    }
    
    
    
    
    
}
