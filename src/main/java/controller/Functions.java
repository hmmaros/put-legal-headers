package controller;


import finals.Finals;
import logger.MyLogger;
import model.Model;

import javax.swing.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Functions {

    private Functions() {

    }

    private static Logger logger = MyLogger.myLogger();

    private static JFrame putLegalHeadersFrame = new JFrame();
    private static String directoryPath;
    private static String textToFind;
    private static String suffix;

    public static void handleDirectories() {
        List<String> foundedPaths = new ArrayList<>();

        logger.log(Level.INFO, "start handle directories");

        directoryPath = Model.getDirectoryPath();
        textToFind = Model.getTextToFind();
        suffix = Model.getSuffix();

        findFilesUsingCommandLine(foundedPaths);

        if (foundedPaths.isEmpty()){
            System.out.println("No files with these criterion found: ");
            System.out.println("Folder path to search: " + Model.getDirectoryPath());
            System.out.println("Suffixes: " + Model.getSuffix());
            System.out.println("Files " + Model.getWithOrWithoutText() + " this text: "+ Model.getTextToFind());
            System.exit(0);
        }
        else {
            displayResults(foundedPaths);
        }


        List<String> legaleHeaders = Model.getLegalHeaderText();

        if (legaleHeaders.isEmpty()){
            System.out.println("The input file is empty or there is an invalid character in this file!");
        }
        else {
            System.out.println("\nStarting adding Legal Headers...");
            for ( String path:foundedPaths ) {
                Functions.createNewFileWithNewTextAtTopAndOldBelow(path, legaleHeaders);
                System.out.println("Done: " + path);
            }
            System.out.println("\nLegal Headers are to " + foundedPaths.size() + " files added!");
        }
    }

    private static void findFilesUsingCommandLine(List<String> foundedPaths) {
        String commandToFindFilesWithoutText = null;
        try {
            if (Model.getWithOrWithoutText().equals("WITHOUT")){
                commandToFindFilesWithoutText = "@for /r %f in (" + suffix + ") do @find /i \"" + textToFind + "\" \"%f\" > nul || echo %f";
            }
            else if (Model.getWithOrWithoutText().equals("WITH")) {
                commandToFindFilesWithoutText = "@for /r %f in (" + suffix + ") do @find /i \"" + textToFind + "\" \"%f\" > nul && echo %f";

            }

            executeCommand(commandToFindFilesWithoutText, directoryPath, foundedPaths);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void executeCommand(String command, String path, List<String> foundedPaths) throws IOException {

        File filePath = new File(path);

        if (command == null){
            System.out.println("An error is occurred: command is null");
        }
        else {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("cmd.exe /K " + command, null, filePath);

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(proc.getInputStream()));

            // read the output from the command
            String si = null;
            while ((si = stdInput.readLine()) != null) {
                if (si.isEmpty()) {
                    break;
                }
                foundedPaths.add(si);
            }
        }


    }

    private static void displayResults(List<String> pathsWithText) {
        System.out.println("Files " + Model.getWithOrWithoutText() + " the given text ( " +Model.getTextToFind() + " ) in path " + Model.getDirectoryPath() + " are: ");
        for (String path:pathsWithText) {
            System.out.println(path);
        }
    }

    public static List<String> readTextFromFile(String filePath) {
        List<String> lines = new ArrayList<>();

        try(BufferedReader in = new BufferedReader( new InputStreamReader( new FileInputStream(filePath), "UTF-8"))){
            String str;

            while ((str = in.readLine()) != null) {
                // System.out.println(str);
                lines.add(str);
            }

            in.close();
        }
        catch (UnsupportedEncodingException e)
        {
            System.out.println(e.getMessage());
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return lines;
    }

    private static void writeToFileAndAppendText(List<String> lines, String pathNameOfTheFile) throws IOException {

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(pathNameOfTheFile, true), "UTF-8"));
        try {
            for (String line:lines) {
                out.write(line + "\n");
            }

        } finally {
            out.close();
        }
    }

    public static void createNewFileWithNewTextAtTopAndOldBelow(String pathOfOldFile, List<String> legaleHeaders) {

        List<String> lines = readTextFromFile(pathOfOldFile);

        try {

            writeToFileAndAppendText(lines, Finals.TEMP_FILE_NAME);
            deleteFileContent(pathOfOldFile);
            appendStartComments(pathOfOldFile, Finals.HTML_START_COMMENTS, Finals.COMMON_START_COMMENTS);
            writeToFileAndAppendText(legaleHeaders, pathOfOldFile);
            appendStartComments(pathOfOldFile, Finals.HTML_END_COMMENTS, Finals.COMMON_END_COMMENTS);
            writeToFileAndAppendText(lines, pathOfOldFile);
            deleteFileContent(Finals.TEMP_FILE_NAME);

            } catch (IOException e) {
            System.out.println("Cannot add text in all files");
                e.printStackTrace();
            }
    }

    private static void appendStartComments(String pathOfOldFile, List<String> htmlComments, List<String> commonComments) throws IOException {
        if (pathOfOldFile.endsWith(".html"))
        {
            writeToFileAndAppendText(htmlComments, pathOfOldFile);
        }
        else
        {
            writeToFileAndAppendText(commonComments, pathOfOldFile);
        }
    }

    private static void deleteFileContent(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        writer.print("");
        writer.close();
    }

    public static void createTxtFile(String fileName) {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(fileName), Charset.forName("UTF-8"))) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
