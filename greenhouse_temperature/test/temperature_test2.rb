require 'test/unit'
require 'serialport'

require "../src/arduino_reader"

class MyTest < Test::Unit::TestCase

   def setup
    @serial_port = '/dev/tty'

    @sp = SerialPort.new(port=@serial_port, baud_rate=115200, data_bits=8, stop_bits=1, parity=SerialPort::NONE)
    @sp.read_timeout = 100
    @sp.puts("Hello World\n")
    @sp.puts("Hello World\n")
    @sp.puts("Hello World\n")
    @sp.puts("Hello World\n")
    @sp.puts("Hello World\n")
    @sp.puts("Hello World\n")
    @sp.puts("Hello World\n")
    @sp.puts("Hello World\n")
    @sp.puts("Hello World\n")
    puts "Wassup #{@sp.gets()}"
  end

  def teardown
    @sp.close
  end

  def test_serialport
    serial = ArduinoReader.new(@serial_port)
    serial.write("Hello World")
    puts "Wassup2 #{serial.read()}"

    assert_equal "Hello World", serial.read()
  end
end