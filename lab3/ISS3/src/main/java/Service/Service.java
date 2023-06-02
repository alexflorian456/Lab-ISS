package Service;

import Domain.Angajat;
import Domain.Comanda;
import Domain.ItemComanda;
import Domain.StatusComanda;
import Repository.AngajatRepo;
import Repository.ComandaRepo;
import Repository.MedicamentRepo;

import java.util.List;

public class Service {
    AngajatRepo angajatRepository;
    ComandaRepo comandaRepository;
    MedicamentRepo medicamentRepository;

    public Service(AngajatRepo angajatRepository, ComandaRepo comandaRepository, MedicamentRepo medicamentRepository) {
        this.angajatRepository = angajatRepository;
        this.comandaRepository = comandaRepository;
        this.medicamentRepository = medicamentRepository;
    }

    public Angajat login(String username, String password) {
        Angajat angajat = angajatRepository.findUserPass(username,password);
        return angajat;
    }

    public List<Comanda> getCommandsOf(Integer a_id) {
        return comandaRepository.getCommandsOf(a_id);
    }

    public List<ItemComanda> getMedsOf(Integer id) {
        return comandaRepository.getMedsOf(id);
    }

    public void removeCommand(Comanda comanda) {
        comandaRepository.remove(comanda);
    }

    public List<String> getMedTypes() {
        return medicamentRepository.getMedTypes();
    }

    public void addCommand(List<ItemComanda> modelCurrent, String deadline,Integer a_id) {
        Comanda comanda = comandaRepository.addCommand(new Comanda(0,deadline, StatusComanda.NEONORATA),a_id);
        for (ItemComanda item :
                modelCurrent) {
            medicamentRepository.addCommandItem(comanda.getId(),medicamentRepository.findIDof(item.getTip()),item.getCantitate());
        }
    }

    public void modCommand(Comanda comanda, List<ItemComanda> modelCurrent, String deadline, Integer id) {
        comandaRepository.remove(comanda);
        addCommand(modelCurrent,deadline,id);
    }

    public void onorareComanda(Comanda comanda) {
        comandaRepository.honorCommand(comanda);
    }

    public List<Comanda> getAllCommands() {
        return comandaRepository.getAll();
    }

    public List<Comanda> filterByType(String text) {
        return comandaRepository.filterByType(text);
    }

    public List<Comanda> filterByDeadline(String toString) {
        return comandaRepository.filterByDeadline(toString);
    }
}
