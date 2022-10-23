package eu.schk.lastgang.model;

import java.time.LocalDateTime;

public class Peak {

    LocalDateTime start;
    LocalDateTime end;
    Double value;
    Double kw;
    long duration;

    public Peak(LocalDateTime start, LocalDateTime end, Double value, long duration, Double kW) {
        this.start = start;
        this.end = end;
        this.value = value;
        this.kw = kW;
        this.duration = duration;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Double getKw() {
        return kw;
    }

    public void setKw(Double kw) {
        this.kw = kw;
    }

    @Override
    public String toString() {
        return "Peak{" +
                "start=" + start +
                ", end=" + end +
                ", value=" + value + " kWh" +
                ", duration=" + duration + " min" +
                '}';
    }
}
