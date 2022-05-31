package com.epam.dao.postgresql;

import com.epam.dao.BaseDao;
import com.epam.dao.Dao;
import com.epam.dao.DaoException;
import com.epam.entity.Brigade;
import com.epam.entity.Entity;
import com.epam.entity.Lodger;
import com.epam.entity.Worker;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class BrigadeDao extends BaseDao implements Dao<Brigade> {
    @Override
    public boolean create(Brigade brigade) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO \"brigade\"(\"workers_id\") " +
                "VALUES (?)")) {
            Array workers = getConnection().createArrayOf("int", brigade.getWorkersId());
            statement.setArray(1, workers);
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Brigade read(Long id) throws DaoException {
        PreparedStatement statement = null;
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        try {
            statement = getConnection().prepareStatement("SELECT id, workers_id " +
                            "FROM brigade " +
                            "WHERE id = ?",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1,id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                Brigade brigade = new Brigade();
                brigade.setId(resultSet.getLong(1));
                Long[] workersId = Arrays.stream(((Integer[]) resultSet
                        .getArray(2)
                        .getArray()))
                        .map(Integer::longValue)
                        .toArray(Long[]::new);
                List<Worker> currentBrigadeWorkers = new ArrayList<>();
                for(Long worker: workersId) {
                    currentBrigadeWorkers.add(readCurrentWorker(worker));
                }
                brigade.setWorkers(currentBrigadeWorkers);
                getConnection().commit();
                return brigade;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                Objects.requireNonNull(statement).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(Brigade brigade) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE \"brigade\" " +
                "SET \"workers_id\"=? " +
                "WHERE \"id\"=?")) {
            statement.setArray(1, getConnection().createArrayOf("int", brigade.getWorkersId()));
            statement.setLong(2, brigade.getId());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("DELETE FROM \"brigade\" " +
                "WHERE id=?")) {
            statement.setLong(1, id);
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Worker readCurrentWorker(Long id) throws DaoException{
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT id, is_busy FROM workers WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
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
}
