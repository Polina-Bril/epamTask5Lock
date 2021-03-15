package com.epam.task5.state;

import com.epam.task5.entity.Pier;
import com.epam.task5.entity.Port;
import com.epam.task5.entity.Ship;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class DepartingState implements AbstractShipState{
    static Logger logger = LogManager.getLogger();

    @Override
    public void doAction(Ship ship) {
        Port port = Port.getInstance();
        Optional<Pier> pier = ship.getPier();
        if (pier.isPresent()) {
            port.releasePier(pier.get());
            ship.setPier(Optional.empty());
            logger.log(Level.INFO, "Ship № " + ship.getShipId() + " depart from port");
        }
        ship.setShipState(new SailingState());
    }
}
