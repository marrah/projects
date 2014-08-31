# Concurrency example

Monitor a specified directory for incoming security events. Three threads are used to do this: one thread monitors the
directory for new events, another is used to print the collected data to the display and the last is used to generate
the events. Three types of security events are tracked: door, image and alarm. The average time to process each event is
calculated based on time time between when the event was generated to when it was processed. The output is printed at
a 1 second cadence.

## classes
* EventGenerator - creates random events and outputs them to JSON files. There is no throttling on file creation.
* DirectoryMonitor - uses Java's WatchService to monitor a directory for new files and collect stats
* DisplayStats - loop at 1 second intervals and display the system stats
* DisplayStatsTest - used to run the entire system. By default it is set to output system stats at 1 second intervals for 60 seconds.

## Requirements
* Java7
* common-io
* gson
* slf4j
* junit


## Output
DoorCnt: 0, ImgCnt:0, AlarmCnt:0, avgProcessingTime: 0ms
DoorCnt: 1487, ImgCnt:1599, AlarmCnt:1389, avgProcessingTime: 580ms
DoorCnt: 3872, ImgCnt:4180, AlarmCnt:3761, avgProcessingTime: 528ms
DoorCnt: 6929, ImgCnt:7698, AlarmCnt:6881, avgProcessingTime: 533ms
...
DoorCnt: 192479, ImgCnt:216251, AlarmCnt:191928, avgProcessingTime: 504ms
DoorCnt: 194946, ImgCnt:219090, AlarmCnt:194386, avgProcessingTime: 503ms
DoorCnt: 197838, ImgCnt:222334, AlarmCnt:197205, avgProcessingTime: 503ms
