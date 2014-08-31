package projects.sandbox.event.monitor;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import projects.sandbox.event.monitor.exceptions.EventMonitorException;
import projects.sandbox.event.monitor.model.MonitorStats;
import projects.sandbox.event.monitor.utils.DirectoryTestUtils;

/**
 * DirectoryMonitorTest - contains the test used to ensure the DirectoryMonitor
 *  successfully finds new events in the specified directory.
 *  
 * @author Marrah
 */
public class DirectoryMonitorTest {

    private static final Path _TEST_DIR = DirectoryTestUtils.getTargetDir(DirectoryMonitorTest.class.getSimpleName());
    
    @BeforeClass
    public static void setUp() throws IOException {
        DirectoryTestUtils.resetTestDirectory(_TEST_DIR);
    }

    @Test
    public void shouldMonitorDirectoryCorrectly() throws IOException, InterruptedException, EventMonitorException {
        
        EventGenerator generator = new EventGenerator(_TEST_DIR);
        Thread generatorTread = new Thread(generator);
        generatorTread.start();
        
        DirectoryMonitor monitor = new DirectoryMonitor(_TEST_DIR);
        Thread monitorThread = new Thread(monitor);
        monitorThread.start();

        List<Path> files;
        while((files = DirectoryTestUtils.fileList(_TEST_DIR)).size() < 5) {
            if(files.size() >= 5) {
                generatorTread.interrupt();
                generatorTread.join();
            }
        }        

        monitorThread.interrupt();
        monitorThread.join();
        MonitorStats stats = monitor.getStats();

        assertTrue((stats.getAlarmCount() + stats.getDoorCount() + stats.getImageCount()) > 0);
    }

}
