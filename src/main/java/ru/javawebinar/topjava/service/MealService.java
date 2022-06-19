package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    private static void throwNotFoundException(int userId, int id) {
        throw new NotFoundException(String.format("There is no meal with id = %d for user with id  = %d", userId, id));
    }

    public List<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int userId, int id) throws NotFoundException {
        Meal meal = repository.get(userId, id);
        if (meal == null)
            throwNotFoundException(userId, id);
        return meal;
    }

    public void delete(int userId, int id) {
        if (!repository.delete(userId, id)) {
            throwNotFoundException(userId, id);
        }
    }

    public void save(int userId, Meal meal) {
        repository.save(userId, meal);
    }
}