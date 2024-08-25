package io.github.ohmyyes1115;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

import io.github.ohmyyes1115.IRepeatedRecord_DAO.EResult;

class RepeatablePlayer {

    public interface IOnFinishedListener {
        void onFinished();
    }

    private IRepeatable m_repeatable;
    private IRepeatedRecord_DAO m_record_DAO;

    private Instant m_starting_time;

    public RepeatablePlayer(IRepeatable repeatable, IRepeatedRecord_DAO record_DAO) {
        m_repeatable = repeatable;
        m_record_DAO = record_DAO;
    }

    public void play(IOnFinishedListener listener) {
        m_starting_time = Instant.now();

        m_repeatable.registerListener(() -> {
            if (m_repeatable.verify()) {
                m_repeatable.setLastRepeatTime(LocalDateTime.now());
                m_repeatable.setRSD(calcRSD());

                QD qd = m_repeatable.getQD();

                // if it take to much time to repeat this repeatable, stay in the original ILevel
                if ((qd == null) || m_repeatable.getRSD().isShorterThan(qd.getDuration())) {
                    m_repeatable.getILevel().increase();
                }

                m_record_DAO.insert(
                    RepeatableRecord_VO_X_Helper.fromRepeatable(m_repeatable).toVO(EResult.O));

                listener.onFinished();
            }
            else {
                m_repeatable.getILevel().decrease();

                m_record_DAO.insert(
                    RepeatableRecord_VO_X_Helper.fromRepeatable(m_repeatable).toVO(EResult.X));
            }
        });
    
        m_repeatable.play();

        m_starting_time = Instant.now();
    }

    private RSD calcRSD() {
        // if (true) return RSD.fromString("61m");
        long duration = Duration.between(m_starting_time, Instant.now()).toSeconds();
        return new RSD(new CustomDuration(Duration.ofSeconds(duration)));
    }
}