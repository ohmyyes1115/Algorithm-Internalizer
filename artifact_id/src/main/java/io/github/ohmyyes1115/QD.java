package io.github.ohmyyes1115;

/**
 * Qualified Duration: max repeatition spending duration allowance.
 * i.e., you need to finish the repeatable within this duration.
 * 
 * [NOTE] This class is sync with class 'RSD'
 */
class QD {

    private CustomDuration m_duration;

    public QD(CustomDuration duration) {
        m_duration = duration;
    }

    public static QD fromString(String formatted_time_str) {
        return new QD(CustomDuration.fromString(formatted_time_str));
    }

    public String toString() {
        return m_duration.toString();
    }

    public String toString(CustomDuration.ETimeScale min_scale) {
        return m_duration.toString(min_scale);
    }

    public boolean isShorterThan(QD other) {
        return m_duration.isShorterThan(other.m_duration);
    }

    public boolean isShorterThan(CustomDuration duration) {
        return m_duration.isShorterThan(duration);
    }

    public CustomDuration getDuration() {
        return m_duration;
    }
}