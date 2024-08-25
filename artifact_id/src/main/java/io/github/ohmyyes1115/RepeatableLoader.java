package io.github.ohmyyes1115;

import java.util.List;
import java.util.stream.Collectors;

class RepeatableLoader {

    private IRepeatable_Leetcode_DAO m_dao;

    public RepeatableLoader(IRepeatable_Leetcode_DAO dao) {
        m_dao = dao;
    }

    public List<IRepeatable> loadLeetcodeRepeatables(Repeatable_Leetcode_Factory factory) {
        List<Repeatable_Leetcode_VO> repeatables = m_dao.loadAll();

        return repeatables.stream()
                          .map(vo -> factory.createRepeatable().fromVO(vo))
                          .collect(Collectors.toList());
    }
}