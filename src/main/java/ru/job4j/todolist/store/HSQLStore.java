package ru.job4j.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todolist.model.Item;

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

    public Item create(Item item) {
        tx(session -> session.save(item));
        return item;
    }

    public void update(Item item) {
        tx(session -> {
            session.update(item);
            return null;
        });
    }

    public void delete(Integer id) {
        tx(session -> {
            Item item = new Item(null);
            item.setId(id);
            session.delete(item);
            return null;
        });
    }

    public List<Item> findAll() {
        return tx(session -> session.createQuery("from ru.job4j.todolist.model.Item").list());
    }

    public Item findById(Integer id) {
        return tx(session -> session.get(Item.class, id));
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