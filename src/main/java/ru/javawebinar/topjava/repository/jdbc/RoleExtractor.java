package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class RoleExtractor implements ResultSetExtractor<Map<Integer, List<Role>>> {

    @Override
    public Map<Integer, List<Role>> extractData(ResultSet rs)
            throws SQLException, DataAccessException {
        Map<Integer, List<Role>> data = new LinkedHashMap<>();
        while (rs.next()) {
            Integer userId = rs.getInt("user_id");
            data.putIfAbsent(userId, new ArrayList<>());
            Role role = Role.valueOf(rs.getString("role"));
            data.get(userId).add(role);
        }
        return data;
    }
}
