class Complaint{    // this is our complaint class which have two things (description and its status).
    public String Description;
    private String Status="PENDING";
    public Complaint(String Description){
        this.Description=Description;
    }
    public void setStatus() {
        this.Status = "RESOLVED";
    }
    public String getStatus(){
        return this.Status;
    }
}