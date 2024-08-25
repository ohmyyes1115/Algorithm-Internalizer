package io.github.ohmyyes1115;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class RepeatableHeap {

    private PriorityQueue<IRepeatable> m_queue;

    public RepeatableHeap(List<IRepeatable> repeatables) {
        m_queue = createPriorityQueue(repeatables);
    }

    public void add(IRepeatable r) {
        m_queue.add(r);
    }

    public IRepeatable peek() {
        return m_queue.peek();
    }

    public IRepeatable poll() {
        return m_queue.poll();
    }

    private PriorityQueue<IRepeatable> createPriorityQueue(List<IRepeatable> repeatables) {

        Comparator<IRepeatable> comparator = (a, b) -> {
            // If this repeatable has never repeat yet, no hurry to start it
            // priority:
            //     1st  = repeated &&    need to repeat now (TTR >= now)
            //     2nd  = never repeat yet
            //     3rd  = repeated && no need to repeat now (TTR < now)

            LocalDateTime a_TTR = (a.getLastRepeatTime() != null) ? GetTTR.from(a) : LocalDateTime.now();
            LocalDateTime b_TTR = (b.getLastRepeatTime() != null) ? GetTTR.from(b) : LocalDateTime.now();

            return a_TTR.compareTo(b_TTR);
        };
        
        PriorityQueue<IRepeatable> queue = new PriorityQueue<>(comparator);
        queue.addAll(repeatables);

        return queue;
    }
}