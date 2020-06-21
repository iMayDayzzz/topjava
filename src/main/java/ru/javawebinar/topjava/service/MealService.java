package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public Meal create (Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    // false if not found
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    // null if not found
    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void update (Meal meal ,int userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

}