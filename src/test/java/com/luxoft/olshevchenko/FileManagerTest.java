package com.luxoft.olshevchenko;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileManagerTest {
    private final String CopyFrom = "src/main/resources/CopyFrom";
    private final String CopyTo = "src/main/resources/CopyTo";

    @SneakyThrows
    @BeforeEach
    void before() {
        createDirectoryAndMessage(CopyFrom);
        createDirectoryAndMessage(CopyTo);
        createDirectoryAndMessage(CopyFrom + "/1");
        createDirectoryAndMessage(CopyFrom + "/1/2");
        createDirectoryAndMessage(CopyFrom + "/1/2/3");
        createDirectoryAndMessage(CopyFrom + "/4");
        createDirectoryAndMessage(CopyFrom + "/4/5");
        writeTxtFile(CopyFrom + "/1", "1");
        writeTxtFile(CopyFrom + "/1/2", "2");
        writeTxtFile(CopyFrom + "/1/2/3", "3");
        writeTxtFile(CopyFrom + "/4", "4");
        writeTxtFile(CopyFrom, "5");
        writeTxtFile(CopyFrom, "6");
        writeTxtFile(CopyFrom, "7");
        writeTxtFile(CopyFrom, "8");
    }

    @AfterEach
    void after() {
        FileManager.deleteAll(CopyFrom);
        FileManager.deleteAll(CopyTo);
        assertEquals(0, FileManager.countFiles(CopyTo));
        assertEquals(0, FileManager.countDirs(CopyTo));
        new File(CopyFrom).delete();
        new File(CopyTo).delete();
    }

    @SneakyThrows
    private static void writeTxtFile(String pathToFile, String content) {
        File file = new File(pathToFile + "/" + content + ".txt");
        createFileAndMessage(file);
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
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
        assertEquals(8, FileManager.countFiles(CopyFrom));
        assertEquals(0, FileManager.countFiles(CopyTo));
    }

    @Test
    @DisplayName("Test CountFiles method if path specified is invalid")
    void testCountFilesIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.countFiles(CopyFrom + "-"));
    }

    @Test
    @DisplayName("Test CountDirs method")
    void testCountDirs() {
        assertEquals(5, FileManager.countDirs(CopyFrom));
        assertEquals(0, FileManager.countDirs(CopyTo));
    }

    @Test
    @DisplayName("Test CountDirs method if path specified is invalid")
    void testCountDirsIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.countDirs(CopyFrom + "-"));
    }

    @Test
    @DisplayName("Test Copy method")
    void testCopy() {
        FileManager.copy(CopyFrom, CopyTo);
        assertEquals(8, FileManager.countFiles(CopyTo));
        assertEquals(5, FileManager.countDirs(CopyTo));
    }

    @Test
    @DisplayName("Test Copy method if path specified is invalid")
    void testCopyIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.copy(CopyFrom + "-", CopyTo));
    }

    @Test
    @DisplayName("Test Move method")
    void testMove() {
        FileManager.move(CopyFrom, CopyTo);
        assertEquals(8, FileManager.countFiles(CopyTo));
        assertEquals(5, FileManager.countDirs(CopyTo));
    }

    @Test
    @DisplayName("Test Move method if path specified is invalid")
    void testMoveIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.move(CopyFrom + "-", CopyTo));
    }


}