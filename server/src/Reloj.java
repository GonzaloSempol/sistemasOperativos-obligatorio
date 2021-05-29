
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gonzalo.sempol
 */
public class Reloj extends Clock{
    
     private ZonedDateTime fechaInicial;


    /**
     * Creates a new fast clock. It will use 'now' as the starting point.
     */
    public Reloj() {
        fechaInicial = ZonedDateTime.now();
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
        // Gets 'now'.
        ZonedDateTime now = ZonedDateTime.now();
        long nowSeconds = now.toInstant().getEpochSecond();
        long beginSeconds = fechaInicial.toInstant().getEpochSecond();
        // Difference between the starting point of this clock and 'now'.
        long delta = nowSeconds - beginSeconds;
        // Creates a datetime that is in the future.
        return fechaInicial.plusDays(delta).toInstant();
    }
    
}


