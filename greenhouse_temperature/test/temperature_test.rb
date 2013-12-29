require 'test/unit'
require 'serialport'

class TemperatureLogTest < Test::Unit::TestCase

  def setup
    @sp = SerialPort.new(port="/dev/tty", baud_rate=115200, data_bits=8, stop_bits=1, parity=SerialPort::NONE)
    @sp.read_timeout = 100
  end

  def teardown
    @sp.close
  end


  def test_simple

    open("/dev/tty", "r+") { |tty|
      tty.sync = true

      Thread.new {
        while true do
          puts "while true"
          tty.printf("Hello World\n\n")
        end
      }

      while (l = tty.gets) do
        puts "while hello #{l}"

        @sp.write("Hello World #{l.sub('\n', '\r')}")
      end


    }
  end

end
