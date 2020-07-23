package ru.javawebinar.topjava.web;

import com.sun.net.httpserver.HttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController {

    @Autowired
    private MealService mealService;


    @GetMapping("")
    public String getAll(Model model) {
            model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
            return "meals";

    }

    @GetMapping(params = "startDate")
    public String getAllWithFilters(Model model, @RequestParam(value = "startDate") String startDate,
                                                 @RequestParam(value = "endDate") String endDate) {

        if (!(startDate == null && endDate == null)) {

            LocalDate localStartDate = parseLocalDate(startDate);
            LocalDate localEndDate = parseLocalDate(endDate);
            model.addAttribute("meals", MealsUtil.getTos(mealService.getBetweenInclusive(localStartDate, localEndDate,
                                                                    SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
            return "meals";
        } else {
            model.addAttribute("meals", MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay()));
            return "meals";
        }
    }

    @GetMapping(value = "/delete/{id}")
    public String delete(@PathVariable(value = "id") String id) {
        mealService.delete(Integer.parseInt(id), SecurityUtil.authUserId());
        return "redirect:/meals";

    }


    @GetMapping(value = "/update/{id}")
    public ModelAndView update(@PathVariable(value = "id") String id) {
        ModelAndView modelAndView = new ModelAndView();
        Meal meal = mealService.get(Integer.parseInt(id), SecurityUtil.authUserId());
        modelAndView.addObject("meal", meal);
        modelAndView.setViewName("mealForm");
        return modelAndView;

    }
    @PostMapping(value = "/update/{id}")
    public String update(@PathVariable("id") String id,
                         @RequestParam ("dateTime") String dateTime,
                         @RequestParam ("description") String description,
                         @RequestParam ("calories") String calories)
            {
        Meal meal = mealService.get(Integer.parseInt(id), SecurityUtil.authUserId());

        meal.setDateTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        meal.setDescription(description);
        meal.setCalories(Integer.parseInt(calories));
        mealService.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";

    }

    @GetMapping(value = "/create")
    public ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView();
       // modelAndView.addObject("meal", new Meal());
        modelAndView.setViewName("mealForm");
        return modelAndView;

    }

    @PostMapping(value = "/create")
    public String update(@RequestParam ("dateTime") String dateTime,
                         @RequestParam ("description") String description,
                         @RequestParam ("calories") String calories)
    {
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        meal.setDescription(description);
        meal.setCalories(Integer.parseInt(calories));
        mealService.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";

    }

}
