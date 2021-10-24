package simulation;

public final class Clock {
    private static int time =0;

    private Clock(){
        /* empty */
    }

    public static void advanceClock(){
        time++;
    }

    public static int getCurrentClockTime(){
        return time;
    }
}
