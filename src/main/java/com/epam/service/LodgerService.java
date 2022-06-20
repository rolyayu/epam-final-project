package com.epam.service;

import com.epam.dao.DaoException;
import com.epam.dao.postgresql.LodgerDao;
import com.epam.dao.postgresql.RequestDao;
import com.epam.entity.Lodger;
import com.epam.entity.Request;
import com.epam.entity.WorkScale;
import com.epam.entity.WorkType;
import com.epam.service.exception.ServiceException;
import com.epam.service.exception.TransactionException;

import java.util.List;

public class LodgerService {

    private LodgerDao lodgerDao;
    private RequestDao requestDao;

    private Transaction transaction;

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public void setLodgerDao(LodgerDao dao) {
        this.lodgerDao = dao;
    }

    public Long sendRequest(WorkScale scale, int timeToDo, WorkType type, Lodger sender) throws ServiceException {
        Request request = new Request();
        request.setWorkScale(scale);
        request.setTimeToDo(timeToDo);
        request.setWorkType(type);
        request.setLodger(sender);
        request.setInProcess(false);
        try {
            return requestDao.create(request);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Lodger> findAll() throws ServiceException {
        try {
            return lodgerDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Lodger findById(Long id) throws ServiceException {
        try {
            return lodgerDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Long save(Lodger lodger) throws ServiceException {
        try {
            return lodgerDao.create(lodger);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean delete(Long id) throws ServiceException {
        try {
            return lodgerDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
//        try {
//            transaction.start();
//            try {
//                boolean isDeleted = lodgerDao.delete(id);
//                transaction.commit();
//                return isDeleted;
//            } catch (DaoException exception) {
//                try {
//                    transaction.rollback();
//                } catch (TransactionException e) {
//                    throw new TransactionException(e);
//                }
//            } catch (TransactionException e) {
//                throw new ServiceException(e);
//            }
//        } catch (TransactionException e) {
//            throw new ServiceException(e);
//        } finally {
//            try {
//                transaction.close();
//            } catch (TransactionException e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
    }
}
