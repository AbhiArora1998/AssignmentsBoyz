package simulation;

/**
 * This file is part of a solution to
 *		CPSC300 Assignment 1 Fall 2021
 *
 * Increments and keeps record of time in the simulation
 *
 * @author The Boyz
 * @version 1
 */

public final class Clock {
    private static int time =0;

    private Clock(){}//Empty

    /**
     * This is incrementing everyclock cycle
     */
    public static void advanceClock(){
        time++;
    }

    /**
     * provides  us the current clock time
     * @return the time variable whenever called
     */
    public static int getCurrentClockTime(){
        return time;
    }
}
