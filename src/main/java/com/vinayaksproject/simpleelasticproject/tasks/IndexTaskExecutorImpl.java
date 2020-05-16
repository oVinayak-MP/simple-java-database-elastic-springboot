/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vinayaksproject.simpleelasticproject.tasks;

import com.vinayaksproject.simpleelasticproject.dao.IndexTaskDAO;
import com.vinayaksproject.simpleelasticproject.enums.JobStatus;
import com.vinayaksproject.simpleelasticproject.tasks.exceptions.TaskSuccessfulException;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;

/**This is an implementation class for IndexTaskExecutor
 *
 * @author vinayak
 */
public class IndexTaskExecutorImpl implements IndexTaskExecutor {
@Autowired
IndexTaskDAO indexTaskDAO;
Taskable task;
private boolean started;
private long executiontime;
com.vinayaksproject.simpleelasticproject.entity.IndexTaskEntry taskMetaData;
    public  IndexTaskExecutorImpl(Taskable task, com.vinayaksproject.simpleelasticproject.entity.IndexTaskEntry taskMetaData) {
     this.task=task;
     this.taskMetaData=taskMetaData;
     started=false;
        
    }

    @Override
    public Object call() throws Exception {
        taskMetaData.setStartTime(new Timestamp(System.currentTimeMillis()));
        long start = System.currentTimeMillis();
        indexTaskDAO.save(taskMetaData);
        try{
        started=true;
        task.start();
        
        }
        catch(TaskSuccessfulException ex){
           taskMetaData.setStatus(JobStatus.SUCCESSFUL);
            taskMetaData.setDetails(ex.getLocalizedMessage());
        }
        catch(Exception ex){
            taskMetaData.setStatus(JobStatus.FAILED);
            taskMetaData.setDetails(ex.getLocalizedMessage());
        }
        finally{
            taskMetaData.setEndTime(new Timestamp(System.currentTimeMillis()));
            long stop = System.currentTimeMillis();
            executiontime=stop-start;
            indexTaskDAO.save(taskMetaData);
        }
        
        return taskMetaData;
    }


    @Override
    public long getExecutionTime() {
        return executiontime;
    }
    

  
    
}