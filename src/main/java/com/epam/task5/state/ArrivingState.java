package com.epam.task5.state;

import com.epam.task5.entity.Pier;
import com.epam.task5.entity.Port;
import com.epam.task5.entity.Ship;
import com.epam.task5.exception.MultithreadingException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class ArrivingState implements AbstractShipState {
    static Logger logger = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) throws MultithreadingException {
        Port port = Port.getInstance();
        Pier pier = port.getPier();
        ship.setPier(Optional.of(pier));
        switch (ship.getShipAction()) {
            case LOADING -> {
                logger.log(Level.INFO, "Ship № " + ship.getShipId() + " arrived to port to pier № " +
                        pier.getId() + " for loading");
                ship.setShipState(new LoadingState());
            }
            case UNLOADING -> {
                logger.log(Level.INFO, "Ship № " + ship.getShipId() + " arrived to port to pier № " +
                        pier.getId() + " for unloading");
                ship.setShipState(new UnloadingState());
            }
            case UNLOADING_LOADING -> {
                logger.log(Level.INFO, "Ship № " + ship.getShipId() + " arrived to port to pier № " +
                        pier.getId() + " for unloading and loading");
                ship.setShipState(new UnloadingState());
            }
        }
    }
}
