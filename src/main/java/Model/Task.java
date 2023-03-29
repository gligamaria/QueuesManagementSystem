package Model;

public class Task {
    private Integer ID;
    private Integer arrivalTime;
    private Integer serviceTime;

    public Task(Integer arrivalTime, Integer serviceTime){
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public Integer getServiceTime() {
        return serviceTime;
    }

    public void decrementServiceTime(){
        this.serviceTime--;
    }

}
