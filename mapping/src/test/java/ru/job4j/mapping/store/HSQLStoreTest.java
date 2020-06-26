package ru.job4j.mapping.store;

import org.junit.After;
import org.junit.Test;
import ru.job4j.mapping.model.*;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class HSQLStoreTest {

    @After
    public void clearDB() {
        HSQLStore store = HSQLStore.instOf();
        store.findAll(Car.class).forEach(store::delete);
        store.findAll(Driver.class).forEach(store::delete);
        store.findAll(Engine.class).forEach(store::delete);
    }

    @Test
    public void whenPersist() {
        HSQLStore store = HSQLStore.instOf();
        store.persist(new Engine(200));
        assertThat(store.findAll(Engine.class).get(0).getPower(), is(200));
    }

    @Test
    public void whenUpdate() {
        HSQLStore store = HSQLStore.instOf();
        Engine engine = new Engine(200);
        store.persist(engine);
        engine.setPower(100);
        store.update(engine);
        assertThat(store.findAll(Engine.class).get(0).getPower(), is(100));
    }

    @Test
    public void whenDelete() {
        HSQLStore store = HSQLStore.instOf();
        Engine engine = new Engine(200);
        store.persist(engine);
        assertThat(store.findAll(Engine.class).size(), is(1));
        store.delete(engine);
        assertThat(store.findAll(Engine.class).size(), is(0));
    }

    @Test
    public void whenOneToOne() {
        HSQLStore store = HSQLStore.instOf();
        store.persist(
                new Car("Car", new Engine(200))
        );
        assertThat(store.findAll(Engine.class).get(0).getPower(), is(200));
        assertThat(store.findAll(Car.class).get(0).getName(), is("Car"));
    }

    @Test
    public void whenManyToMany() {
        HSQLStore store = HSQLStore.instOf();
        Car car1 = new Car("Car1", new Engine(150));
        Car car2 = new Car("Car2", new Engine(200));
        store.persist(car1);
        store.persist(car2);

        Driver driver1 = new Driver("Driver1");
        store.persist(driver1);
        driver1.addCar(car1);
        driver1.addCar(car2);
        store.update(driver1);

        Driver driver2 = new Driver("Driver2");
        store.persist(driver2);
        driver2.addCar(car1);
        driver2.addCar(car2);
        store.update(driver2);

        List<Driver> drivers = store.findAll(Driver.class);
        List<Car> cars = store.findAll(Car.class);

        assertThat(drivers.get(0).getCars().size(), is(2));
        assertThat(drivers.get(1).getCars().size(), is(2));
        assertThat(cars.get(0).getDrivers().size(), is(2));
        assertThat(cars.get(1).getDrivers().size(), is(2));
    }
}
