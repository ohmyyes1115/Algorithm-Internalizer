package io.github.ohmyyes1115;

/**
 * Repeatition Spending Duration: how long to finish this repeatable
 * 
 * [NOTE] This class is sync with class 'QD'
 */
class RSD {

    private CustomDuration m_duration;

    public RSD(CustomDuration duration) {
        m_duration = duration;
    }

    public static RSD fromString(String formatted_time_str) {
        return new RSD(CustomDuration.fromString(formatted_time_str));
    }

    public String toString() {
        return m_duration.toString();
    }

    public String toString(CustomDuration.ETimeScale min_scale) {
        return m_duration.toString(min_scale);
    }

    public boolean isShorterThan(RSD other) {
        return m_duration.isShorterThan(other.m_duration);
    }

    public boolean isShorterThan(CustomDuration duration) {
        return m_duration.isShorterThan(duration);
    }

    public CustomDuration getDuration() {
        return m_duration;
    }
}