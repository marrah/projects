package projects.sandbox.event.monitor;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import projects.sandbox.event.monitor.model.SecurityEvent;
import projects.sandbox.event.monitor.utils.DirectoryTestUtils;

/**
 * EventGeneratorTest - contains the test used to ensure the EventGenerator
 *  is generating json event files.
 *  
 * @author Marrah
 * 
 */
public class EventGeneratorTest {

    private static final Path _TEST_DIR = DirectoryTestUtils.getTargetDir(EventGeneratorTest.class.getSimpleName());

    @BeforeClass
    public static void setUp() throws IOException {
        DirectoryTestUtils.resetTestDirectory(_TEST_DIR);
    }

    @Test
    public void shouldCreateRandomEventFilesSuccessfully() throws IOException, InterruptedException {
        EventGenerator generator = new EventGenerator(_TEST_DIR);
        
        Thread worker = new Thread(generator);
        worker.start();
        
        List<Path> files;
        while((files = DirectoryTestUtils.fileList(_TEST_DIR)).isEmpty()) {
            if(!files.isEmpty()) {
                worker.interrupt();
            }
        }
       
        assertNotNull(files);
        assertTrue(files.size() > 0);

        for (Path file : files) {
            assertTrue(file.toString().contains(SecurityEvent.FILE_EXTENSION));
        }
    }
}
