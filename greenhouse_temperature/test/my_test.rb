require 'test/unit'
require 'rubygems'
require 'serialport'

class MyTest < Test::Unit::TestCase

  # Called before every test method runs. Can be used
  # to set up fixture information.
  def setup
    # Do nothing
  end

  # Called after every test method runs. Can be used to tear
  # down fixture information.

  def teardown
    # Do nothing
  end

  # trying to use socat to setup a virtual serial port for testing
  def test_serial_port
    master = SerialPort.new "/home/ralph/dev/vmodem0", 38400

    master.write "AT\r\n"

    master.write "AT\r\n"
    master.write "AT\r\n"
    #slave = SerialPort.new "/dev/pts/10", 38400

    assert_equal "AT", master.read
  end
end