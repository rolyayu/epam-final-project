package com.epam.dao.postgresql;

import com.epam.dao.BaseDao;
import com.epam.dao.Dao;
import com.epam.dao.DaoException;
import com.epam.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorkPlanDao extends BaseDao implements Dao<WorkPlan> {
    @Override
    public Long create(WorkPlan plan) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("INSERT INTO work_plan (brigade_id, request_id) " +
                "VALUES (?,?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, plan.getBrigade().getId());
            statement.setLong(2, plan.getRequest().getId());
            int changedRows = statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                resultSet.next();
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public WorkPlan read(Long id) throws DaoException {
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException();
        }
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT work_plan.id, " +
                "request.id,request.scale,request.time_to_do,request.type_of_work, " +
                "lodgers.id,lodgers.name, " +
                "brigade.id,brigade.workers_id " +
                "FROM work_plan " +
                "INNER JOIN brigade on brigade.id = work_plan.brigade_id " +
                "INNER JOIN request on request.id = work_plan.request_id " +
                "INNER JOIN lodgers on lodgers.id = request.lodger_id " +
                "WHERE work_plan.id=?")) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                WorkPlan plan = new WorkPlan();
                plan.setId(resultSet.getLong(1));

                Request request = new Request();
                request.setId(resultSet.getLong(2));
                request.setWorkScale(WorkScale.valueOf(resultSet.getString(3)));
                request.setTimeToDo(resultSet.getInt(4));
                request.setWorkType(WorkType.valueOf(resultSet.getString(5)));

                Lodger lodger = new Lodger();
                lodger.setId(resultSet.getLong(6));
                lodger.setName(resultSet.getString(7));
                request.setLodger(lodger);
                plan.setRequest(request);

                Brigade brigade = new Brigade();
                brigade.setId(resultSet.getLong(8));
                brigade.setWorkers(getWorkers(resultSet.getArray(9)));
                plan.setBrigade(brigade);

                getConnection().commit();
                return plan;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean update(WorkPlan plan) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("UPDATE work_plan " +
                "SET brigade_id=?,request_id=? " +
                "WHERE \"id\"=?")) {
            statement.setLong(1, plan.getBrigade().getId());
            statement.setLong(2, plan.getRequest().getId());
            statement.setLong(3, plan.getId());
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (PreparedStatement statement = getConnection().prepareStatement("DELETE FROM work_plan " +
                "WHERE id=?")) {
            statement.setLong(1, id);
            int changedRows = statement.executeUpdate();
            return changedRows == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public List<WorkPlan> readAll() throws DaoException{
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException();
        }
        try (PreparedStatement statement = getConnection().prepareStatement("SELECT work_plan.id, " +
                "request.id,request.scale,request.time_to_do,request.type_of_work, " +
                "lodgers.id,lodgers.name, " +
                "brigade.id,brigade.workers_id " +
                "FROM work_plan " +
                "INNER JOIN brigade on brigade.id = work_plan.brigade_id " +
                "INNER JOIN request on request.id = work_plan.request_id " +
                "INNER JOIN lodgers on lodgers.id = request.lodger_id ")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                List<WorkPlan> planList = new ArrayList<>();
                while(resultSet.next()) {
                    WorkPlan plan = new WorkPlan();
                    plan.setId(resultSet.getLong(1));

                    Request request = new Request();
                    request.setId(resultSet.getLong(2));
                    request.setWorkScale(WorkScale.valueOf(resultSet.getString(3)));
                    request.setTimeToDo(resultSet.getInt(4));
                    request.setWorkType(WorkType.valueOf(resultSet.getString(5)));

                    Lodger lodger = new Lodger();
                    lodger.setId(resultSet.getLong(6));
                    lodger.setName(resultSet.getString(7));
                    request.setLodger(lodger);
                    plan.setRequest(request);

                    Brigade brigade = new Brigade();
                    brigade.setId(resultSet.getLong(8));
                    brigade.setWorkers(getWorkers(resultSet.getArray(9)));
                    plan.setBrigade(brigade);
                    planList.add(plan);
                }

                getConnection().commit();
                return planList;
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Worker> getWorkers(Array workersArray) throws DaoException {
        try {
            Long[] workersId = Arrays.stream(((Integer[]) workersArray
                            .getArray()))
                    .map(Integer::longValue)
                    .toArray(Long[]::new);
            List<Worker> workers = new ArrayList<>();
            for (Long workerId : workersId) {
                try (PreparedStatement statement = getConnection().prepareStatement("SELECT id, is_busy FROM workers WHERE id = ?", Statement.RETURN_GENERATED_KEYS)) {
                    statement.setLong(1, workerId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        resultSet.next();
                        Worker worker = new Worker();
                        worker.setId(resultSet.getLong(1));
                        worker.setBusy(Boolean.parseBoolean(resultSet.getString(2)));
                        workers.add(worker);
                    }
                } catch (SQLException e) {
                    throw new DaoException(e);
                }
            }
            return workers;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

}
