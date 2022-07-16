package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final int MEAL_1_ID = 100003;

    public static final LocalDate FIRST_DAY = LocalDate.of(2020, Month.JANUARY, 30);
    public static final LocalDate SECOND_DAY = LocalDate.of(2020, Month.JANUARY, 31);


    public static final Meal MEALS_1 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEALS_2 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEALS_3 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEALS_4 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEALS_5 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEALS_6 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEALS_7 = new Meal(100009, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final Meal ADMIN_MEALS_1 = new Meal(100010, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal ADMIN_MEALS_2 = new Meal(100011, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal ADMIN_MEALS_3 = new Meal(100012, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal ADMIN_MEALS_4 = new Meal(100013, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal ADMIN_MEALS_5 = new Meal(100014, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal ADMIN_MEALS_6 = new Meal(100015, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal ADMIN_MEALS_7 = new Meal(100016, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static final List<Meal> USER_MEALS = Arrays.asList(MEALS_7, MEALS_6, MEALS_5, MEALS_4, MEALS_3, MEALS_2, MEALS_1);
    public static final List<Meal> ADMIN_MEALS = Arrays.asList(ADMIN_MEALS_7, ADMIN_MEALS_6, ADMIN_MEALS_5, ADMIN_MEALS_4, ADMIN_MEALS_3, ADMIN_MEALS_2, ADMIN_MEALS_1);


    public static Meal getUpdated() {
        Meal updated = new Meal(MEALS_1);
        updated.setDateTime(LocalDateTime.of(2020, Month.FEBRUARY, 1, 0, 0));
        updated.setDescription("Updated");
        updated.setCalories(330);
        return updated;
    }

    public static Meal getNew() {
        return new Meal(LocalDateTime.of(2021, Month.JANUARY, 1, 0, 0), "Новая еда", 1000);
    }


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }
}