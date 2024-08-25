package io.github.ohmyyes1115;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Get the 'time' to repeat (in LocalDateTime form)
 */
public class GetTTR {
    
    public static LocalDateTime from(IRepeatable repeatable) {
        if (repeatable.getLastRepeatTime() == null) {
            return null;
        }

        QD qd = repeatable.getQD();

        if ((qd == null) || repeatable.getRSD().isShorterThan(qd.getDuration())) {
            return repeatable.getLastRepeatTime().plus(
                repeatable.getILevel().getDelta());
        }
        // if this repeatable takes duration more than it's QD to repeat, repeat it in 16 hours (i.e., the next day)
        else {
            return repeatable.getLastRepeatTime().plus(
                Duration.ofHours(16));
        }
    }
}