
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class Reloj extends Clock{
    
     private ZonedDateTime fechaInicial;
     private ZonedDateTime fechaCovid;


   
    public Reloj() {
        fechaInicial = ZonedDateTime.now();
        fechaCovid = ZonedDateTime.parse("2021-02-02T00:00:00.00-03:00[Asia/Calcutta]"); //Fecha de comienzo de la simulación
    }


    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }


    @Override
    public Clock withZone(ZoneId zone) {
        return null;
    }


    @Override
    public Instant instant() {
        ZonedDateTime now = ZonedDateTime.now();
        long nowSeconds = now.toInstant().getEpochSecond();
        long beginSeconds = fechaInicial.toInstant().getEpochSecond();
        // Diferencia entre inicio y el ahora
        long delta = nowSeconds - beginSeconds;
        //Crea un datetime 
        return fechaCovid.plusDays(delta).toInstant();
    }
    
}


