public class HiloVacunador implements Runnable {
    private String ciVacunador;
    private String ciVacunado;
    
    public HiloVacunador(String c, String c2) {
        this.ciVacunador = c;
        this.ciVacunado = c2;
    }
    
    @Override
    public void run() {
    }
}
