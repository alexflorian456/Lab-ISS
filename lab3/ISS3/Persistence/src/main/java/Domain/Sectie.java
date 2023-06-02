package Domain;

public class Sectie extends Entity<Integer>{
    private String nume;

    public Sectie(Integer id, String nume) {
        super(id);
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}
