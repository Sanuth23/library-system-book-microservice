package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dto.Book;
import org.example.entity.BookEntity;
import org.example.repository.BookRepository;
import org.example.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    final BookRepository repository;
    ModelMapper mapper;
    @Bean
    public void setUp(){
        this.mapper=new ModelMapper();
    }

    @Override
    public void addBook(Book book) {
        BookEntity entity = mapper.map(book, BookEntity.class);
        repository.save(entity);
    }

    @Override
    public List<Book> getBooks() {
        List<Book> bookList = new ArrayList<>();
        List<BookEntity> entityList = repository.findAll();
        for (BookEntity entity:entityList) {
            Book book = mapper.map(entity, Book.class);
            bookList.add(book);
        }
        return bookList;
    }

    @Override
    public boolean deleteBook(Long id) {
        if(repository.existsById(id)){
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Book getBookById(Long id) {
        Optional<BookEntity> byId = repository.findById(id);

        return mapper.map(byId,Book.class);
    }
}
