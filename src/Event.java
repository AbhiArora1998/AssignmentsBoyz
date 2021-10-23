public abstract class Event {

    protected int processingTime;
    protected boolean done = false;

    public abstract void happens();

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
