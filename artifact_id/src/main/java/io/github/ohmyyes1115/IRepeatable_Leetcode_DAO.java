package io.github.ohmyyes1115;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

interface IRepeatable_Leetcode_DAO {

    List<Repeatable_Leetcode_VO> loadAll();

    boolean updateILevel(RID rid, int iLevel);

    boolean updateLastRepeatTime(RID rid, LocalDateTime last_repeat_time);

    Optional<LocalDateTime> readLastRepeatTime(RID rid);

    boolean updateRSD(RID rid, RSD rsd);
    
    Optional<RSD> readRSD(RID rid);

    Optional<QD> readQD(RID rid);
}