package ru.job4j.cars.store;

import org.hibernate.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.FilterDB;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class HSQLStore {

    private final Semaphore sem;
    private final SessionFactory sf;

    private HSQLStore() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        sem = new Semaphore(5);
    }

    private static final HSQLStore INST = new HSQLStore();

    public static HSQLStore instOf() {
        return INST;
    }

    public <T> void persist(T model) {
        tx(session -> {
            session.persist(model);
            return null;
        });
    }

    public <T> void update(T model) {
        tx(session -> {
            session.update(model);
            return null;
        });
    }

    public <T> void delete(T model) {
        tx(session -> {
            session.delete(model);
            return null;
        });
    }

    public <T> List<T> findAll(Class<T> cl) {
        return tx(session -> session.createQuery("from " + cl.getName(), cl).list());
    }

    public <T> List<T> findWithFilter(Class<T> cl, List<FilterDB> filters) {
        return tx(session -> {
            for (FilterDB filter : filters) {
                session.enableFilter(filter.getName());
                if (filter.withParameter()) {
                    session.getEnabledFilter(filter.getName()).setParameter(filter.getParamName(), filter.getParamValue());
                }
            }
            return session.createQuery("from " + cl.getName(), cl).list();
        });
    }

    public <T> T findById(Class<T> cl, Integer id) {
        return tx(session -> session.get(cl, id));
    }

    public User findUserByEmail(String email) {
        return tx(session ->
                (User) session.createQuery("from ru.job4j.cars.model.User where email = :email")
                        .setParameter("email", email)
                        .uniqueResult()
        );
    }

    private <T> T tx(final Function<Session, T> command) {
        try {
            sem.acquire();
            Session session = sf.openSession();
            Transaction tx = session.beginTransaction();
            try {
                T result = command.apply(session);
                tx.commit();
                return result;
            } catch (final Exception e) {
                tx.rollback();
                throw e;
            } finally {
                session.close();
                sem.release();
            }
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}