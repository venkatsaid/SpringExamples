package com.example.springbatch.controller;

import javax.servlet.http.HttpServletResponse;

import com.example.springbatch.service.JobService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class Controller {

@Autowired
JobService jobService;
    @PostMapping(value = "uploadFile/")
    public void uploadFile(@RequestParam("file") MultipartFile uploadFile,HttpServletResponse response){
        jobService.startFileUpload(uploadFile,response);

    }

    
}