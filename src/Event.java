public abstract class Event {

    protected int processingTime;
    protected int creationTime;
    protected int startTime;

    protected Event(){
        creationTime = Clock.getTime();
        startTime = 0;
    }

    protected void start() {
        startTime = Clock.getTime();
    }

    public boolean isDone(){
        return (Clock.getTime() - startTime) >= processingTime;
    }
}
