package com.epam.service;

import com.epam.dao.DaoException;
import com.epam.dao.postgresql.RequestDao;
import com.epam.entity.Entity;
import com.epam.entity.Lodger;
import com.epam.entity.Request;
import com.epam.entity.WorkPlan;
import com.epam.service.exception.ServiceException;

import java.util.List;

public class RequestService {
    private RequestDao requestDao;

    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public List<Request> readAll() throws ServiceException {
        try {
            return requestDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public Request readById(Long id) throws ServiceException {
        try {
            return requestDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean save(Request request) throws ServiceException{
        try {
            return requestDao.update(request);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public boolean delete(Long id) throws ServiceException {
        try {
            return requestDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<Request> getLodgerRequests(Long id) throws ServiceException {
        try {
            return requestDao.readAll().stream()
                    .filter(request -> request.getLodger().getId().equals(id))
                    .toList();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
