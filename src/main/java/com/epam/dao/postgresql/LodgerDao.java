package com.epam.dao.postgresql;

import com.epam.dao.BaseDao;
import com.epam.dao.Dao;
import com.epam.dao.DaoException;
import com.epam.entity.Lodger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LodgerDao extends BaseDao implements Dao<Lodger> {
    @Override
    public boolean create(Lodger lodger) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO \"lodgers\"(\"name\") " +
                "VALUES (?)")) {
            statement.setString(1, lodger.getName());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Lodger read(Long id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT (\"id\",\"name\") " +
                "FROM \"lodgers\" " +
                "WHERE id=?",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                Lodger lodger = new Lodger();
                lodger.setId(resultSet.getLong(1));
                lodger.setName(resultSet.getString(2));
                return lodger;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Lodger lodger) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE \"lodgers\" " +
                "SET \"name\"=? " +
                "WHERE \"id\"=?")) {
            statement.setString(1, lodger.getName());
            statement.setLong(2, lodger.getId());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("DELETE FROM \"lodgers\" " +
                "WHERE id=?")) {
            statement.setLong(1, id);
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    public List<Lodger> readAll() throws DaoException {
        try (Statement statement =getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT \"id\", \"name\" " +
                    "FROM \"lodgers\"")) {
                List<Lodger> lodgers = new ArrayList<>();
                while (resultSet.next()) {
                    Lodger lodger = new Lodger();
                    lodger.setId(resultSet.getLong("id"));
                    lodger.setName(resultSet.getString("name"));
                    lodgers.add(lodger);
                }
                return lodgers;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
