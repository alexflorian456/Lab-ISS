package Domain;

public class ItemComanda extends Entity<Integer>{
    private Integer cantitate;
    private String tip;

    public ItemComanda(Integer id, Integer cantitate, String tip) {
        super(id);
        this.cantitate = cantitate;
        this.tip = tip;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }
}
