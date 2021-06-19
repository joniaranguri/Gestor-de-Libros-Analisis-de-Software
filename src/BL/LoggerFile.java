package BL;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerFile {
    private static LoggerFile INSTANCE;
    private static Logger logger;

    private LoggerFile() {
        try {

            logger = Logger.getLogger("MyLog");
            FileHandler fh;
            // This block configure the logger with handler and formatter
            fh = new FileHandler("operations.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages

        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static LoggerFile getInstance() {
        //No queremos que cada vez que se clickee sobre la opcion
        //se cree una nueva instancia de la clase
        //Para evitar eso usamos el patron Singleton
        if (INSTANCE == null) {
            INSTANCE = new LoggerFile();
        }
        return INSTANCE;
    }

    public static void Log(final String message) {
        logger.info(message);

    }
}
