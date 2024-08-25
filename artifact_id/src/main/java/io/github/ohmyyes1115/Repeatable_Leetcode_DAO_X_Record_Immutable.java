package io.github.ohmyyes1115;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * This class is an implementation of 'IRepeatable_Leetcode_DAO'
 * which allows 'Reading-operation' but disallows 'Writing-operation'.
 * 
 * It's a decorator of 'IRepeatable_Leetcode_DAO'
 * 
 * This is for debug purpose.
 * Turn off if you don't want to insert any repeating-records.
 */
class Repeatable_Leetcode_DAO_X_Record_Immutable implements IRepeatable_Leetcode_DAO {

    private IRepeatable_Leetcode_DAO m_decoratee;

    public Repeatable_Leetcode_DAO_X_Record_Immutable(IRepeatable_Leetcode_DAO decoratee) {
        m_decoratee = decoratee;
    }

    @Override  // IRepeatable_Leetcode_DAO
    public List<Repeatable_Leetcode_VO> loadAll() {
        // Reading-operation => forward
        return m_decoratee.loadAll();
    }

    @Override  // IRepeatable_Leetcode_DAO
    public boolean updateILevel(RID rid, int iLevel) {
        // Writing-operation => skip
        return true;
    }

    @Override  // IRepeatable_Leetcode_DAO
    public boolean updateLastRepeatTime(RID rid, LocalDateTime last_repeat_time) {
        // Writing-operation => skip
        return true;
    }

    @Override  // IRepeatable_Leetcode_DAO
    public Optional<LocalDateTime> readLastRepeatTime(RID rid) {
        // Reading-operation => forward
        return m_decoratee.readLastRepeatTime(rid);
    }

    @Override
    public boolean updateRSD(RID rid, RSD rsd) {
        // Writing-operation => skip
        return true;
    }

    @Override
    public Optional<RSD> readRSD(RID rid) {
        // Reading-operation => forward
        return m_decoratee.readRSD(rid);
    }

    @Override
    public Optional<QD> readQD(RID rid) {
        // Reading-operation => forward
        return m_decoratee.readQD(rid);
    }
    
}