package com.example.springbatch.config;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import com.example.springbatch.model.Book;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.stereotype.Component;

@Component
public class BookItemWriter extends JdbcBatchItemWriter<Book>{

    final String sql="Insert into book(isbn,title,author,publisher,published,price) values (?,?,?,?,?,?)";
    BookItemWriter(DataSource dataSource){
        super.setDataSource(dataSource);
        super.setSql(sql);
        super.setItemPreparedStatementSetter(preparedStatementSetter());

    } 

    ItemPreparedStatementSetter<Book> preparedStatementSetter(){
        return new ItemPreparedStatementSetter<Book>(){
            
            @Override
            public void setValues(Book item, PreparedStatement ps) throws SQLException {
            	System.out.println("writer Item"+item);
            	
                ps.setString(1, item.getIsbn());
                ps.setString(2, item.getTitle());
                ps.setString(3, item.getAuthor());
                ps.setString(4, item.getPublisher());
                ps.setString(5, item.getPublished());
                ps.setDouble(6, item.getPrice());
                
            }
        };
    }

    
    
}