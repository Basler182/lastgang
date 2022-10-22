package eu.schk.lastgang.util;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

public final class FilePath {

    private FilePath() {
        throw new IllegalStateException("Utility class");
    }

    public static String getFilePath(String fileName) throws URISyntaxException {
        return getFileFromResource(fileName).getAbsolutePath();
    }

    private static File getFileFromResource(String fileName) throws URISyntaxException {

        ClassLoader classLoader = FilePath.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {

            // failed if files have whitespaces or special characters
            //return new File(resource.getFile());

            return new File(resource.toURI());
        }

    }
}
