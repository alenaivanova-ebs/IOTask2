package com.project.counter.api;

import com.project.counter.exception.FileProcessException;
import com.project.counter.exception.WriteToFileException;

import java.util.Iterator;
import java.util.ServiceLoader;


public class Main {

    public static void main(String[] args) throws WriteToFileException, FileProcessException {
        if (args.length != 2) {
            System.err.printf("Usage:%n  java -jar  application.jar <inputFileName> <outputFileName> %n");
            System.exit(1);
        }

        String inputFileName = args[0];
        String outputFileName = args[1];
        process(inputFileName, outputFileName);
    }

    private static void process(String inputFileName, String outputFileName) throws WriteToFileException, FileProcessException {
        Iterator<ProcessorFactory> factories =
                ServiceLoader.load(ProcessorFactory.class).iterator();
        if (!factories.hasNext()) {
            throw new IllegalStateException("No ProcessorFactory found");
        }

        ProcessorFactory factory = factories.next();
        FileProcessor processor = factory.createProcessor(inputFileName);
        processor.process(inputFileName, outputFileName);
    }
}
