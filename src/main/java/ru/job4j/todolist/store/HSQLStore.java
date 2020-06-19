package ru.job4j.todolist.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todolist.model.Item;

import java.util.ArrayList;
import java.util.List;

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
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(item);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return item;
    }

    public void update(Item item) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.update(item);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void delete(Integer id) {
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Item item = new Item(null);
            item.setId(id);
            session.delete(item);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            result = session.createQuery("from ru.job4j.todolist.model.Item").list();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }

    public Item findById(Integer id) {
        Item result = null;
        Session session = sf.openSession();
        Transaction tx = session.beginTransaction();
        try {
            result = session.get(Item.class, id);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return result;
    }
}