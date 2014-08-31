package projects.sandbox.event.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.sandbox.event.monitor.exceptions.EventMonitorException;
import projects.sandbox.event.monitor.model.MonitorStats;

/**
 * DisplayStats - output a status of the system at a specified cadence 
 *
 * @author Marrah
 */
public class DisplayStats {

    private static final Logger _logger = LoggerFactory.getLogger(DisplayStats.class);

    private DirectoryMonitor _monitor;

    /**
     * Constructor
     * 
     * @param monitor the directory monitor
     */
    public DisplayStats(final DirectoryMonitor monitor) {
        _monitor = monitor;
    }

    /**
     * Create a thread used to monitor the directory and maintain stats.
     *   Output those stats at the cadence specified and stop looping when
     *   the timeout is reached.
     * 
     * @param timeoutInSeconds the amount of time to run the display
     * @param intervalInSeconds the delay between statistic output
     * @throws EventMonitorException
     */
    public void start(final int timeoutInSeconds, final int intervalInSeconds) throws EventMonitorException {

        try {
            Thread monitorThread = new Thread(_monitor);
            monitorThread.start();

            long startTime = System.currentTimeMillis();
            while ((System.currentTimeMillis() - startTime) < timeoutInSeconds * 1000) {
                MonitorStats stats = _monitor.getStats();
                System.out.println(stats.toString());

                Thread.sleep(intervalInSeconds * 1000);
            }

            monitorThread.interrupt();
            monitorThread.join();
        } catch (InterruptedException ex) {
            _logger.warn("Monitoring thread interrupter", ex);
        }
    }

}
