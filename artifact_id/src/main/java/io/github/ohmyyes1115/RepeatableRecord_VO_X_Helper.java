package io.github.ohmyyes1115;

import java.time.LocalDateTime;

import io.github.ohmyyes1115.IRepeatedRecord_DAO.EResult;

class RepeatableRecord_VO_X_Helper {

    public static InnerConverter fromRepeatable(IRepeatable repeatable) {
        return new InnerConverter(repeatable);
    }

    public static class InnerConverter {

        private IRepeatable m_repeatable;

        public InnerConverter(IRepeatable repeatable) {
            m_repeatable = repeatable;
        }

        public RepeatedRecord_VO toVO(EResult result) {
            RepeatedRecord_VO vo = new RepeatedRecord_VO();

            vo.title    = m_repeatable.getTitle();
            vo.dateTime = LocalDateTime.now();
            vo.rid      = m_repeatable.getRid();
            vo.result   = result;
            vo.qd   = m_repeatable.getQD();

            if (result == EResult.O) {
                vo.rsd   = m_repeatable.getRSD();
            }

            return vo;
        }
    }

}