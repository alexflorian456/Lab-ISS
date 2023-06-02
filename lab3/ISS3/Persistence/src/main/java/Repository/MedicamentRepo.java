package Repository;

import Domain.Medicament;

import java.util.List;

public interface MedicamentRepo extends Repository<Integer, Medicament> {
    List<String> getMedTypes();

    Integer findIDof(String tip);

    void addCommandItem(Integer id, Integer iDof, Integer cantitate);
}
