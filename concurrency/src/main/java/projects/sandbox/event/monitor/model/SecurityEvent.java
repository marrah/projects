package projects.sandbox.event.monitor.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * SecurityEvent - A POJO used to contain the JSON event data.
 *
 * @author mmcclell
 *
 */
public class SecurityEvent {
    
    public static final String DOOR = "door";
    public static final String ALARM = "alarm";
    public static final String IMAGE = "img";
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String FILE_EXTENSION = ".json";

    private static final DateFormat _SIMPLE_DATE = new SimpleDateFormat(DATE_FORMAT);

    private String Type;
    private String Date;

    /**
     * Constructor
     * 
     * @param eventName the name of the event
     * @param currentTimeMillis the creation time of the event
     */
    public SecurityEvent(final String eventName, final long currentTimeMillis) {
    	Type = eventName; 
    	Date = _SIMPLE_DATE.format(currentTimeMillis);
	}

	/**
	 * get the event type
	 * 
	 * @return the event type
	 */
	public String getType() {
    	return Type;
    }
    
    /**
     * get the event date
     * 
     * @return the event date
     */
    public String getDate() {
    	return Date;
    }
    
    /**
     * is the event an alarm?
     * 
     * @return true if the event is an alarm, false otherwise
     */
    public boolean isAlarm() {
    	return isType(ALARM);
    }
    
    /**
     * is the event a door event? 
     * 
     * @return true if the event is a door event, false otherwise
     */   
    public boolean isDoor() {
    	return isType(DOOR);
    }
    
    /**
     * is the event an image?
     * 
     * @return true if the event is an image, false otherwise
     */
    public boolean isImage() {
    	return isType(IMAGE);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return Type + " - " + Date;
    }

    /**
     /**
     * Parse the JSON event file into a SecurityEvent object
     *
     * @param fileName the JSON file to parse
     * @return the SecurityEvent the security event found in the JSON file
     * @throws java.io.IOException
     */
    public static SecurityEvent readJSON(Path fileName) throws IOException {
        SecurityEvent event;
        try (Reader reader = new FileReader(fileName.toFile())) {
            Gson gson = new GsonBuilder().create();
            event = gson.fromJson(reader, SecurityEvent.class);
        }

        return event;
    }

    /**
     * does the event match the type specified?
     * 
     * @param type the type to match against
     * @return true if the types match
     */
    private boolean isType(String type) {
        return Type.equals(type);
    }

}
