package Controller;

import Model.Server;
import Model.Task;

import javax.swing.plaf.TableHeaderUI;
import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxTasksPerServer){
        //for maxNoServers
        // - create server objects
        // - create thread with the object
        servers = new ArrayList<Server>();
        int i = 0;
        while(i < maxNoServers){
            servers.add(new Server(maxTasksPerServer));
            Thread thread = new Thread(servers.get(i));
            thread.start();
            i++;
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ShortestQueueStrategy();
        }
        else strategy = new TimeStrategy();
    }

    public void dispatchTask(Task task){
        //call the strategy addTask method
        strategy.addTask(servers,task);
    }

    public List<Server> getServers(){
        return servers;
    }
}
