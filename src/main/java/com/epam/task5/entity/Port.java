package com.epam.task5.entity;

import com.epam.task5.exception.MultithreadingException;

import com.epam.task5.util.IdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Port {
    static Logger logger = LogManager.getLogger();
    private static Port instance;
    private static final int PIERS = 4;
    private static final int CAPACITY = 32;
    private static Lock lock = new ReentrantLock();
    private AtomicInteger fullness;
    private List<Pier> freePiers = new ArrayList<>();
    private List<Pier> busyPiers = new ArrayList<>();
    private Lock pierLock = new ReentrantLock();
    private Condition pierCondition = pierLock.newCondition();
    private Lock portLock = new ReentrantLock();

    private Port() {
        fullness = new AtomicInteger();
        for (int i = 0; i < PIERS; i++) {
            Pier pier = new Pier(IdGenerator.generatePierId());
            freePiers.add(pier);
        }
    }

    public static int getPiers() {
        return PIERS;
    }

    public int getCapacity() {
        return CAPACITY;
    }

    public AtomicInteger getFullness() {
        return fullness;
    }

    public List<Pier> getFreePiers() {
        return Collections.unmodifiableList(freePiers);
    }

    public List<Pier> getBusyPiers() {
        return Collections.unmodifiableList(busyPiers);
    }

    public static Port getInstance() {
        if (instance == null) {
            try {
                lock.lock();
                if (instance == null) {
                    instance = new Port();
                    logger.log(Level.INFO, "Port created");
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Pier getPier() throws MultithreadingException {
        try {
            pierLock.lock();
            while (freePiers.isEmpty()) {
                try {
                    pierCondition.await();
                } catch (InterruptedException e) {
                    throw new MultithreadingException("Thread interrupted", e);
                }
            }
            Optional<Pier> pierOptional = freePiers.stream().findAny();
            Pier pier = pierOptional.get();
            freePiers.remove(pier);
            busyPiers.add(pier);
            return pier;
        } finally {
            pierLock.unlock();
        }
    }

    public void releasePier(Pier pier) {
        try {
            pierLock.lock();
            if (busyPiers.remove(pier)) {
                freePiers.add(pier);
            }
        } finally {
            pierCondition.signal();
            pierLock.unlock();
        }
    }

    public void loadShip(Ship ship) throws MultithreadingException {
        logger.log(Level.INFO, "Ship № " + ship.getShipId() + " is loading");
        while (!ship.isFull()) {
            try {
                portLock.lock();
                if (fullness.get() > 0) {
                    ship.addContainer();
                    fullness.decrementAndGet();
                }
                if (fullness.get() == 0) {
                    logger.log(Level.INFO, "Port is empty");
                    break;
                }
            } finally {
                portLock.unlock();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new MultithreadingException("Thread interrupted", e);
            }
        }
    }

    public void unloadShip(Ship ship) throws MultithreadingException {
        logger.log(Level.INFO, "Ship № " + ship.getShipId() + " is unloading");
        while (!ship.isEmpty()) {
            try {
                portLock.lock();
                if (fullness.get() < CAPACITY) {
                    ship.removeContainer();
                    fullness.incrementAndGet();
                }
                if (fullness.get() == CAPACITY) {
                    logger.log(Level.INFO, "Port is totally full");
                    break;
                }
            } finally {
                portLock.unlock();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new MultithreadingException("Thread interrupted", e);
            }
        }
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Port{");
        sb.append("fullness=").append(fullness);
        sb.append(", freePiers=").append(freePiers);
        sb.append(", busyPiers=").append(busyPiers);
        sb.append('}');
        return sb.toString();
    }
}