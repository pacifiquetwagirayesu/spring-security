package com.pacifique.security.review.proxies;

import com.pacifique.security.review.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface IEmailProxies {

    Logger log = LoggerFactory.getLogger(IEmailProxies.class);

    void accountActivityNotification(User user);


    default void submitTaskForExecution(Callable<String> task, ExecutorService executorService) {
        try {
            Future<String> submitted = executorService.submit(task);
            log.info("Task submitted, and responded with {} 🤷🏻‍♂️", submitted.get());
        } catch (ExecutionException | InterruptedException e) {
            log.info("Error while submitting task", e);
        } finally {
            executorService.shutdown();
        }
    }
}
