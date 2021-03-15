package com.epam.task5.state;

import com.epam.task5.entity.Ship;
import com.epam.task5.exception.MultithreadingException;

public interface AbstractShipState {
    void doAction(Ship ship) throws MultithreadingException;
}
