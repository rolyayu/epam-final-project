package com.epam.service;

import com.epam.dao.DaoException;
import com.epam.dao.postgresql.WorkerDao;
import com.epam.entity.Worker;
import com.epam.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class WorkerService {

    private WorkerDao workerDao;

    public void setDao(WorkerDao dao) {
        this.workerDao = dao;
    }

    public List<Worker> readAllAvailable() throws ServiceException {
        try {
            return workerDao.readAllAvailable();
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public List<Worker> readList(long[] workersId) throws ServiceException {
        try {
            List<Worker> workers = new ArrayList<>();
            for(long workerId:workersId) {
                workers.add(workerDao.read(workerId));
            }
            return workers;
        } catch (DaoException e) {
            throw new ServiceException();
        }
    }

    public void freeWorkers(List<Worker> workers) throws ServiceException {
        try {
            for(Worker worker:workers) {
                worker.setBusy(false);
                workerDao.update(worker);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
