package io.github.ohmyyes1115;

import java.time.Period;
import java.time.temporal.TemporalAmount;

class ILevel_X_Leetcode implements ILevel {

    private Repeatable_Leetcode_VO m_repeatable_VO;
    private IRepeatable_Leetcode_DAO m_repeatable_DAO;

    private static final Period[] DELTA = {
        Period.ofDays( 0),  // Level 0
        Period.ofDays( 1),  // Level 1
        Period.ofDays( 2),  // Level 2
        Period.ofDays( 3),  // Level 3
        Period.ofDays( 4),  // Level 4
        Period.ofDays( 5),  // Level 5
        Period.ofDays( 6),  // Level 6
        Period.ofDays( 7),  // Level 7
        Period.ofDays( 8),  // Level 8
        Period.ofDays( 9),  // Level 9
        Period.ofDays(10),  // Level 10
    };  // Repeating time-interval of each level

    private static final int MAX_ILEVEL = DELTA.length - 1;

    public ILevel_X_Leetcode(Repeatable_Leetcode_VO vo, IRepeatable_Leetcode_DAO dao) {
        m_repeatable_VO  = vo;
        m_repeatable_DAO = dao;
    }

    @Override
    public void increase() {
        int new_iLevel = clamp(m_repeatable_VO.getILevel() + 1, 0, MAX_ILEVEL);
        m_repeatable_VO.setILevel(new_iLevel);
        m_repeatable_DAO.updateILevel(m_repeatable_VO.getRid(), new_iLevel);
    }

    @Override
    public void decrease() {
        int new_iLevel = clamp(m_repeatable_VO.getILevel() - 3, 0, MAX_ILEVEL);
        m_repeatable_VO.setILevel(new_iLevel);
        m_repeatable_DAO.updateILevel(m_repeatable_VO.getRid(), new_iLevel);
    }

    @Override
    public TemporalAmount getDelta() {
        return DELTA[m_repeatable_VO.getILevel()];
    }

    private static int clamp(int val, int min, int max) {
        return Math.max(0, Math.min(10, val));
    }

}