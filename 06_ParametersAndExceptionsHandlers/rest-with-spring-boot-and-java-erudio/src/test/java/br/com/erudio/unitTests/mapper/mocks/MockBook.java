package br.com.erudio.unitTests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.erudio.dto.v1.BookDTO;
import br.com.erudio.model.Book;

public class MockBook {


    public Book mockEntity() {
        return mockEntity(0);
    }
    
    public BookDTO mockDTO() {
        return mockDTO(0);
    }
    
    public List<Book> mockEntityList() {
        List<Book> Books = new ArrayList<Book>();
        for (int i = 0; i < 14; i++) {
            Books.add(mockEntity(i));
        }
        return Books;
    }

    public List<BookDTO> mockVOList() {
        List<BookDTO> Books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Books.add(mockDTO(i));
        }
        return Books;
    }
    
    public Book mockEntity(int number) {
        Book book = new Book();
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Name Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(number);
        book.setId(number);
        return book;
    }

    public BookDTO mockDTO(int number) {
        BookDTO book = new BookDTO();
        book.setTitle("Title Test" + number);
        book.setAuthor("Author Name Test" + number);
        book.setLaunchDate(new Date());
        book.setPrice(number);
        book.setKey(number);
        return book;
    }

}
