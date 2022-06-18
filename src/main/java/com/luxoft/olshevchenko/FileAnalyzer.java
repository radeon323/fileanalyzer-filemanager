package com.luxoft.olshevchenko;

import java.io.*;
import java.util.*;

/**
 * @author Oleksandr Shevchenko
 */
public class FileAnalyzer {

    public static void main(String[] args) {
        String word = args[0];
        String path = args[1];
        int wordEntryCount = new FileAnalyzer().findWordEntryCount(path, word);
        int sentencesWithGivenWordCount = new FileAnalyzer().findSentencesWithGivenWord(path, word);
        System.out.println();
        System.out.println("\u001B[34m" + "Word '" + word + "' in this text occurs " + wordEntryCount + " times." + "\u001B[0m");
        System.out.println("\u001B[33m" + "The number of sentences with word '" + word + "' is " + sentencesWithGivenWordCount + "." + "\u001B[0m");
    }

    public int findWordEntryCount(String path, String word) {
        String regex = "\\b";
        List<String> contentList = getListFromFileContent(path, regex);
        long count = contentList.stream().filter((c) -> c.equals(word)).count();
        return (int) count;
    }

    public int findSentencesWithGivenWord(String path, String word) {
        String regex = "[\\.\\!\\?]";
        List<String> contentList = getListFromFileContent(path, regex);
        long count = contentList.stream().filter((c) -> c.contains(word)).count();
        List<String> sentences = contentList.stream().filter((c) -> c.contains(word)).toList();
        for (String sentence : sentences) {
            System.out.println(sentence);
        }
        return (int) count;
    }

    private List<String> getListFromFileContent(String path, String regex) {
        File file = new File(path);
        FileManager.pathValidation(file);
        try (InputStream inputStream = new FileInputStream(file)) {
            int length = (int) file.length();
            byte [] array = new byte[length];
            inputStream.read(array);
            String content = new String(array).toLowerCase();
            return List.of(content.split(regex));
        }  catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
