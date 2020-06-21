package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.MealsUtil.getTos;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<MealTo> getAll() {
        log.info("getAll");
        return getTos(service.getAll(authUserId()), DEFAULT_CALORIES_PER_DAY);
    }

    public MealTo get(int id) {
        log.info("get {}", id);
        Meal meal = service.get(id, authUserId());
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getCalories() <= DEFAULT_CALORIES_PER_DAY);
    }

    public MealTo create(Meal meal) {
        log.info("create {}", meal);
        service.create(meal, authUserId());
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getCalories() <= DEFAULT_CALORIES_PER_DAY);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal) {
        log.info("update {}", meal);
        service.update(meal, authUserId());
    }

}