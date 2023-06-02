package Repository;


import Domain.Angajat;

public interface AngajatRepo extends Repository<Integer, Angajat> {
    public Angajat findUserPass(String username, String password);
}
