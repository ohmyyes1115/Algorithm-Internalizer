package io.github.ohmyyes1115;

import java.time.Duration;

class CustomDuration {

    public enum ETimeScale {
        SECOND_SCALE,
        MINUTE_SCALE,
        HOUR_SCALE
    }

    private Duration m_duration;

    public CustomDuration(Duration duration) {
        m_duration = duration;
    }

    public static CustomDuration fromString(String formatted_time_str) {
        String duration_style_str = "PT" + formatted_time_str.toUpperCase().replace(":", "");
        return new CustomDuration(Duration.parse(duration_style_str));
    }

    public String toString() {
        return toString(ETimeScale.SECOND_SCALE);
    }

    public String toString(ETimeScale min_scale) {
        long hours = 0;
        long mins = 0;
        long secs = 0;

        long remain_secs = m_duration.getSeconds();

        hours = remain_secs / 3600;
        remain_secs %= 3600;

        mins = remain_secs / 60;
        secs = remain_secs % 60;

        // round secs to mins
        mins += (secs / 60);
        secs %= 60;

        if (min_scale == ETimeScale.MINUTE_SCALE || min_scale == ETimeScale.HOUR_SCALE) {
            if (secs > 0) {
                mins += 1;
                secs = 0;
            }
        }

        // round mins to hours
        hours += (mins / 60);
        mins %= 60;

        if (min_scale == ETimeScale.HOUR_SCALE) {
            if (mins > 0) {
                hours += 1;
                mins = 0;
            }
        }

        StringBuilder sb = new StringBuilder();
        
        if (hours > 0) {
            sb.append(hours + "h");
        }
            
        if (mins > 0) {
            if (!sb.isEmpty()) {
                sb.append(":");
            }
            sb.append(mins + "m");
        }

        if (secs >= 1) {
            if (!sb.isEmpty()) {
                sb.append(":");
            }
            sb.append(secs + "s");
        }
        

        if (sb.isEmpty()) {
            // 0s / 0m / 0h
            sb.append("0" + (scaleToString(min_scale)));
        }

        return sb.toString();
    }

    private String scaleToString(ETimeScale min_scale) {
        if (min_scale == ETimeScale.SECOND_SCALE) return "s";
        if (min_scale == ETimeScale.MINUTE_SCALE) return "m";
        if (min_scale == ETimeScale.HOUR_SCALE)   return "h";

        return "";
    }

    public boolean isShorterThan(CustomDuration other) {
        return m_duration.compareTo(other.m_duration) < 0;
    }
}