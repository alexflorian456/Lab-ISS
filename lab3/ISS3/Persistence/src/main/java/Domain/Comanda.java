package Domain;

public class Comanda extends Entity<Integer>{
    private String deadline;
    private StatusComanda status;
    private String stringStatus;

    public Comanda(Integer id, String deadline, StatusComanda status) {
        super(id);
        this.deadline = deadline;
        this.status = status;
        if(status==StatusComanda.ONORATA) {
            stringStatus = "ONORATA";
        }
        if(status==StatusComanda.INDISPONIBIL){
            stringStatus="INDISPONIBIL";
        }
        if(status==StatusComanda.NEONORATA){
            stringStatus="NEONORATA";
        }
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public StatusComanda getStatus() {
        return status;
    }

    public void setStatus(StatusComanda status) {
        this.status = status;
    }

    public String getStringStatus() {
        return stringStatus;
    }

    public void setStringStatus(String stringStatus) {
        this.stringStatus = stringStatus;
    }
}
