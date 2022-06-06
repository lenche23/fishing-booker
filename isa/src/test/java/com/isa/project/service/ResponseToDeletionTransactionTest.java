package com.isa.project.service;

import com.isa.project.model.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ResponseToDeletionTransactionTest {

    @Autowired
    public ResponseToDeletionTransactionService responseToDeletionTransactionService;

    @Autowired
    public AppUserService appUserService;


    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingForApproveDeletionRequest() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                DeletionRequest request = createDeletionRequest();
                AppUser user = appUserService.findByEmail(request.getUserEmail());
                try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                responseToDeletionTransactionService.responseToApproveDeletionTransactional(request.getId(), user.getId());
            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                DeletionRequest request = createDeletionRequest();
                AppUser user = appUserService.findByEmail(request.getUserEmail());
                responseToDeletionTransactionService.responseToApproveDeletionTransactional(request.getId(), user.getId());
            }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testOptimisticLockingForDeclineDeletionRequest() throws Throwable {

        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<?> future1 = executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 1");
                ResponseToDeletionRequest response = createDeclinedResponseToDeletionRequest();
                try { Thread.sleep(3000); } catch (InterruptedException e) {}// thread uspavan na 3 sekunde da bi drugi thread mogao da izvrsi istu operaciju
                responseToDeletionTransactionService.responseToDeclineDeletionTransactional(response, response.getDeletionRequest().getId());
            }
        });
        executor.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("Startovan Thread 2");
                ResponseToDeletionRequest response = createDeclinedResponseToDeletionRequest();
                responseToDeletionTransactionService.responseToDeclineDeletionTransactional(response, response.getDeletionRequest().getId());
            }
        });
        try {
            future1.get(); // podize ExecutionException za bilo koji izuzetak iz prvog child threada
        } catch (ExecutionException e) {
            System.out.println("Exception from thread " + e.getCause().getClass()); // u pitanju je bas ObjectOptimisticLockingFailureException
            throw e.getCause();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    private DeletionRequest createDeletionRequest() {
        AppUser user = appUserService.findOne(1L);
        String userEmail = user.getEmail();

        DeletionRequest deletionRequest = new DeletionRequest();
        deletionRequest.setResponseToDeletionRequest(null);
        deletionRequest.setDateSubmitted(new Date());
        deletionRequest.setApproved(false);
        deletionRequest.setExplanation("Molim te me obrisi");
        deletionRequest.setUserEmail(userEmail);

        return deletionRequest;
    }

    private ResponseToDeletionRequest createDeclinedResponseToDeletionRequest() {
        Administrator admin = (Administrator) appUserService.findOne(1L);

        ResponseToDeletionRequest responseToDeletionRequest = new ResponseToDeletionRequest();
        responseToDeletionRequest.setDeletionRequest(createDeletionRequest());
        responseToDeletionRequest.setApproved(false);
        responseToDeletionRequest.setDateSubmitted(new Date());
        responseToDeletionRequest.setAdministrator(admin);
        responseToDeletionRequest.setContent("Ne mogu da te obrisem");

        return responseToDeletionRequest;
    }

}
