require "fileutils"

class Logger

  def initialize(filename)
    @filename_ending = filename
    @file = create()
    @date_pattern = "%Y%m%d"
  end

  def write(output)
    rotate()

    if output.to_s != ''
	  puts output
      @file.puts output
      @file.flush
    end
  end
  
  private
  
  def rotate
    filename = File.basename(@file)
  
    if !(filename.include?(Time.now.strftime(@date_pattern)))
      @file.close
      @file = create()
    end  
  end

  def create
    file = make_filename()
    FileUtils.mkpath(File.dirname(file))	
    File.new(file, "a")
  end
  
  def make_filename
    time = Time.now
    time.year.to_s + "/" + time.strftime("%m") + "/" + time.strftime('%Y%m%d') + @filename_ending
  end
end

