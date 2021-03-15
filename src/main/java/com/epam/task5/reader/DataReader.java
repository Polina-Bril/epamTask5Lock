package com.epam.task5.reader;

import com.epam.task5.exception.MultithreadingException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataReader {
    static Logger logger = LogManager.getLogger();

    public List<String> read(String fileName) throws MultithreadingException {
        Path path = Paths.get(fileName);
        List<String> lines;
        if (Files.notExists(path) || Files.isDirectory(path) || !Files.isReadable(path)) {
            throw new MultithreadingException("can't parse data because of data source exception: " + fileName);
        }
        try (Stream<String> stream = Files.lines(path)) {
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            throw new MultithreadingException("can't parse data because of IOException: " + e);
        }
        logger.log(Level.INFO, "read lines from file : " + lines);
        return lines;
    }
}
