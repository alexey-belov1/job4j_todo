package ru.job4j.mapping.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;
import java.util.function.Function;

public class HSQLStore {

    private final SessionFactory sf;

    private HSQLStore() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
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

    public <T> T findById(Class<T> cl, Integer id) {
        return tx(session -> session.get(cl, id));
    }

    private <T> T tx(final Function<Session, T> command) {
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
        }
    }
}