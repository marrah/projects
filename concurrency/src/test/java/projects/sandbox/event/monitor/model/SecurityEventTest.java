package projects.sandbox.event.monitor.model;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Paths;

import org.junit.Test;

import projects.sandbox.event.monitor.utils.DirectoryTestUtils;

/**
 * SecurityEventTest - contains a set of tests to ensure the json files are being 
 * read in correctly. 
 * 
 * @author Marrah
 * 
 */
public class SecurityEventTest {

    @Test
    public void shouldReadDoorJSONCorrectly() throws IOException {
        SecurityEvent event = SecurityEvent.readJSON(Paths.get(DirectoryTestUtils.getTestResourceDir() + File.separator + "door.json"));
        assertEquals(event.getType(), "door");
        assertEquals(event.getDate(), "2014-08-26 12:27:06");
        assertFalse(event.isAlarm());
    }

    @Test
    public void shouldReadAlarmJSONCorrectly() throws IOException {
        SecurityEvent event = SecurityEvent.readJSON(Paths.get(DirectoryTestUtils.getTestResourceDir() + File.separator + "alarm.json"));
        assertEquals(event.getType(), "alarm");
        assertEquals(event.getDate(), "2014-08-26 12:27:06");
        assertTrue(event.isAlarm());
    }

}
