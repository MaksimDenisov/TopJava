package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    @Autowired
    private MealService service;

    @Test
    public void testGet() {
        assertMatch(service.get(MEAL_1_ID, UserTestData.USER_ID), MEALS_1);
    }

    @Test
    public void testGetNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void testDelete() {
        service.delete(MEAL_1_ID, UserTestData.USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1_ID, UserTestData.USER_ID));
    }

    @Test
    public void testDeleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1_ID, UserTestData.ADMIN_ID));
    }

    @Test
    public void testGetBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(FIRST_DAY, FIRST_DAY, UserTestData.USER_ID), MEALS_3, MEALS_2, MEALS_1);
        assertMatch(service.getBetweenInclusive(SECOND_DAY, SECOND_DAY, UserTestData.USER_ID), MEALS_7, MEALS_6, MEALS_5, MEALS_4);
    }

    @Test
    public void testGetAll() {
        assertMatch(service.getAll(UserTestData.USER_ID), USER_MEALS);
    }

    @Test
    public void testUpdate() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_1_ID, USER_ID), getUpdated());
    }

    @Test
    public void testUpdateNotOwn() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));

    }

    @Test
    public void testCreate() {
        Meal created = service.create(getNew(), USER_ID);
        Meal newMeal = getNew();
        Integer createdId = created.getId();
        newMeal.setId(createdId);
        assertMatch(created, newMeal);
        assertMatch(service.get(createdId, USER_ID), newMeal);
    }

    @Test
    public void testDuplicateDateTimeCreate() {
        Meal newMeal = getNew();
        newMeal.setDateTime(MEALS_1.getDateTime());
        assertThrows(DuplicateKeyException.class, () -> service.update(newMeal, USER_ID));
    }
}
