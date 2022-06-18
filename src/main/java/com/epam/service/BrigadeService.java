package com.epam.service;

import com.epam.dao.Dao;
import com.epam.dao.DaoException;
import com.epam.dao.postgresql.BrigadeDao;
import com.epam.dao.postgresql.WorkerDao;
import com.epam.entity.Brigade;
import com.epam.entity.Worker;
import com.epam.service.exception.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class BrigadeService {
    private BrigadeDao brigadeDao;
    private WorkerDao workerDao;

    public void setWorkerDao(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    public void setBrigadeDao(BrigadeDao brigadeDao) {
        this.brigadeDao = brigadeDao;
    }

    public Brigade getById(Long id) throws ServiceException {
        try {
           Brigade brigade = brigadeDao.read(id);
           List<Worker> brigadeWorkers = new ArrayList<>();
           for(long workerId:brigade.getWorkersId()) {
               brigadeWorkers.add(workerDao.read(workerId));
           }
           brigade.setWorkers(brigadeWorkers);
           return brigade;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Brigade> readAll() throws ServiceException{
        try {
            return brigadeDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
