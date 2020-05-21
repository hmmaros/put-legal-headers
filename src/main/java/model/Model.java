package model;

import java.util.List;

public class Model {

    private static String directoryPath;

    private static String textToFind;
    private static String thesuffix;
    private static String withOrWithoutText;
    private static List<String> theLegalHeaderText;

    public static String getWithOrWithoutText() {
        return withOrWithoutText;
    }

    public static void setWithOrWithoutText(String withOrWithout) {
        withOrWithoutText = withOrWithout;
    }

    public static String getTextToFind() {
        return textToFind;
    }

    public static void setTextToFind(String text) {
        textToFind = text;
    }

    public static String getSuffix() {
        return thesuffix;
    }

    public static void setSuffix(String suffix) {
        thesuffix = suffix;
    }

    public static List<String> getLegalHeaderText() {
        return theLegalHeaderText;
    }

    public static void setLegalHeaderText(List<String> legalHeaderText) {
        theLegalHeaderText = legalHeaderText;
    }

    public static String getDirectoryPath() {
        return directoryPath;
    }

    public static void setDirectoryPath(String tomcatpath) {
        directoryPath = tomcatpath;
    }

}
