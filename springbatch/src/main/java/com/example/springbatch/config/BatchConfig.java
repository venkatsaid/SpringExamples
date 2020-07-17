package com.example.springbatch.config;

import java.util.Arrays;

import javax.sql.DataSource;

import com.example.springbatch.model.Book;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;

import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;

@Configuration
public class BatchConfig {
    

    @Autowired
    StepBuilderFactory stepBuilderFactory;
    @Autowired
    JobBuilderFactory JobBuilderFactory;
   
    @Bean 
    Job insertFileinDB(ItemReader<Book> bookItemReader, ItemWriter<Book> writer) {
        Step step= stepBuilderFactory.get("book-batch")
        		.<Book, Book> chunk(2)
        .reader(bookItemReader)
        .writer(writer)
        .build();


        Job job= JobBuilderFactory.get("job")
        .incrementer(new RunIdIncrementer())
        .start(step)
        .build();
        return job;
    }

    @Bean
    @StepScope
    FlatFileItemReader<Book> flatFileItemReader(@Value("#{jobParameters['fileName']}") String fileName) throws UnexpectedInputException, ParseException, Exception{
    	System.out.println("reader started");
    	 FlatFileItemReader<Book> reader = new FlatFileItemReader<Book>();
    	   
    	
        //Set input file location
        reader.setResource(new FileSystemResource(fileName));
         
        //Set number of lines to skips. Use it if file has header rows.
        reader.setLinesToSkip(0);   
         
        //Configure how each line will be parsed and mapped to different values
        reader.setLineMapper(new DefaultLineMapper() {
            {
                //3 columns in each row
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                    	setDelimiter(",");
                    	setStrict(false);
                        setNames(new String[] { "isbn","title","author","publisher","published","price" });
                    }
                });
                //Set values in Employee class
                setFieldSetMapper(new BeanWrapperFieldSetMapper<Book>() {
                    {
                        setTargetType(Book.class);
                    }
                });
                
            }
        });
//        reader.open(new ExecutionContext());
//        reader.read();
        return reader;

        
        
    }




    
    @StepScope
    CompositeItemWriter<Book> compositeItemWriter(BookItemWriter bookItemWriter){
    	System.out.println(":wruetr started");
        CompositeItemWriter<Book> writer= new CompositeItemWriter<>();
        writer.setDelegates(Arrays.asList (bookItemWriter));
        return writer;

    }

}