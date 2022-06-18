package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    MealsDao dao = new MealsDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("meals", MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.DEFAULT_CALORIES_PER_DAY));
            log.debug("Forward to meals.jsp");
            forward = "/meals.jsp";
        } else if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            log.debug("delete id  = " + id);
            dao.delete(id);
            log.debug("redirect  to meals");
            response.sendRedirect("./meals");
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            log.debug("edit id  = " + id);
            request.setAttribute("meal", dao.get(id));
            log.debug("redirect  to ./mealForm.jsp");
            forward = "./mealForm.jsp";
        } else if (action.equalsIgnoreCase("add")) {
            request.setAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 100));
            log.debug("add redirect  to ./mealForm.jsp");
            forward = "./mealForm.jsp";
        } else {
            response.sendRedirect("meals");
            return;
        }
        request.getRequestDispatcher(forward).forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        Integer calories = Integer.parseInt(request.getParameter("calories"));
        if (request.getParameter("id").isEmpty()) {
            dao.add(new Meal(dateTime, description, calories));
        } else {
            Integer id = Integer.parseInt(request.getParameter("id"));
            Meal meal = new Meal(dateTime, description, calories);
            meal.setId(id);
            dao.update(id, meal);
        }
        response.sendRedirect("./meals");
    }
}
