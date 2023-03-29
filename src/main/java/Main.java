import Controller.Controller;
import Controller.SimulationManager;
import View.SimulationFrame;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        //SimulationFrame frame = new SimulationFrame("QUEUES MANAGEMENT APPLICATION");

        SimulationManager simulationManager = new SimulationManager(200,9,3,100,10,20,1000);
        Thread thread = new Thread(simulationManager);
        thread.start();
        Thread.sleep(simulationManager.timeLimit * 1000L);
        System.out.println("Avg arrival time: " + simulationManager.computeAvgArrivalTime());
        System.out.println("Avg service time: " + simulationManager.computeAvgServiceTime());
    }
}
