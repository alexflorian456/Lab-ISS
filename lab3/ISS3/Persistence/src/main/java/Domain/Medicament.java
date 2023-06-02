package Domain;

public class Medicament extends Entity<Integer>{
    private String tip;

    public Medicament(Integer id, String tip) {
        super(id);
        this.tip = tip;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
