package Domain;

public class Entity<TID> {
    private TID id;

    public Entity(TID id) {
        this.id = id;
    }

    public TID getId() {
        return id;
    }

    public void setId(TID id) {
        this.id = id;
    }
}
