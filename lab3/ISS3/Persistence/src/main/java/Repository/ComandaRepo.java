package Repository;

import Domain.Comanda;
import Domain.ItemComanda;

import java.util.List;

public interface ComandaRepo extends Repository<Integer, Comanda> {

    List<Comanda> getCommandsOf(Integer a_id);

    List<ItemComanda> getMedsOf(Integer id);

    Comanda addCommand(Comanda comanda, Integer a_id);

    void honorCommand(Comanda comanda);

    List<Comanda> filterByType(String text);

    List<Comanda> filterByDeadline(String toString);
}
