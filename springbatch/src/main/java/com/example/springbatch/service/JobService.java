package com.example.springbatch.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class JobService {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job insertFileinDB;

    public void startFileUpload(MultipartFile uploadFile, HttpServletResponse response) {

        batchTrigger(uploadFile.getOriginalFilename());
    }

    private void batchTrigger(String fileName) {
        JobParameters jobParameters = new JobParametersBuilder().addString("fileName", fileName).toJobParameters();

        try {
            JobExecution job = jobLauncher.run(insertFileinDB, jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
    
}