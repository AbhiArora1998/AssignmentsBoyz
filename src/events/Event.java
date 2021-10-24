package events;

import static simulation.Clock.getCurrentClockTime;

public abstract class Event {

    protected int processingTime;
    protected int creationTime;
    protected int startTime;

    protected Event(){
        creationTime = getCurrentClockTime();
    }

    protected void start() {
        startTime = getCurrentClockTime();
    }

    public boolean isDone(){
        return (getCurrentClockTime() - startTime) >= processingTime;
    }
}
