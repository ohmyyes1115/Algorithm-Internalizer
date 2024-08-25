package io.github.ohmyyes1115;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

import javax.annotation.Nonnull;

import io.github.ohmyyes1115.MainUI.CodingView;

class Repeatable_X_Leetcode implements IRepeatable {

    private Listener                 m_listener;
    private Repeatable_Leetcode_VO   m_repeatable_VO;
    private IRepeatable_Leetcode_DAO m_repeatable_Leetcode_DAO;
    private CodingView               m_coding_view;
    private ILeetcodeVerifier        m_verifier;

    private boolean m_mock_verified_result = false;

    public Repeatable_X_Leetcode(@Nonnull Repeatable_Leetcode_VO   vo,
                               @Nonnull IRepeatable_Leetcode_DAO dao,
                               @Nonnull CodingView               codingView,
                               @Nonnull ILeetcodeVerifier        verifier) {
        m_repeatable_VO           = vo;
        m_repeatable_Leetcode_DAO = dao;
        m_coding_view             = codingView;
        m_verifier                = verifier;
    }
    
    @Override  // IRepeatable
    public void registerListener(Listener listener) {
        m_listener = listener;
    }

    @Override  // IRepeatable
    public boolean play() {

        m_coding_view.setDescription(m_repeatable_VO.getDescription());
        m_coding_view.setCodingText(m_repeatable_VO.getTemplate());
        m_coding_view.registerListener(new MainUI.ICodingViewListener() {

            @Override
            public void onSubmitted() {
                m_listener.onSubmitted();
            }

            @Override
            public void OnButtonPressed_Leetcode() {
                openUrl(m_repeatable_VO.getUrl());
            }

            @Override
            public void OnButtonPressed_O() {
                m_mock_verified_result = true;
                m_listener.onSubmitted();

                // Clear problem-description & coding-text, since this 'repeatable' is finished.
                m_coding_view.setDescription(null);
                m_coding_view.setCodingText(null);
                
                m_coding_view.unRegisterListener(this);
            }

            @Override
            public void OnButtonPressed_X() {
                m_mock_verified_result = false;
                m_listener.onSubmitted();
            }
            
        });

        return true;
    }

    // @Override  // IRepeatable
    // public void OnRepeated_Correct(LocalDateTime date_time) {
    // }

    // @Override  // IRepeatable
    // public void OnRepeated_Wrong(LocalDateTime date_time) {
    // }

    @Override  // IRepeatable
    public boolean verify() {

        /* verify by text answer */
        // verifyByAnswer();
        
        return m_mock_verified_result;

        // return new LeetcodeVerifier(ans).verify(submission);
    }

    @SuppressWarnings("unused")
    private boolean verifyByAnswer() {
        String submission = m_coding_view.getCodingText();
        String ans = m_repeatable_VO.getAnswer();

        return m_verifier.verify(submission);
    }

    @Override  // IRepeatable
    public String getTitle() {
        return m_repeatable_VO.getTitle();
    }

    @Override  // IRepeatable
    public RID getRid() {
        return m_repeatable_VO.getRid();
    }

    @Override  // IRepeatable
    public ILevel getILevel() {
        return new ILevel_X_Leetcode(m_repeatable_VO, m_repeatable_Leetcode_DAO);
    }

    @Override  // IRepeatable
    public void setLastRepeatTime(LocalDateTime last_repeat_time) {
        m_repeatable_VO.setLastRepeatTime(last_repeat_time);
        m_repeatable_Leetcode_DAO.updateLastRepeatTime(m_repeatable_VO.getRid(), last_repeat_time);
    }

    @Override  // IRepeatable
    public LocalDateTime getLastRepeatTime() {
        return m_repeatable_VO.getLastRepeatTime();
        // m_repeatable_VO.setLastRepeatTime(
        //     m_repeatable_Leetcode_DAO.readLastRepeatTime(m_repeatable_VO.getId())
        //         .orElse(LocalDateTime.now().minusYears(10)));
    }

    @Override
    public QD getQD() {
        return m_repeatable_VO.getQD();
    }

    @Override
    public void setRSD(RSD rsd) {
        m_repeatable_VO.setRSD(rsd);
        m_repeatable_Leetcode_DAO.updateRSD(m_repeatable_VO.getRid(), rsd);
    }

    @Override
    public RSD getRSD() {
        return m_repeatable_VO.getRSD();
    }

    private boolean openUrl(String url) {

        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                try {
                    desktop.browse(URI.create(url));
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    // private String readFileString(String file_path) {
    //     try {
    //         return new String(
    //             Files.readAllBytes(Paths.get(file_path)));
    //     }
    //     catch (IOException e) {
    //         e.printStackTrace();
    //         return "";
    //     }
    // }
    
}