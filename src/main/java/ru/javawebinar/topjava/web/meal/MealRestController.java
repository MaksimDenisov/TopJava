package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.util.List;

@Controller
public class MealRestController {

    private final  MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void save(int userId, Meal meal) {
        service.save(userId, meal);
    }

    public void delete(int userId, int id) {
        service.delete(userId, id);
    }

    public Meal get(int userId, int id) {
        return service.get(userId, id);
    }

    public List<MealTo> getAll(int userId) {
        return service.getAll(userId);
    }
}