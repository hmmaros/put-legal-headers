package controller;

import finals.Finals;
import logger.MyLogger;
import model.Model;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.List;
import java.util.logging.Logger;

public class Controller {

    private static Logger logger = MyLogger.myLogger();

    public Controller() {

        checkIfFileExistsAndNotEmpty();
        readJsonData();

        Functions.createTxtFile(Finals.TEMP_FILE_NAME);

        Functions.handleDirectories();

    }

    private static void checkIfFileExistsAndNotEmpty() {
        List<String> inputFileText = Functions.readTextFromFile(Finals.INPUT_FILE_NAME);

        if (inputFileText.isEmpty()) {
            Functions.createTxtFile(Finals.INPUT_FILE_NAME);
            System.out.println("\nThe " + Finals.INPUT_FILE_NAME + " is empty or doesnt exist.\n A file with this name is now created in the root folder(where .jar file is).\n Please add the legal header text in this file!\n");
            System.exit(0);
        }

        Model.setLegalHeaderText(inputFileText);
    }

    private static void readJsonData() {
        JSONParser parser = new JSONParser();


        try {

            Object obj = parser.parse(new FileReader("config.json"));

            JSONObject jsonObject = (JSONObject) obj;

            String directoryPath = (String) jsonObject.get("directoryPath");
            String textToFind = (String) jsonObject.get("textToFind");
            String suffix = (String) jsonObject.get("suffix");
            String withOrWithoutText = (String) jsonObject.get("withOrWithoutText");

            Model.setDirectoryPath(directoryPath);
            // text to be found
            Model.setTextToFind(textToFind);
            Model.setSuffix(suffix);
            Model.setWithOrWithoutText(withOrWithoutText);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
