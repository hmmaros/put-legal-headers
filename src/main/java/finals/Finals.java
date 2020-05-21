package finals;

import java.util.Arrays;
import java.util.List;

public class Finals {

    private Finals() {
        // boom
    }

    public final static String INPUT_FILE_NAME = "inputText.txt";
    public final static String TEMP_FILE_NAME = "tempFile.txt";

    public final static List<String> HTML_START_COMMENTS = Arrays.asList("<!--");
    public final static List<String> HTML_END_COMMENTS = Arrays.asList("-->");
    public final static List<String> COMMON_START_COMMENTS = Arrays.asList("/*");
    public final static List<String> COMMON_END_COMMENTS = Arrays.asList("*/");


}
