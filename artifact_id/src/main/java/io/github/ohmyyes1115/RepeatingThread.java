package io.github.ohmyyes1115;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.swing.SwingUtilities;

class RepeatingThread extends Thread {

    private List<IRepeatable> m_repeatables;
    private RepeatableHeap m_heap;
    private boolean m_stop = false;
    private IRepeatedRecord_DAO m_record_DAO;

    public RepeatingThread(List<IRepeatable> repeatables, IRepeatedRecord_DAO record_DAO) {
        m_repeatables = repeatables;
        m_record_DAO = record_DAO;
    }

    public void startRepeat() {
        if (!m_repeatables.isEmpty()) {
            m_heap = new RepeatableHeap(m_repeatables);
            start();
        }
    }

    public void stopRepeat() {
        m_stop = true;
        interrupt();

        try {
            join();
        }
        catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {

        while (!m_stop) {
            IRepeatable first = m_heap.peek();

            if (TimeToRepeatOrNot.from(first)) { 
                m_heap.poll();

                SwingUtilities.invokeLater(() -> {
                    new RepeatablePlayer(first, m_record_DAO).play(() -> {
                        m_heap.add(first);

                        synchronized (this) {
                            notify();
                        }
                    });
                });

                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
            else {
                // sleep until the closest TTR expired
                long diff_sec = ChronoUnit.SECONDS.between(LocalDateTime.now(), GetTTR.from(first)) + 1;  // + 1 to skip 0.x sec
                System.out.println("Sleep " + diff_sec / 3600 + " hours");

                try {
                    sleep(diff_sec * 1000);
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }

    // private void execute() {
    //     System.out.println("executeNext");

    //     IRepeatable repeatable = m_queue.poll();

    //     repeatable.registerListener(new IRepeatable.Listener() {

    //         @Override
    //         public void onSubmitted() {
    //             if (repeatable.verify()) {
    //                 repeatable.getILevel().increase();
    //                 repeatable.setLastRepeatTime(LocalDateTime.now());

    //                 m_queue.add(repeatable);

    //                 SwingUtilities.invokeLater(() -> executeNext());
    //             }
    //             else {
    //                 repeatable.getILevel().decrease();
    //             }
    //         }
    //     });
    
    //     repeatable.play();

    // }

}