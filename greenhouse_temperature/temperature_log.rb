require "serialport"

require_relative 'arduino_reader.rb'
require_relative 'logger.rb'

$POLL_SECONDS = 30

def run 
  arduino = ArduinoReader.new
  log = Logger.new("_temperatures.txt")
    
  repeat_every($POLL_SECONDS) do
    log.write(arduino.read())
  end
	
  arduino.close
  log.close
end

def repeat_every(interval)
  loop do
    start_time = Time.now
    yield
    elapsed = Time.now - start_time	
    sleep([interval - elapsed, 0].max)
  end
end

run()