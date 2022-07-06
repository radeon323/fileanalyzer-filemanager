package com.luxoft.olshevchenko;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerTest {
    private static final String COPY_FROM = "src/test/resources/CopyFrom";
    private static final String COPY_TO = "src/test/resources/CopyTo";

    @SneakyThrows
    @BeforeEach
    void before() {
        createDirectoryAndMessage(COPY_FROM);
        createDirectoryAndMessage(COPY_TO);
        createDirectoryAndMessage(COPY_FROM + "/1");
        createDirectoryAndMessage(COPY_FROM + "/1/2");
        createDirectoryAndMessage(COPY_FROM + "/1/2/3");
        createDirectoryAndMessage(COPY_FROM + "/4");
        createDirectoryAndMessage(COPY_FROM + "/4/5");
        writeTxtFile(COPY_FROM + "/1", "1");
        writeTxtFile(COPY_FROM + "/1/2", "2");
        writeTxtFile(COPY_FROM + "/1/2/3", "3");
        writeTxtFile(COPY_FROM + "/4", "4");
        writeTxtFile(COPY_FROM, "5");
        writeTxtFile(COPY_FROM, "6");
        writeTxtFile(COPY_FROM, "7");
        writeTxtFile(COPY_FROM, "8");
    }

    @AfterEach
    void after() {
        FileManager.deleteAll(COPY_FROM);
        FileManager.deleteAll(COPY_TO);
        assertEquals(0, FileManager.countFiles(COPY_TO));
        assertEquals(0, FileManager.countDirs(COPY_TO));
        new File(COPY_FROM).delete();
        new File(COPY_TO).delete();
    }

    private static void writeTxtFile(String pathToFile, String content) {
        File file = new File(pathToFile + "/" + content + ".txt");
        createFileAndMessage(file);
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file " + file + " .");
        }
    }

    @SneakyThrows
    private static void createFileAndMessage(File file) {
        if (file.createNewFile()) {
            System.out.println("File " + file.getName() + " is created");
        }
    }

    private static void createDirectoryAndMessage(String path) {
        File file = new File(path);
        if (file.mkdir()) {
            System.out.println("Directory [" + file.getName() + "] is created");
        }
    }

    @Test
    @DisplayName("Test CountFiles method")
    void testCountFiles() {
        assertEquals(8, FileManager.countFiles(COPY_FROM));
        assertEquals(0, FileManager.countFiles(COPY_TO));
    }

    @Test
    @DisplayName("Test CountFiles method if path specified is invalid")
    void testCountFilesIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.countFiles(COPY_FROM + "-"));
    }

    @Test
    @DisplayName("Test CountDirs method")
    void testCountDirs() {
        assertEquals(5, FileManager.countDirs(COPY_FROM));
        assertEquals(0, FileManager.countDirs(COPY_TO));
    }

    @Test
    @DisplayName("Test CountDirs method if path specified is invalid")
    void testCountDirsIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.countDirs(COPY_FROM + "-"));
    }

    @Test
    @DisplayName("Test Copy method")
    void testCopy() {
        FileManager.copy(COPY_FROM, COPY_TO);
        assertEquals(8, FileManager.countFiles(COPY_TO));
        assertEquals(5, FileManager.countDirs(COPY_TO));
    }

    @Test
    @DisplayName("Test Copy method if path specified is invalid")
    void testCopyIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.copy(COPY_FROM + "-", COPY_TO));
    }

    @Test
    @DisplayName("Test Move method")
    void testMove() {
        FileManager.move(COPY_FROM, COPY_TO);
        assertEquals(8, FileManager.countFiles(COPY_TO));
        assertEquals(5, FileManager.countDirs(COPY_TO));
    }

    @Test
    @DisplayName("Test Move method if path specified is invalid")
    void testMoveIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.move(COPY_FROM + "-", COPY_TO));
    }


}