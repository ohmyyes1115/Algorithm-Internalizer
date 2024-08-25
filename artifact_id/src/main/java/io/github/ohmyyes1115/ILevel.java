package io.github.ohmyyes1115;

import java.time.temporal.TemporalAmount;

interface ILevel {
    void increase();
    void decrease();
    TemporalAmount getDelta();
}