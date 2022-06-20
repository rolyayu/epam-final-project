package com.epam.service;

import com.epam.dao.DaoException;
import com.epam.dao.postgresql.BrigadeDao;
import com.epam.dao.postgresql.WorkPlanDao;
import com.epam.dao.postgresql.WorkerDao;
import com.epam.entity.Brigade;
import com.epam.entity.Request;
import com.epam.entity.WorkPlan;
import com.epam.entity.Worker;
import com.epam.service.exception.NotEnoughWorkersException;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.TransactionException;

import java.util.ArrayList;
import java.util.List;

public class Dispatcher {
    private WorkerDao workerDao;

    private BrigadeDao brigadeDao;

    private WorkPlanDao workPlanDao;

    private Transaction transaction;

    private WorkerService workerService;

    public void setWorkerService(WorkerService workerService) {
        this.workerService = workerService;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setWorkerDao(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    public void setBrigadeDao(BrigadeDao brigadeDao) {
        this.brigadeDao = brigadeDao;
    }

    public void setWorkPlanDao(WorkPlanDao workPlanDao) {
        this.workPlanDao = workPlanDao;
    }

    private Brigade formatBrigade(Request request) throws ServiceException {
        try {
            transaction.start();
            List<Worker> availableWorkers = workerDao.readAllAvailable();
            int neededWorkers = request.getWorkScale().getNeededWorkers();
            if(neededWorkers>availableWorkers.size()) {
                throw new NotEnoughWorkersException();
            } else {
                Brigade brigade = new Brigade();
                List<Worker> brigadeWorkers = new ArrayList<>();
                for (int i = 0; i < neededWorkers; i++) {
                    Worker currentAvailableWorker = availableWorkers.get(i);
                    brigadeWorkers.add(currentAvailableWorker);
                    currentAvailableWorker.setBusy(true);
                    workerDao.update(currentAvailableWorker);
                }
                brigade.setWorkers(brigadeWorkers);
                brigade.setBusy(true);
                Long brigadeId = brigadeDao.create(brigade);
                brigade.setId(brigadeId);
                transaction.commit();
                return brigade;
            }
        } catch (DaoException | TransactionException e) {
            throw new ServiceException(e);
        } catch (NotEnoughWorkersException e) {
            try {
                transaction.rollback();
                throw new NotEnoughWorkersException(e);
            } catch (TransactionException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                transaction.close();
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        }
    }

    public Long formatWorkPlan(Request request) throws ServiceException{
        try {
            WorkPlan plan = new WorkPlan();
            plan.setDone(false);
            List<Brigade> availableBrigades = brigadeDao.readAll()
                    .stream()
                    .filter(brigade -> !brigade.isBusy())
                    .filter(brigade -> brigade.getWorkers().size()==request.getWorkScale().getNeededWorkers())
                    .toList();
            if(!availableBrigades.isEmpty()) {
                Brigade forCurrentRequest = availableBrigades.get(0);
                forCurrentRequest.setBusy(true);
                brigadeDao.update(forCurrentRequest);
                plan.setBrigade(forCurrentRequest);
            } else {
                plan.setBrigade(formatBrigade(request));
            }
            plan.setRequest(request);
            request.setInProcess(true);
            return workPlanDao.create(plan);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void completeWorkPlan(WorkPlan planToComplete) throws ServiceException {
        try {
            Brigade brigade = planToComplete.getBrigade();
            planToComplete.setDone(true);
            brigade.setBusy(false);
            brigadeDao.update(brigade);
            this.workPlanDao.update(planToComplete);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
