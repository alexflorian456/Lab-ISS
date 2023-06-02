package Repository.ORM;

import Domain.Angajat;
import Repository.AngajatRepo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class AngajatORMRepo implements AngajatRepo {
    static SessionFactory sessionFactory;

    public AngajatORMRepo(){
        /*sessionFactory = ORMUtils.getSessionFactory();*/
        sessionFactory = initialize();
    }

    private SessionFactory initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            return new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            System.err.println("Exception "+e);
            StandardServiceRegistryBuilder.destroy( registry );
        }
        return null;
    }

    @Override
    public Angajat findUserPass(String username, String password) {
        Angajat angajat = new Angajat();
        try(Session session = sessionFactory.openSession()){
            Transaction tx = null;
            try{
                tx = session.beginTransaction();
                Query query = session.createQuery("from Angajat where username=:usernameParam and password=:passwordParam");
                query.setParameter("usernameParam",username);
                query.setParameter("passwordParam",password);
                List<Angajat> angajats = query.list();
                angajat = angajats.get(0);
                tx.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return angajat;
    }

    @Override
    public Angajat findID(Integer id) {
        return null;
    }

    @Override
    public List<Angajat> getAll() {
        return null;
    }

    @Override
    public Angajat add(Angajat angajat) {
        return null;
    }

    @Override
    public Angajat remove(Angajat angajat) {
        return null;
    }

    @Override
    public Angajat update(Angajat angajat) {
        return null;
    }
}
