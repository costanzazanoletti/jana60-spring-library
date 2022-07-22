package jana60.repository;

import org.springframework.data.repository.CrudRepository;
import jana60.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

  public Integer countByIsbn(String isbn);
}
