package io.github.ohmyyes1115;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

class Repeatable_Leetcode_VO {

    private String m_title;
    private RID m_rid;
    private String m_url;
    private BufferedImage m_desc_img;
    private String m_template;
    private String m_answer;
    private int m_iLevel;
    private LocalDateTime m_last_repeat_time;
    private QD m_qd;
    private RSD m_rsd;

    public Repeatable_Leetcode_VO setTitle(String title) {
        m_title = title;
        return this;
    }

    public String getTitle() {
        return m_title;
    }

    public RID getRid() {
        return m_rid;
    }

    public Repeatable_Leetcode_VO setRid(RID rid) {
        m_rid = rid;
        return this;
    }

	public Repeatable_Leetcode_VO setUrl(String url) {
		m_url = url;
        return this;
	}

    public String getUrl() {
        return m_url;
    }

    public Repeatable_Leetcode_VO setDescription(BufferedImage desc_img) {
        m_desc_img = desc_img;
        return this;
    }

    public BufferedImage getDescription() {
        return m_desc_img;
    }

	public Repeatable_Leetcode_VO setTemplate(String template) {
		m_template = template;
        return this;
	}

    public String getTemplate() {
        return m_template;
    }

    public Repeatable_Leetcode_VO setAnswer(String ans) {
        m_answer = ans;
        return this;
    }

    public String getAnswer() {
        return m_answer;
    }

    public int getILevel() {
        return m_iLevel;
    }

    public Repeatable_Leetcode_VO setILevel(int iLevel) {
        m_iLevel = iLevel;
        return this;
    }

    public LocalDateTime getLastRepeatTime() {
        return m_last_repeat_time;
    }

    public Repeatable_Leetcode_VO setLastRepeatTime(LocalDateTime last_repeat_time) {
        m_last_repeat_time = last_repeat_time;
        return this;
    }

    public QD getQD() {
        return m_qd;
    }

    public Repeatable_Leetcode_VO setQD(QD qd) {
        m_qd = qd;
        return this;
    }

    public RSD getRSD() {
        return m_rsd;
    }

    public Repeatable_Leetcode_VO setRSD(RSD rsd) {
        m_rsd = rsd;
        return this;
    }
}