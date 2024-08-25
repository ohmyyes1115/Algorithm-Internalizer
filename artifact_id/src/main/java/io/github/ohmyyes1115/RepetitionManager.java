package io.github.ohmyyes1115;

import java.util.List;

class RepetitionManager {

    // private MainUI m_ui;
    private RepeatingThread m_RepeatingThread;

    public RepetitionManager(MainUI ui) {
        // m_ui = ui;
    }

    public void repeat(List<IRepeatable> repeatables) {
        if (repeatables.isEmpty())
            return;

        if (m_RepeatingThread != null) {
            m_RepeatingThread.stopRepeat();
        }

        m_RepeatingThread = new RepeatingThread(repeatables, RepeatableRecordDAO_Factory.create());
        m_RepeatingThread.startRepeat();
    }

    // private void executeFirst() {
    //     System.out.println("executeFirst");

    //     if (m_queue.isEmpty()) {
    //         return;
    //     }

    //     SwingUtilities.invokeLater(() -> {
    //         IRepeatable repeatable = m_queue.poll();
    //         new RepeatablePlayer(repeatable).play(() -> {
    //             m_queue.add(repeatable);
    //         });
    //     });

    //     synchronized (this) {
    //         try {
    //             wait();
    //         } catch (InterruptedException e) {
    //             e.printStackTrace();
    //         }
    //     }

    // }
}