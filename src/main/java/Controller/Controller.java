package Controller;

import View.SimulationFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Controller implements ActionListener {

    public SimulationFrame frame;

    public String timeLimitText;
    public String noClientsText;
    public String noQueuesText;
    public String strategy;
    public String arrivalTimeMinText;
    public String arrivalTimeMaxText;
    public String serviceTimeMinText;
    public String serviceTimeMaxText;

    private SimulationManager simulationManager;

    public Controller(String name, SimulationFrame simulationFrame){
        frame = simulationFrame;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("start")){

            timeLimitText = frame.timeLimit.getText();
            noClientsText = frame.noClients.getText();
            noQueuesText = frame.noQueues.getText();
            strategy = (String)frame.strategyBox.getSelectedItem();
            arrivalTimeMinText = frame.arrivalTimeMin.getText();
            arrivalTimeMaxText = frame.arrivalTimeMax.getText();
            serviceTimeMinText = frame.serviceTimeMin.getText();
            serviceTimeMaxText = frame.serviceTimeMax.getText();

            SwingWorker<Void, ArrayList<String>> swingWorker = new SwingWorker<Void, ArrayList<String>>() {

                @Override
                protected Void doInBackground() throws Exception {

                    int timeLimit = Integer.parseInt(timeLimitText);
                    int maxProcessingTime = Integer.parseInt(serviceTimeMaxText);
                    int minProcessingTime = Integer.parseInt(serviceTimeMinText);
                    int maxArrivalTime = Integer.parseInt(arrivalTimeMaxText);
                    int minArrivalTime = Integer.parseInt(arrivalTimeMinText);
                    int numberOfServers = Integer.parseInt(noQueuesText);
                    int numberOfClients= Integer.parseInt(noClientsText);
                    String strategy = (String)frame.strategyBox.getSelectedItem();

                    simulationManager = new SimulationManager(timeLimit, maxProcessingTime, minProcessingTime,
                            maxArrivalTime,minArrivalTime,numberOfServers,numberOfClients);
                    Thread thread = new Thread(simulationManager);
                    thread.start();
                    SelectionPolicy selectionPolicy;
                    assert strategy != null;
                    if(strategy.equals("Shortest time strategy"))
                        selectionPolicy = SelectionPolicy.SHORTEST_TIME;
                    else selectionPolicy = SelectionPolicy.SHORTEST_QUEUE;
                    simulationManager.setSelectionPolicy(selectionPolicy);
                    while(simulationManager.getRunning()){
                    frame.queues.setText(simulationManager.getToPrint());
                    }

                    if(!simulationManager.getRunning()){
                        printFinalThings();
                    }
                    return null;
                }
            };
            swingWorker.execute();
        }
        else{
            if(simulationManager!=null){
                simulationManager.endSimulationFromStop();
                printFinalThings();
            }
        }
    }

    public void printFinalThings(){
        frame.labelActualAvgArrivalTime.setText(Float.toString(simulationManager.computeAvgArrivalTime()));
        frame.labelActualAvgServiceTime.setText(Float.toString(simulationManager.computeAvgServiceTime()));
        frame.labelActualPeekHour.setText(Integer.toString(simulationManager.getPeekHour()));
        frame.labelActualPeekHour.setVisible(true);
        frame.labelActualAvgServiceTime.setVisible(true);
        frame.labelActualAvgArrivalTime.setVisible(true);
    }
}
