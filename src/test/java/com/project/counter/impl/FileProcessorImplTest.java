package com.project.counter.impl;

import com.project.counter.api.FileProcessor;
import com.project.counter.api.ProcessorFactory;
import com.project.counter.exception.FileProcessException;
import com.project.counter.exception.WriteToFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

final class FileProcessorImplTest {

    private static Stream<Arguments> testProcessInputProvider() {
        return Stream.of(
                Arguments.of("input_test_3.txt", "src/test/resources/output_actual_3.csv", "output_expected_3.csv"),
                Arguments.of("input_test_2.txt", "src/test/resources/output_actual_2.csv", "output_expected_2.csv")
        );
    }

    @ParameterizedTest
    @MethodSource("testProcessInputProvider")
    void testProcess(String inputFile, String actualOutputFile, String expectedOutputFile) throws WriteToFileException, FileProcessException, IOException, URISyntaxException {
        ProcessorFactory factory = new ProcessorFactoryImpl();
        FileProcessor processor = factory.createProcessor(inputFile);
        processor.process(inputFile, actualOutputFile);

        Reader readerExpected = loadResource(expectedOutputFile);
        Reader readerActual = loadResource(actualOutputFile);
        long lineNumber = filesCompareByLine(readerExpected, readerActual);
        Assertions.assertEquals(-1, lineNumber);
    }

    private Reader loadResource(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        Reader reader = null;
        InputStream inp= classloader.getResourceAsStream(fileName);
        InputStreamReader streamReader = new InputStreamReader(inp, StandardCharsets.UTF_8);
        reader = new BufferedReader(streamReader);

        return reader;
    }

    public static long filesCompareByLine(Reader readerExpected, Reader readerActual) throws IOException {
        try (BufferedReader bf1 = new BufferedReader(readerExpected);
             BufferedReader bf2 = new BufferedReader(readerActual)) {

            long lineNumber = 1;
            String line1 = "", line2 = "";
            while ((line1 = bf1.readLine()) != null) {
                line2 = bf2.readLine();
                if (line2 == null || !line1.equals(line2)) {
                    return lineNumber;
                }
                lineNumber++;
            }
            if (bf2.readLine() == null) {
                System.out.println("-1");
                return -1;
            } else {
                return lineNumber;
            }
        }
    }
}