public abstract class Event {

    protected int processingTime;
    public abstract void happens();
    public abstract void departure(Patient patient);

}
