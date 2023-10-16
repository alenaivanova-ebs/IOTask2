package com.project.counter.api;

import com.project.counter.exception.FileProcessException;
import com.project.counter.exception.WriteToFileException;

public interface FileProcessor {
    /**
     * Processes data from the given input stream, sending the result to the provided output stream.
     *
     * @param inputFile The source of data to be processed.
     * @param outputFile The output.
     * @throws FileProcessException, WriteToFileException Upon failure to read from the source or write to the sink.
     */
    void process(String inputFile, String outputFile )
            throws FileProcessException, WriteToFileException;

}
