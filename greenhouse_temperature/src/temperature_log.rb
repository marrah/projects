require "serialport"
require 'logger'

require './arduino_reader'

# Loop forever logging the data retrieved from the aurdino/serial port.

STDOUT.sync = true
@log = Logger.new('../logs/temperatures.txt', 'daily')
@log.level = Logger::INFO

def run (arduino)
  while true do
    while (i = arduino.read()) do
       puts i
       @log.info(i)
    end
  end
	
  arduino.close
  @log.close
end

run(ArduinoReader.new("COM4"))