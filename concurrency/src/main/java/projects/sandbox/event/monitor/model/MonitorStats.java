package projects.sandbox.event.monitor.model;

import java.text.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MonitorStats - used to collect the security event statistics
 * 
 * @author Marrah
 * 
 */
public class MonitorStats {

    private int _doorCount = 0;
    private int _imageCount = 0;
    private int _alarmCount = 0;
    private long _totalProcessingTime = 0;

    private static final DateFormat _SIMPLE_FORMAT = new SimpleDateFormat(SecurityEvent.DATE_FORMAT);

    /**
     * Constructor
     */
    public MonitorStats() { }

    /**
     * Copy constructor
     * 
     * @param stats stats to copy
     */
    public MonitorStats(final MonitorStats stats) {
        _doorCount = stats.getDoorCount();
        _imageCount = stats.getImageCount();
        _alarmCount = stats.getAlarmCount();
        _totalProcessingTime = stats.getTotalProcessingTime();
    }

    /**
     * Increment the door count by 1
     */
    public void incrementDoorCount() {
        _doorCount++;
    }

    /**
     * Increment the alarm count by 1
     */
    public void incrementAlarmCount() {
        _alarmCount++;
    }

    /**
     * Increment the image count by 1
     */
    public void incrementImageCount() {
        _imageCount++;
    }

    /**
     * get the door count
     * 
     * @return the door count
     */
    public int getDoorCount() {
        return _doorCount;
    }

    /**
     * get the image count
     * 
     * @return the image count
     */
    public int getImageCount() {
        return _imageCount;
    }

    /**
     * get the alarm count
     * 
     * @return the alarm count
     */
    public int getAlarmCount() {
        return _alarmCount;
    }

    /**
     * get the processing time
     * 
     * @return the processing time
     */
    public long getTotalProcessingTime() {
        return _totalProcessingTime;
    }
    
    /**
     * calculate the processing time
     * 
     * @return the processing time
     */
    public long getAverageProcessingTime() {
        int totalCount = getAlarmCount() + getDoorCount() + getImageCount();
        return (totalCount == 0) ? 0 : _totalProcessingTime / totalCount;
    }

    /**
     * update the processing time 
     * 
     * @param eventDate the event time to add to the total processing time
     */
    public void updateProcessingTime(final String eventDate) {
        long now = System.currentTimeMillis();

        Date parsedDate;
        try {
            parsedDate = _SIMPLE_FORMAT.parse(eventDate);
            _totalProcessingTime += (now - parsedDate.getTime());
        } catch (ParseException e) {
            // ignore
        }
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DoorCnt: " + getDoorCount() + ", ImgCnt:" + getImageCount() + ", AlarmCnt:" + getAlarmCount()
                        + ", avgProcessingTime: " + getAverageProcessingTime() + "ms";
    }

}
