package projects.sandbox.event.monitor.utils;


import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DirectoryTestUtils - contains a collection of methods used to get or
 * manipulate test directories.
 *
 * @author Marrah
 *
 */
public class DirectoryTestUtils {

    /**
     * Get the target directory for the test output
     *
     * @param className the class name
     * @return the test output directory
     */
    public static Path getTargetDir(final String className) {
        return Paths.get(System.getProperty("user.dir"), "target", className);
    }

    /**
     * Get the test resource directory
     *
     * @return the test resource directory
     */
    public static Path getTestResourceDir() {
        return Paths.get(System.getProperty("user.dir"), "src", "test", "resources");
    }

    /**
     * Delete the specified directory and recreate it
     *
     * @param directory the directory to reset
     * @throws IOException
     */
    public static void resetTestDirectory(final Path directory) throws IOException {
        if(Files.exists(directory)) {
            FileUtils.deleteDirectory(directory.toFile());
        }
        Files.createDirectories(directory);
    }

    /**
     * Create a list of the files within a specified path
     *
     * @param directory the path to check
     * @return the list of files
     * @throws IOException
     */
    public static List<Path> fileList(final Path directory) throws IOException {
        List<Path> fileNames = new ArrayList<>();
        try(DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path path : directoryStream) {
                fileNames.add(path);
            }
        }
        return fileNames;
    }

}

