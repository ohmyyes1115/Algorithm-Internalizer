package io.github.ohmyyes1115;

import java.time.LocalDateTime;

class TimeToRepeatOrNot {

    public static boolean from(IRepeatable repeatable) {
        // never repeated
        if (repeatable.getLastRepeatTime() == null) {
            return true;
        }

        LocalDateTime ttr = GetTTR.from(repeatable);

        // time's up
        return ttr.isEqual(LocalDateTime.now()) || ttr.isBefore(LocalDateTime.now());
    }
}