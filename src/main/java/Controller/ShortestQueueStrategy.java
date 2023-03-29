package Controller;

import Model.Server;
import Model.Task;

import java.util.List;

public class ShortestQueueStrategy implements Strategy {

    @Override
    public void addTask(List<Server> servers, Task task) {
        int index = 0;
        Server randomServer = servers.get(0);
        for(Server server:servers){
            if(server.getNumberOfTasks() < randomServer.getNumberOfTasks()){
                index = servers.indexOf(server);
            }
        }
        servers.get(index).addTask(task);
    }
}
