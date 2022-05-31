package com.epam.dao.postgresql;

import com.epam.dao.BaseDao;
import com.epam.dao.Dao;
import com.epam.dao.DaoException;
import com.epam.entity.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestDao extends BaseDao implements Dao<Request> {
    @Override
    public Long create(Request request) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO request" +
                "(scale,time_to_do,type_of_work,lodger_id) " +
                "VALUES (?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, request.getWorkScale().getScale());
            statement.setInt(2, request.getTimeToDo());
            statement.setString(3, request.getWorkType().getType());
            statement.setLong(4, request.getLodger().getId());
            int changedRows = statement.executeUpdate();
            try(ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Request read(Long id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT * " +
                        "FROM request " +
                        "JOIN lodgers l on l.id = request.lodger_id " +
                        "WHERE request.id=?",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                Request request = new Request();
                request.setId(resultSet.getLong(1));
                request.setWorkScale(WorkScale.valueOf(resultSet.getString(2)));
                request.setTimeToDo(resultSet.getInt(3));
                request.setWorkType(WorkType.valueOf(resultSet.getString(4)));
                Lodger lodger = new Lodger();
                lodger.setId(resultSet.getLong(6));
                lodger.setName(resultSet.getString(7));
                request.setLodger(lodger);
                return request;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Request request) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE request " +
                "SET scale=?,time_to_do=?,type_of_work=? " +
                "WHERE id=?")) {
            statement.setString(1, request.getWorkScale().getScale());
            statement.setInt(2, request.getTimeToDo());
            statement.setString(3, request.getWorkType().getType());
            statement.setLong(4, request.getId());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("DELETE FROM request " +
                "WHERE id=?")) {
            statement.setLong(1, id);
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public List<Request> readAll() throws DaoException {
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT " +
                    "request.id, request.scale, request.time_to_do,request.type_of_work, request.lodger_id,lodgers.name " +
                    "FROM request " +
                    "JOIN lodgers on lodgers.id = request.lodger_id")) {

                List<Request> requests = new ArrayList<>();
                while (resultSet.next()) {
                    Request request = new Request();
                    request.setId(resultSet.getLong(1));
                    request.setWorkScale(WorkScale.valueOf(resultSet.getString(2)));
                    request.setTimeToDo(resultSet.getInt(3));
                    request.setWorkType(WorkType.valueOf(resultSet.getString(4)));
                    Lodger lodger = new Lodger();
                    lodger.setId(resultSet.getLong(5));
                    lodger.setName(resultSet.getString(6));
                    request.setLodger(lodger);
                    requests.add(request);
                }
                return requests;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
