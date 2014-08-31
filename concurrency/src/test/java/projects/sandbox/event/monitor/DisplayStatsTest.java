package projects.sandbox.event.monitor;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.BeforeClass;
import org.junit.Test;

import projects.sandbox.event.monitor.exceptions.EventMonitorException;
import projects.sandbox.event.monitor.utils.DirectoryTestUtils;

/**
 * DisplayLogTest - contains the test used to test all the parts of the system.
 *     It creates a thread to generate the events and it starts the output display.
 *     By default the program will print out statistics every second and 
 *     terminate in 60 seconds.
 * 
 * @author Marrah
 */
public class DisplayStatsTest {

    private static final int _TIMEOUT_SECONDS = 60;
    private static final int _INTERVAL_SECONDS = 1;

    private static final Path _TEST_DIR = DirectoryTestUtils.getTargetDir(DisplayStatsTest.class.getSimpleName());

    @BeforeClass
    public static void setUp() throws IOException {
        DirectoryTestUtils.resetTestDirectory(_TEST_DIR);
    }

    @Test
    public void monitorDirectoryAndDisplayOutput() throws IOException, InterruptedException, EventMonitorException {
        EventGenerator events = new EventGenerator(_TEST_DIR);
        Thread eventsThread = new Thread(events);
        eventsThread.start();

        DirectoryMonitor monitor = new DirectoryMonitor(_TEST_DIR);
        DisplayStats display = new DisplayStats(monitor);
        display.start(_TIMEOUT_SECONDS, _INTERVAL_SECONDS);

        eventsThread.interrupt();
    }

}
