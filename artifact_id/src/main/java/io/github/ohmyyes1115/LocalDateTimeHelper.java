package io.github.ohmyyes1115;

import java.time.Duration;
import java.time.LocalDateTime;

import io.github.ohmyyes1115.LocalDateTimeHelper.InnerConvertor.EType;

class LocalDateTimeHelper {

    public static class InnerConvertor {
        public enum EType {
            REMOVE_FLOATING
        }

        private LocalDateTime m_LocalDateTime;
        private EType m_type;

        InnerConvertor(LocalDateTime localDateTime, EType type) {
            m_LocalDateTime = localDateTime;
            m_type = type;
        }

        public String toString() {
            if (m_type == EType.REMOVE_FLOATING) {
                String fullStr = m_LocalDateTime.toString();
                return fullStr.substring(0, fullStr.indexOf("."));
            }

            throw new IllegalArgumentException("Unknown type = " + m_type);
        }
    }

    public static InnerConvertor removeFloating(LocalDateTime localDateTime) {
        return new InnerConvertor(localDateTime, EType.REMOVE_FLOATING);
    }
}

class DurationHelper {

    public enum ETimeScale {
        SECOND_SCALE,
        MINITUE_SCALE,
        HOUR_SCALE
    }

    public static class InnerConvertor {

        private Duration m_duration;
        private String m_formatted_str;
        private ETimeScale m_min_scale;

        InnerConvertor(Duration duration) {
            m_duration = duration;
        }

        InnerConvertor(String formatted_str) {
            m_formatted_str = formatted_str;
        }

        public String toString() {
            return toString(ETimeScale.SECOND_SCALE);
        }

        public String toString(ETimeScale min_scale) {
            long total_secs = m_duration.getSeconds();
            StringBuilder sb = new StringBuilder();

            do {
                long hours = total_secs / 3600;
                if (hours >= 1) {
                    sb.append(hours + "h");
                }
                
                if (min_scale == ETimeScale.HOUR_SCALE) {
                    break;
                }

                long mins = (min_scale == ETimeScale.MINITUE_SCALE) ?
                                (total_secs - 1) / 60 + 1 :
                                 total_secs / 60;
                if (mins >= 1) {
                    if (!sb.isEmpty()) {
                        sb.append(":");
                    }
                    sb.append(mins + "m");
                }

                if (min_scale == ETimeScale.MINITUE_SCALE) {
                    break;
                }

                long secs = total_secs % 60;
                if (secs >= 1) {
                    if (!sb.isEmpty()) {
                        sb.append(":");
                    }
                    sb.append(secs + "s");
                }
            } while (false /* inorder to break */);

            return sb.toString();
        }

        // public Duration toDuration() {

        // }
    }

    public static InnerConvertor fromDuration(Duration duration) {
        return new InnerConvertor(duration);
    }

    public static InnerConvertor fromString(String formatted_str) {
        return new InnerConvertor(formatted_str);
    }
}