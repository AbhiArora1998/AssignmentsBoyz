public class DepartureEvent extends Event {

    private static final int DEPARTURE_PROCESSING_TIME = 1;

    public DepartureEvent(Patient patient) {
        super();
        processingTime = DEPARTURE_PROCESSING_TIME;
    }

    @Override
    public void start() {
        super.start();
    }
}
