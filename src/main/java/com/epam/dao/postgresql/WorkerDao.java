package com.epam.dao.postgresql;

import com.epam.dao.BaseDao;
import com.epam.dao.Dao;
import com.epam.dao.DaoException;
import com.epam.entity.Lodger;
import com.epam.entity.Worker;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkerDao extends BaseDao implements Dao<Worker> {
    @Override
    public boolean create(Worker worker) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO workers(is_busy) " +
                "VALUES (?)")) {
            statement.setBoolean(1, worker.isBusy());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Worker read(Long id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT (id,is_busy) " +
                "FROM workers " +
                "WHERE id=?", Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, id);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                Worker worker = new Worker();
                worker.setId(resultSet.getLong(1));
                worker.setBusy(Boolean.parseBoolean(resultSet.getString(2)));
                return worker;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Worker worker) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE workers " +
                "SET is_busy=? " +
                "WHERE id=?")) {
            statement.setBoolean(1, worker.isBusy());
            statement.setLong(2, worker.getId());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("DELETE FROM workers " +
                "WHERE id=?")){
            statement.setLong(1, id);
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<Worker> readAllAvailable() throws DaoException {
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT id, is_busy " +
                    "FROM workers " +
                    "WHERE is_busy=false")) {
                List<Worker> workers = new ArrayList<>();
                while (resultSet.next()) {
                    Worker worker = new Worker();
                    worker.setId(resultSet.getLong(1));
                    worker.setBusy(resultSet.getBoolean(2));
                    workers.add(worker);
                }
                return workers;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
