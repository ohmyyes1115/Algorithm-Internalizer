package io.github.ohmyyes1115;

import java.time.LocalDateTime;

abstract class Repeatable_X_Abstract implements IRepeatable {

    // private Listener m_listener;

    public final LocalDateTime getTimeToRepeat() {

        return getLastRepeatTime().plus(
                getILevel().getDelta());
    }

    // final protected void onSubmitted() {
    //     m_listener.onSubmitted();
    // }

    // private void notifyFinished() {
    //     if (m_listener != null) {
    //         m_listener.onFinished();
    //     }
    // }

    // @Override  // IRepeatable.Listener
    // public void registerListener(Listener listener) {
    //     m_listener = listener;
    // }

    // protected abstract boolean verify();
    // protected abstract ILevel getILevel();
    // protected abstract void setLastRepeatTime(LocalDateTime last_repeat_time);
}