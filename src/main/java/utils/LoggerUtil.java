package utils;
import org.apache.logging.log4j.*;
import org.testng.annotations.*;

public class LoggerUtil {
  
	public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}	

