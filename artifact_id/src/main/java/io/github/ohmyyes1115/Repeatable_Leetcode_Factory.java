package io.github.ohmyyes1115;

import javax.annotation.Nonnull;

class Repeatable_Leetcode_Factory {
    private MainUI m_ui;
    private IRepeatable_Leetcode_DAO m_repeatable_Leetcode_DAO;
    private LeetcodeVerifierFactory m_leetcode_verifier_factory;

    public Repeatable_Leetcode_Factory(MainUI ui, @Nonnull IRepeatable_Leetcode_DAO repeatable_Leetcode_DAO) {
        m_ui = ui;
        m_repeatable_Leetcode_DAO = repeatable_Leetcode_DAO;
        m_leetcode_verifier_factory = new LeetcodeVerifierFactory();
    }

    public Inner_Factory createRepeatable() {
        return new Inner_Factory();
    }

    // private IRepeatableFactory createFactory_Leetcode(Repeatable_Leetcode_VO problem, CodingView leetcode_view) {
    //     return new RepeatableFactory_X_Leetcode(problem, leetcode_view);
    // }

    public class Inner_Factory {
        public IRepeatable fromVO(Repeatable_Leetcode_VO vo) {
            return new Repeatable_X_Leetcode(vo, m_repeatable_Leetcode_DAO, m_ui.getCodingView(),
                // m_leetcode_verifier_factory.create(vo.getId())
                null
            );
        }

        private Inner_Factory() {}
    }
}