package simulation;

public final class Clock {
    private static int time =0;

    private Clock(){}//Empty
/*
This is incrementing everyclock cycle
 */
    public static void advanceClock(){
        time++;
    }
/*
provides  us the current clock time
@return the time variable whenever called
 */
    public static int getCurrentClockTime(){
        return time;
    }
}
