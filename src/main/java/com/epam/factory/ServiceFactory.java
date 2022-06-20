package com.epam.ioc;

import com.epam.dao.postgresql.*;
import com.epam.entity.Request;
import com.epam.pool.ConnectionPool;
import com.epam.service.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ServiceFactory implements AutoCloseable {
    private Connection connection;

    private Transaction transaction;

    private LodgerDao lodgerDao;

    private LodgerService lodgerService;

    private RequestDao requestDao;

    private RequestService requestService;

    private WorkerDao workerDao;

    private WorkerService workerService;

    private BrigadeDao brigadeDao;

    private BrigadeService brigadeService;

    private WorkPlanDao workPlanDao;

    private WorkPlanService workPlanService;

    private Dispatcher dispatcher;

    public LodgerService getLodgerService() throws ServiceFactoryException {
        if (lodgerService == null) {
            LodgerService service = new LodgerService();
            service.setRequestDao(getRequestDao());
            service.setLodgerDao(getLodgerDao());
            service.setTransaction(getTransaction());
            this.lodgerService = service;
        }
        return lodgerService;
    }

    private LodgerDao getLodgerDao() throws ServiceFactoryException {
        if (lodgerDao == null) {
            LodgerDao lodgerDao = new LodgerDao();
            lodgerDao.setConnection(getConnection());
            this.lodgerDao = lodgerDao;
        }
        return this.lodgerDao;
    }

    public RequestService getRequestService() throws ServiceFactoryException {
        if (requestService == null) {
            RequestService service = new RequestService();
            service.setRequestDao(getRequestDao());
            this.requestService = service;
        }
        return requestService;
    }

    private RequestDao getRequestDao() throws ServiceFactoryException {
        if (requestDao == null) {
            RequestDao requestDao = new RequestDao();
            requestDao.setConnection(getConnection());
            this.requestDao = requestDao;
        }
        return requestDao;
    }

    public WorkerService getWorkerService() throws ServiceFactoryException {
        if (workerService == null) {
            WorkerService workerService = new WorkerService();
            workerService.setDao(getWorkerDao());
            this.workerService = workerService;
        }
        return workerService;
    }

    private WorkerDao getWorkerDao() throws ServiceFactoryException {
        if (workerDao == null) {
            WorkerDao workerDao = new WorkerDao();
            workerDao.setConnection(getConnection());
            this.workerDao = workerDao;
        }
        return workerDao;
    }

    public BrigadeService getBrigadeService() throws ServiceFactoryException {
        if (brigadeService == null) {
            BrigadeService brigadeService = new BrigadeService();
            brigadeService.setBrigadeDao(getBrigadeDao());
            this.brigadeService = brigadeService;
        }
        return brigadeService;
    }

    private BrigadeDao getBrigadeDao() throws ServiceFactoryException {
        if (brigadeDao == null) {
            BrigadeDao brigadeDao = new BrigadeDao();
            brigadeDao.setConnection(getConnection());
            this.brigadeDao = brigadeDao;
        }
        return brigadeDao;
    }

    public WorkPlanService getWorkPlanService() throws ServiceFactoryException {
        if (workPlanService == null) {
            WorkPlanService workPlanService = new WorkPlanService();
            workPlanService.setBrigadeDao(getBrigadeDao());
            workPlanService.setWorkPlanDao(getWorkPlanDao());
            this.workPlanService = workPlanService;
        }
        return workPlanService;
    }

    private WorkPlanDao getWorkPlanDao() throws ServiceFactoryException {
        if (workPlanDao == null) {
            WorkPlanDao workPlanDao = new WorkPlanDao();
            workPlanDao.setConnection(getConnection());
            this.workPlanDao = workPlanDao;
        }
        return workPlanDao;
    }

    public Dispatcher getDispatcher() throws ServiceFactoryException {
        if (dispatcher == null) {
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setBrigadeDao(getBrigadeDao());
            dispatcher.setWorkerDao(getWorkerDao());
            dispatcher.setWorkPlanDao(getWorkPlanDao());
            dispatcher.setTransaction(getTransaction());
            dispatcher.setWorkerService(getWorkerService());
            this.dispatcher = dispatcher;
        }
        return dispatcher;
    }

    private Connection getConnection() throws ServiceFactoryException {
        if (connection == null) {
            try {
                connection = ConnectionPool.getConnection();
            } catch (SQLException e) {
                throw new ServiceFactoryException(e);
            }
        }
        return connection;
    }

    private Transaction getTransaction() throws ServiceFactoryException {
        if (transaction == null) {
            transaction = new Transaction();
            transaction.setConnection(getConnection());
        }
        return transaction;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}
