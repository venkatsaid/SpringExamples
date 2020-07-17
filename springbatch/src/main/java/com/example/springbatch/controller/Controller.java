package com.example.springbatch.controller;

import java.io.IOException;

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


@PostMapping(value = "/uploadFile",consumes = "multipart/form-data")
 public void uploadFile( @RequestParam("file") MultipartFile file,HttpServletResponse response) throws IOException{
	
	System.out.print("api called:"+file);
        jobService.startFileUpload(file,response);

    }

@PostMapping(value = "/api")
 public void api(HttpServletResponse response){
//	System.out.print("api called");
        

    }



}