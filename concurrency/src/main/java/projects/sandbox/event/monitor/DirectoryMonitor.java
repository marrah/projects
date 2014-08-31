package projects.sandbox.event.monitor;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.WatchEvent.Kind;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.sandbox.event.monitor.exceptions.EventMonitorException;
import projects.sandbox.event.monitor.model.MonitorStats;
import projects.sandbox.event.monitor.model.SecurityEvent;

/**
 * DirectoryMonitor - monitor a folder for security events (new files) and 
 *      process files as soon as possible. Keep track of the security events statistics
 * 
 * @author Marrah
 * 
 */
public class DirectoryMonitor implements Runnable {

    private static final Logger _logger = LoggerFactory.getLogger(DirectoryMonitor.class);

    private Path _directory;
    private WatchService _watchService;
    private MonitorStats _stats;

    /**
     * Constructor - The watch service is set to only trigger on file creation
     * 
     * @param directoryToMonitor the directory to monitor
     * @throws EventMonitorException
     */
    public DirectoryMonitor(final Path directoryToMonitor) throws EventMonitorException {
        _directory = directoryToMonitor;
        _stats = new MonitorStats();

        try {
            _watchService = FileSystems.getDefault().newWatchService();
            _directory.register(_watchService, StandardWatchEventKinds.ENTRY_CREATE);
        } catch (IOException ex) {
            throw new EventMonitorException("Unable to monitor directory specified " + _directory.toAbsolutePath(), ex);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        try {
            startMonitor();
        } catch (IOException e) {
            _logger.warn("Failed to read event", e);
        } catch (InterruptedException e) {
            // ignore
        }
    }

    /**
     * Start monitoring the directory for file creations. When a new file is
     * found parse it and update the collected stats.
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    private void startMonitor() throws IOException, InterruptedException {

        while (true) {

            WatchKey key = _watchService.take();

            Kind<?> kind;
            for (WatchEvent<?> event : key.pollEvents()) {
                kind = event.kind();

                if(StandardWatchEventKinds.ENTRY_CREATE == kind) {
                    Path newPath = ((WatchEvent<Path>) event).context();
                    Path child = _directory.resolve(newPath);

                    SecurityEvent securityEvent;

                    while ((securityEvent = SecurityEvent.readJSON(child)) == null) {
                        Thread.sleep(2);
                    }

                    logSecurityEvent(securityEvent);
                }
            }

            if(!key.reset()) {
                break;
            }
        }
    }

    /**
     * get the current monitor stats
     * 
     * @return a copy of the current monitor stats
     */
    public synchronized MonitorStats getStats() {
        return new MonitorStats(_stats);
    }

    /**
     * Log the security event. Increment the proper counter and update the
     *   processing time
     * 
     * @param securityEvent the security event to log
     */
    private synchronized void logSecurityEvent(final SecurityEvent securityEvent) {
        if(securityEvent.isAlarm()) {
            _stats.incrementAlarmCount();
        } else if(securityEvent.isDoor()) {
            _stats.incrementDoorCount();
        } else if(securityEvent.isImage()) {
            _stats.incrementImageCount();
        }
        
        _stats.updateProcessingTime(securityEvent.getDate());
    }
}
