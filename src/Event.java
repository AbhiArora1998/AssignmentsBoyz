public abstract class Event {

    protected int processingTime;
    protected int creationTime;
    protected int startTime;

    protected Event(){
        creationTime = Clock.getTime();
    }

    protected void start() {
        startTime = Clock.getTime();
    }

    public boolean isDone(){
        return (Clock.getTime() - startTime) >= processingTime;
    }
}
