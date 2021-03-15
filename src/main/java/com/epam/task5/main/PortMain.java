package com.epam.task5.main;

import com.epam.task5.entity.Ship;
import com.epam.task5.entity.ShipAction;
import com.epam.task5.exception.MultithreadingException;
import com.epam.task5.parser.DataParser;
import com.epam.task5.reader.DataReader;

import java.util.List;

public class PortMain {
    private static final String FILE_PATH = "src/main/resources/data/data.txt";
    private static final String SHIP_NAME = "Ship";

    public static void main(String[] args) throws MultithreadingException {
        DataReader reader = new DataReader();
        List<String> readLines = reader.read(FILE_PATH);
        DataParser parser = new DataParser();
        for (String line : readLines) {
            List<Integer> numbers = parser.parseLineToNumberList(line);
            Ship ship = new Ship(numbers.get(0), numbers.get(1), ShipAction.values()[numbers.get(2)]);
            Thread thread = new Thread(ship);
            thread.setName(SHIP_NAME + ship.getShipId());
            thread.start();
        }
    }
}
