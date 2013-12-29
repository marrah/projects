# Used to read data from the serial port.

class ArduinoReader  
  def initialize(port_name)
    @serial_port = SerialPort.new(port=port_name, baud_rate=115200, data_bits=8, stop_bits=1, parity=SerialPort::NONE)
    @serial_port.read_timeout = (30 * 1000)
  end
  
  def close
    @serial_port.close
  end

  #read a line
  def read()
    @serial_port.gets(nil)
  end

  def write(msg)
    @serial_port.puts(msg)
  end
end