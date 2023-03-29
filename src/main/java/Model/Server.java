package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod = new AtomicInteger();
    private boolean stillRun;

    public void setStillRun(boolean stillRun) {
        this.stillRun = stillRun;
    }

    public int getWaitingPeriod() {
        return waitingPeriod.intValue();
    }

    public void setWaitingPeriod(int waitingPeriod) {
        this.waitingPeriod.set(waitingPeriod);
    }

    public Server(int maxTasks){
        //initialize queue and waitingPeriod
        tasks = new LinkedBlockingQueue<Task>();
        setWaitingPeriod(0);
        stillRun = false;
    }

    public void addTask(Task newTask){
        //add task to queue + increment the waitingPeriod
        tasks.add(newTask);
        int newWaitingPeriod = waitingPeriod.intValue() + newTask.getServiceTime();
        waitingPeriod.set(newWaitingPeriod);
    }

    public void run(){

        //take next task from queue
        // stop the thread for 1 s
        //decrement serviceTime + waitingPeriod

        stillRun = true;
        while(stillRun){
        try{
            if(!tasks.isEmpty()){
                Task currentTask = tasks.peek();
                Thread.sleep(1000);
                currentTask.decrementServiceTime();
                waitingPeriod.decrementAndGet();
                if(currentTask.getServiceTime() == 0){
                    tasks.take();
                }
            }
        }
         catch(InterruptedException e){
            e.printStackTrace();
        }
        }
    }

    public BlockingQueue<Task> getTasks(){
        return tasks;
    }

    public int getNumberOfTasks(){
        return tasks.size();
    }
}