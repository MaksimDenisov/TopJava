package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.stream()
                .map(meal -> new Meal(meal.getDateTime(), meal.getDescription() + " админа", meal.getCalories()))
                .forEach(meal -> save(1, meal));

        MealsUtil.meals.forEach(meal -> save(2, meal));
    }

    @Override
    public Meal save(Integer userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.putIfAbsent(userId, new ConcurrentHashMap<>());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null)
            return null;
        return map.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(Integer userId, int id) {
        Map<Integer, Meal> map = repository.get(userId);
        if (map == null)
            return false;
        return map.remove(id) != null;
    }

    @Override
    public Meal get(Integer userId, int id) {
        return repository.getOrDefault(userId, Collections.emptyMap()).get(id);
    }

    @Override
    public List<Meal> getAll(Integer userId) {
        return repository.getOrDefault(userId, Collections.emptyMap())
                .values()
                .stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

