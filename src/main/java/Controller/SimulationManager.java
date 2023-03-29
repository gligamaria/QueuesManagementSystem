package Controller;

import Model.Server;
import Model.Task;
import View.SimulationFrame;

import static Controller.FileManagement.*;

import java.util.*;

public class SimulationManager implements Runnable{
    //data read from UI
    public int timeLimit ;
    private final int maxProcessingTime ;
    private final int minProcessingTime ;
    private final int maxArrivalTime ;
    private final int minArrivalTime ;
    private final int numberOfServers ;
    private final int numberOfClients;
    private SelectionPolicy selectionPolicy;

    public void setSelectionPolicy(SelectionPolicy selectionPolicy) {
        this.selectionPolicy = selectionPolicy;
    }

    private Scheduler scheduler;
    private List<Task> tasks = new ArrayList<Task>();
    private int totalArrivalTime = 0;
    private int totalServiceTime = 0;
    private int peekHour = 0;
    private int maxCounter = 0;
    private int taskCounter;

    private boolean running;
    private String toPrint;

    public String getToPrint() {
        return toPrint;
    }

    public boolean getRunning() {
        return running;
    }

    public int getPeekHour() {
        return peekHour;
    }

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime, int maxArrivalTime,
                             int minArrivalTime, int numberOfServers, int numberOfClients){
        // initialize the scheduler
        //   => create and start numberOfServers threads
        //   => initialize selection strategy => createStrategy
        this.timeLimit = timeLimit;
        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minArrivalTime = minArrivalTime;
        this.numberOfServers = numberOfServers;
        this.numberOfClients = numberOfClients;
        running = true;
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(selectionPolicy);
        // initialize frame to display simulation
        // generate numberOfClients clients using generateNRandomTasks()
        //   and store them to generatedThreads
        generateNRandomTasks();
        setFile("out.txt");
    }

    public int randomNumber(int min, int max){
        return new Random().nextInt((max - min + 1)) + min;
    }

    public void sortTasks(){
        for(Task firstTask:tasks){
            for(Task secondTask:tasks){
                if(firstTask.getArrivalTime() > secondTask.getArrivalTime())
                    Collections.swap(tasks,tasks.indexOf(firstTask), tasks.indexOf(secondTask));
            }
        }
    }

    public void generateNRandomTasks(){
        // generated N random tasks:
        // - random processing time
        // minProcessingTime < processingTime < maxProcessingTime
        // - random arrivalTime
        int n = numberOfClients, index = 1;
        while (n != 0){
            int processingTime = randomNumber(minProcessingTime, maxProcessingTime);
            int arrivalTime = randomNumber(minArrivalTime, maxArrivalTime);
            Task newTask = new Task(arrivalTime, processingTime);
            tasks.add(newTask);
            n--;
        }
        // sort list with respect to arrivalTime
        sortTasks();
        for (Task currentTask : tasks) {
            currentTask.setID(index);
            index++;
        }
    }

    public float computeAvgArrivalTime(){ return (float)totalArrivalTime/numberOfClients;
    }

    public float computeAvgServiceTime(){
        return (float)totalServiceTime/numberOfClients;
    }

    @Override
    public void run() {
        int currentTime = 1;
        while (currentTime <= timeLimit && running){
            toPrint = "";
            // iterate generatedTasks list and pick tasks that have the
            // arrivalTime equal with the currentTime
            List<Task> toRemoveTasks = new ArrayList<Task>();
            for(Task randomTask : tasks){
                if(randomTask.getArrivalTime() == currentTime){
                    //   - send task to queue by calling the dispatchTask method from scheduler
                    totalArrivalTime += randomTask.getArrivalTime();
                    totalServiceTime += randomTask.getServiceTime();
                    scheduler.dispatchTask(randomTask);
                    toRemoveTasks.add(randomTask);
                }
            }
            //   - delete client from list
            tasks.removeAll(toRemoveTasks);
            // update UI frame

            System.out.println("Time " + currentTime);
            write("Time " + currentTime + "\n");
            toPrint += ("<html>Time " + currentTime + "<br>");
            System.out.print("Waiting clients: ");
            write("Waiting clients: ");
            toPrint += ("Waiting clients: ");
            taskCounter = 0;
            for(Task randomTask : tasks){
                printTask(randomTask);
                taskCounter++;
                if(taskCounter%8==0){
                    printEnter();
                }
            }
            printEnter();
            int i = 1;
            taskCounter = 0;
            for(Server randomServer : scheduler.getServers()){
                if(randomServer.getTasks().isEmpty()){
                    System.out.println("Queue " + i + ": closed");
                    write("Queue " + i + ": closed\n");
                    toPrint += ("Queue " + i + ": closed <br>");
                }
                else{
                    System.out.print("Queue " + i + ":");
                    write("Queue " + i + ":");
                    toPrint += ("Queue " + i + ":");
                    for(Task randomTask : randomServer.getTasks()){
                        taskCounter++;
                        printTask(randomTask);
                        if(taskCounter%8==0){
                            printEnter();
                        }
                    }
                    if(taskCounter > maxCounter){
                        maxCounter = taskCounter;
                        peekHour = currentTime;
                    }
                    printEnter();
                }
                i++;
            }
            System.out.println();
            write("\n");
            toPrint += "<br> </html>";
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //  wait an interval of 1 second
        }
        for(Server randomServer : scheduler.getServers())
            randomServer.setStillRun(false);
        closeFile();
        running = false;
    }

    public void printEnter(){
        System.out.println();
        write("\n");
        toPrint += "<br>";
    }

    public void printTask(Task randomTask){
        System.out.print("("+randomTask.getID()+","+randomTask.getArrivalTime()+","+randomTask.getServiceTime()+");");
        write("("+randomTask.getID()+","+randomTask.getArrivalTime()+","+randomTask.getServiceTime()+");");
        toPrint += ("("+randomTask.getID()+","+randomTask.getArrivalTime()+","+randomTask.getServiceTime()+");");
    }

    public void endSimulationFromStop(){
        for(Server randomServer : scheduler.getServers())
            randomServer.setStillRun(false);
        closeFile();
        running = false;
    }
}
