package events;

import simulation.Patient;
import simulation.Simulation;

import static simulation.Clock.getCurrentClockTime;
import static simulation.Simulation.treatmentRooms;
import static simulation.Simulation.waitingQueue;

public class StartTreatmentEvent extends Event {

    public StartTreatmentEvent(Patient patient) {
        super();
        this.patient = patient;
        processingTime = patient.getTreatmentTime();
    }

    @Override
    public void start(){
        if(treatmentRooms.anyRoomAvailable()){
            super.start();
            treatmentRooms.placePatient(treatmentRooms.getRoomAvailable(), this);
            patient.setCurrentEvent(this);
            waitingQueue.remove();
            System.out.println(this);
        }
            shouldStart = false;

    }

    @Override
    public void finish() {

        patient.setCurrentEvent(null);
        isDone = true;
        System.out.println(this);
        if(patient.getPriority() != Patient.HIGHEST_PRIORITY){
            patient.setCurrentEvent(new DepartureEvent(patient));
            patient.getCurrentEvent().setShouldStart(true);
        }

    }

    @Override
    public String toString() {
        if(isDone()){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") finishes treatment";
        } else if (hasStarted && startTime == getCurrentClockTime()){
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") starts treatment"
                    + " (waited " + patient.getWaitingTime()
                    + ", "+ treatmentRooms.roomsAvailable() + " rm(s) remain)";
        } else if (hasStarted){
            return "Time " + getCurrentClockTime() + ":  " + patient.getPatientNumber()
                    + " is STILL being treated (time remaining: " + (processingTime - (getCurrentClockTime() - startTime) + ").");
        } else {
            return "Time " + startTime + ":  " + patient.getPatientNumber()
                    + " (Priority " + patient.getPriority() + ") is WAITING for treatment"
                    + " (waited " + patient.getWaitingTime()
                    + ", "+ treatmentRooms.roomsAvailable() + " rm(s) remain)";
        }
    }
}
