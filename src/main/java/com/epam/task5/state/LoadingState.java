package com.epam.task5.state;

import com.epam.task5.entity.Pier;
import com.epam.task5.entity.Port;
import com.epam.task5.entity.Ship;
import com.epam.task5.exception.MultithreadingException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoadingState implements AbstractShipState{
    static Logger logger = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) throws MultithreadingException {
        Port port = Port.getInstance();
        Optional<Pier> pier = ship.getPier();
        if (pier.isPresent()) {
            port.loadShip(ship);
            logger.log(Level.INFO,"Ship № " + ship.getShipId() + " loaded");
        }
        ship.setShipState(new DepartingState());
    }
}
