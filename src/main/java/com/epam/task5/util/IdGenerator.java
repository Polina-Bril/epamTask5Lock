package com.epam.task5.util;

public class IdGenerator {
    private static int SHIP_ID_COUNTER = 1;
    private static int PIER_ID_COUNTER = 1;

    public static long generatePierId(){
        long id = 0;
        id = PIER_ID_COUNTER++;
        return id;
    }
    public static long generateShipId(){
        long id = 0;
        id = SHIP_ID_COUNTER++;
        return id;
    }
}
