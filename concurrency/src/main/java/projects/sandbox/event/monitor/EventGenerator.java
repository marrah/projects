package projects.sandbox.event.monitor;

import java.io.*;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.sandbox.event.monitor.exceptions.EventMonitorException;
import projects.sandbox.event.monitor.model.SecurityEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * EventGenerator - Loop and create random security events. Place the JSON files in the
 * specified directory. There is no throttling on file creation.
 * 
 * @author Marrah
 * 
 */
public class EventGenerator implements Runnable {
    private static final Logger _logger = LoggerFactory.getLogger(EventGenerator.class);

    private Path _directory;
    
    /**
     * Constructor
     * 
     * @param directory the directory to create the event output the files in
     */
    public EventGenerator(final Path directory) {
        _directory = directory;
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        try {
            createEvents();
        } catch (EventMonitorException ex) {
            _logger.error("Failed to write event ", ex);
        }
    }
    
    /**
     * Create security events
     *   Utilize a random integer to decide what type of event to create
     *   
     * @throws EventMonitorException
     */
    public void createEvents() throws EventMonitorException {
        while(true) {
            int random = createRandomInteger();

            if ((random % 3) == 0) {
                writeJSONEvent(new SecurityEvent(SecurityEvent.DOOR, System.currentTimeMillis()));
            } else if (random % 2 == 0) {
                writeJSONEvent(new SecurityEvent(SecurityEvent.ALARM, System.currentTimeMillis()));
            } else {
                writeJSONEvent(new SecurityEvent(SecurityEvent.IMAGE, System.currentTimeMillis()));
            }
        }
    }
    
    private int createRandomInteger() {
        return 1 + (int) (Math.random() * ((25 - 1) + 1));
    }

    /**
     * Write the security event to a file
     * 
     * @param event the event to output to a file
     * @throws EventMonitorException
     */
    private void writeJSONEvent(final SecurityEvent event) throws
            EventMonitorException {

        try {
            File tmp = File.createTempFile(event.getType() + "_", SecurityEvent.FILE_EXTENSION, _directory.toFile());

            try (Writer writer = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8")) {
                Gson gson = new GsonBuilder().create();
                gson.toJson(event, writer);
            }
        } catch (IOException ex) {
            throw new EventMonitorException("Failed to write event: " + event, ex);
        }
    }
}
