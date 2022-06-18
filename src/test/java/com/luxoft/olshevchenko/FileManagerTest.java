package com.luxoft.olshevchenko;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    private final String CopyFrom = "src/main/resources/FileManagerTest/CopyFrom";
    private final String CopyTo = "src/main/resources/FileManagerTest/CopyTo";
    private final String MoveFrom = "src/main/resources/FileManagerTest/MoveFrom";

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
        FileManager.deleteAll(CopyTo);
        assertEquals(0, FileManager.countFiles(CopyTo));
        assertEquals(0, FileManager.countDirs(CopyTo));
    }

    @Test
    @DisplayName("Test Copy method if path specified is invalid")
    void testCopyIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.copy(CopyFrom + "-", CopyTo));
    }

    @Test
    @DisplayName("Test Move method")
    void testMove() {
        FileManager.move(MoveFrom, CopyTo);
        assertEquals(8, FileManager.countFiles(CopyTo));
        assertEquals(5, FileManager.countDirs(CopyTo));
        FileManager.deleteAll(CopyTo);
        assertEquals(0, FileManager.countFiles(CopyTo));
        assertEquals(0, FileManager.countDirs(CopyTo));
        FileManager.copy(CopyFrom, MoveFrom);
        assertEquals(8, FileManager.countFiles(MoveFrom));
        assertEquals(5, FileManager.countDirs(MoveFrom));
    }

    @Test
    @DisplayName("Test Move method if path specified is invalid")
    void testMoveIfInvalidPath() {
        Assertions.assertThrows(RuntimeException.class, () -> FileManager.move(MoveFrom + "-", CopyTo));
    }


}