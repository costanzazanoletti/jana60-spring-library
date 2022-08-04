package jana60.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import jana60.model.Book;
import jana60.repository.BookRepository;

@RestController
@CrossOrigin
@RequestMapping("/api/books")
public class RestBookController {

  @Autowired
  private BookRepository repo;

  @GetMapping
  public List<Book> get() {
    return (List<Book>) repo.findAll();
  }

  @GetMapping("/{id}")
  public Book getBookById(@PathVariable Integer id) {
    Optional<Book> result = repo.findById(id);
    if (result.isPresent()) {
      return result.get();
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book with id " + id + " not found");
    }
  }
}
