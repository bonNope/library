package webApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webApp.models.Book;
import webApp.models.Person;
import webApp.repositories.BookRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll(boolean sortByYear){
        if(sortByYear)
            return bookRepository.findAll(Sort.by("year"));
        else
            return bookRepository.findAll();
    }

    public List<Book> findWithPagination(Integer page, Integer booksPerPage, boolean sortByYear){
        if(sortByYear)
            return bookRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("year"))).getContent();
        else
            return bookRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> searchByName(String query){
        return bookRepository.findByNameStartingWith(query);
    }

    public Book findOne(int id){
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = bookRepository.findById(id).get();

        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        bookRepository.deleteById(id);
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson){
        bookRepository.findById(bookId).ifPresent(
                book -> {
                    book.setOwner(selectedPerson);
                    book.setTakenAt(new Date());
                }
        );
    }

    @Transactional
    public void release(int bookId){
        bookRepository.findById(bookId).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                }
        );
    }

    @Transactional
    public Person getBookOwner(int id){
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

}
