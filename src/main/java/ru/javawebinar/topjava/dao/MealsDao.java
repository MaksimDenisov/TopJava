package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDao {
    private Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::add);
    }

    public Meal add(Meal meal) {
        meal.setId(counter.incrementAndGet());
        meals.put(meal.getId(), meal);
        return meal;
    }

    public Meal get(Integer id) {
        return meals.get(id);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    public Meal update(Integer id, Meal meal) {
        return meals.computeIfPresent(id, (oldValue, newValue) -> meal);
    }

    public void delete(Integer id) {
        meals.remove(id);
    }
}
