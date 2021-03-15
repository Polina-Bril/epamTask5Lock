package com.epam.task5.state;

import com.epam.task5.entity.Ship;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SailingState implements AbstractShipState {
    static Logger logger = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        logger.log(Level.INFO, "Ship № " + ship.getShipId() + " is arriving to port");
        ship.setShipState(new ArrivingState());
    }
}
