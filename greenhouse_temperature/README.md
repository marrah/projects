# Temperature logger

This code is used to log data retrieved from the serial port. You will need to modify the serial port configuration
to match your system. Current settings are for windows and data sent to the serial port via an arduino.

* temperature_log - loops forever and logs the data to a file
* ardunio_reader - a class used to initialize the serial port connection and read data as it becomes available

This code was written to log the temperatures in my greenhouse. The windows open and close automatically based on the
temperature and the temperature data is used to improve the logic used to control the windows.

## Requirements
* Update serial port to fit your configuration.
* Create an output directory for the logs. Its not created automatically.

## Required gems
* serialport

## Tested on
* ruby 2.0.0p247 (2013-06-27 revision 41674) [x86_64-linux]
* ruby 1.9.3p125 (2012-02-16) [i386-mingw32]