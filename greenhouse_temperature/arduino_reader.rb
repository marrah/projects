class ArduinoReader  
  def initialize
    @serial_port = SerialPort.new(port="COM3", baud_rate=115200, data_bits=8, stop_bits=1, parity=SerialPort::NONE)	
  end
  
  def close
    @serial_port.close
  end
  
  # read entire arduino serial buffer
  # filter out one temperature data set
  def read
    serial_buffer = @serial_port.read

    if serial_buffer.to_s != ''
      temperature = Time.now.strftime('%Y-%m-%d %H:%M:%S') + " "
      temperature_array = serial_buffer.split( /\r?\n/ )
      temperature += temperature_array[temperature_array.length() -2].strip
    end
  
    temperature
  end
end