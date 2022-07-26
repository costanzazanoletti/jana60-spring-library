package jana60.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import jana60.model.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {

  public Integer countByIsbn(String isbn); // select count(*) from book where isbn = ?
  
  public List<Book> findByTitleContainingOrAuthorsContainingIgnoreCase(String queryTitle, String queryAuthors);
}
