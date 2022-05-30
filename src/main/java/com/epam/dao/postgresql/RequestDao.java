package com.epam.dao.postgresql;

import com.epam.dao.BaseDao;
import com.epam.dao.Dao;
import com.epam.dao.DaoException;
import com.epam.entity.Lodger;
import com.epam.entity.Request;
import com.epam.entity.WorkScale;
import com.epam.entity.WorkType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RequestDao extends BaseDao implements Dao<Request> {
    @Override
    public boolean create(Request request) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("INSERT INTO \"request\"" +
                    "(\"id\",\"scale\",\"time_to_do\",\"type_of_work\",\"lodger_id\") " +
                    "VALUES (?,?,?,?,?)");
            statement.setLong(1,request.getId());
            statement.setString(2,request.getWorkScale().getScale());
            statement.setInt(3,request.getTimeToDo());
            statement.setString(4,request.getWorkType().getType());
            statement.setLong(5,request.getLodger().getId());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                Objects.requireNonNull(statement).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Request read(Long id) throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement("SELECT * FROM \"request\" " +
                    "JOIN lodgers l on l.id = request.lodger_id " +
                    "WHERE request.id=?",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, id);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
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
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                Objects.requireNonNull(statement).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(resultSet).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(Request request) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("UPDATE \"request\" " +
                    "SET \"scale\"=?,\"time_to_do\"=?,\"type_of_work\"=? " +
                    "WHERE \"id\"=?");
            statement.setString(1, request.getWorkScale().getScale());
            statement.setInt(2, request.getTimeToDo());
            statement.setString(3, request.getWorkType().getType());
            statement.setLong(4,request.getId());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                Objects.requireNonNull(statement).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = getConnection().prepareStatement("DELETE FROM \"request\" " +
                    "WHERE id=?");
            statement.setLong(1, id);
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                Objects.requireNonNull(statement).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public List<Request> readAll() throws DaoException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getConnection().prepareStatement("SELECT * FROM \"request\" " +
                            "JOIN lodgers l on l.id = request.lodger_id ",
                    Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            List<Request> requests = new ArrayList<>();
            while(resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getLong(1));
                request.setWorkScale(WorkScale.valueOf(resultSet.getString(2)));
                request.setTimeToDo(resultSet.getInt(3));
                request.setWorkType(WorkType.valueOf(resultSet.getString(4)));
                Lodger lodger = new Lodger();
                lodger.setId(resultSet.getLong(6));
                lodger.setName(resultSet.getString(7));
                request.setLodger(lodger);
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                Objects.requireNonNull(statement).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(resultSet).close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
