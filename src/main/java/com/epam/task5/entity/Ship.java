package com.epam.task5.entity;

import com.epam.task5.exception.MultithreadingException;
import com.epam.task5.state.AbstractShipState;
import com.epam.task5.state.SailingState;
import com.epam.task5.util.IdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class Ship implements Runnable{
    static Logger logger = LogManager.getLogger();
    private long shipId;
    private int capacity;
    private int busyness;
    private ShipAction shipAction;
    private AbstractShipState shipState;
    private Optional<Pier> pier;

    public Ship(int capacity, int busyness, ShipAction shipAction) {
        shipId = IdGenerator.generateShipId();
        this.capacity = capacity;
        this.busyness = busyness;
        this.shipAction = shipAction;
        shipState = new SailingState();
    }

    public long getShipId() {
        return shipId;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBusyness() {
        return busyness;
    }

    public ShipAction getShipAction() {
        return shipAction;
    }

    public void setShipPurpose(ShipAction shipAction) {
        this.shipAction = shipAction;
    }

    public AbstractShipState getShipState() {
        return shipState;
    }

    public void setShipState(AbstractShipState shipState) {
        this.shipState = shipState;
    }

    public Optional<Pier> getPier() {
        return pier;
    }

    public void setPier(Optional<Pier> pier) {
        this.pier = pier;
    }

    public boolean isFull() {
        boolean result = busyness == capacity;
        if (result) {
            logger.log(Level.INFO, "Ship № " + shipId + "has no free space");
        }
        return result;
    }

    public boolean isEmpty() {
        boolean result;
        result = busyness == 0;
        if (result) {
            logger.log(Level.INFO, "Ship № " + shipId + "has no containers");
        }
        return result;
    }

    public void addContainer() {
        busyness++;
        logger.log(Level.INFO, "Container loaded to ship № " + shipId);
    }

    public void removeContainer() {
        busyness--;
        logger.log(Level.INFO, "Container unloaded from ship № " + shipId);
    }

    @Override
    public void run() {
        do {
            try {
                shipState.doAction(this);
            } catch (MultithreadingException e) {
                e.printStackTrace();
            }
        } while (shipState.getClass() != SailingState.class);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ship ship = (Ship) o;
        if (shipId != ship.shipId) {
            return false;
        }
        if (capacity != ship.capacity) {
            return false;
        }
        if (busyness != ship.busyness) {
            return false;
        }
        if (shipAction != ship.shipAction) {
            return false;
        }
        if (shipState == null) {
            if (ship.shipState != null) {
                return false;
            }
        } else if (!shipState.equals(ship.shipState)) {
            return false;
        }
        if (pier.isEmpty()) {
            if (ship.pier.isPresent()) {
                return false;
            }
        } else if (!pier.equals(ship.pier)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = (int) shipId;
        result = prime * result + capacity;
        result = prime * result + busyness;
        result = prime * result + (shipAction != null ? shipAction.hashCode() : 0);
        result = prime * result + (shipState != null ? shipState.hashCode() : 0);
        result = prime * result + (pier.isPresent() ? pier.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ship{");
        sb.append("shipId=").append(shipId);
        sb.append(", capacity=").append(capacity);
        sb.append(", busyness=").append(busyness);
        sb.append(", ShipAction=").append(shipAction);
        sb.append(", shipState=").append(shipState.getClass().getSimpleName());
        sb.append(", pier=").append(pier);
        sb.append('}');
        return sb.toString();
    }
}