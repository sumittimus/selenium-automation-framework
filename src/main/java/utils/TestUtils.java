package utils;

import org.apache.logging.log4j.Logger;

public class TestUtils {
    private static final Logger logger = LoggerUtil.getLogger(TestUtils.class);

    public static void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
            logger.info("Waited for " + seconds + " seconds");
        } catch (InterruptedException e) {
            logger.error("Thread sleep interrupted", e);
        }
    }
}
