package com.epam.service;

import com.epam.dao.DaoException;
import com.epam.dao.postgresql.BrigadeDao;
import com.epam.dao.postgresql.WorkPlanDao;
import com.epam.entity.Entity;
import com.epam.entity.Request;
import com.epam.entity.WorkPlan;
import com.epam.service.exception.ServiceException;

import java.util.List;

public class WorkPlanService {
    private WorkPlanDao workPlanDao;

    private BrigadeDao brigadeDao;

    private Transaction transaction;

    public void setBrigadeDao(BrigadeDao brigadeDao) {
        this.brigadeDao = brigadeDao;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setWorkPlanDao(WorkPlanDao workPlanDao) {
        this.workPlanDao = workPlanDao;
    }

    public List<WorkPlan> readAll() throws ServiceException {
        try {
            return workPlanDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public WorkPlan readById(Long id) throws ServiceException {
        try {
            return workPlanDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void save(WorkPlan plan) throws ServiceException {
        try {
            workPlanDao.update(plan);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean delete(WorkPlan plan) throws ServiceException {
        try {
            plan.getBrigade().setBusy(false);
            brigadeDao.update(plan.getBrigade());
            return workPlanDao.delete(plan.getId());
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<WorkPlan> getPlansByRequests(List<Request> requests) throws ServiceException{
        try {
            List<Long> requestsId = requests.stream()
                    .map(Entity::getId)
                    .toList();
            return workPlanDao.readAll()
                    .stream()
                    .filter(plan -> requestsId.contains(plan.getRequest().getId()))
                    .toList();
        } catch (DaoException e ) {
            throw new ServiceException(e);
        }
    }
}
