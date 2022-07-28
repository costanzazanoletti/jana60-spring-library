package jana60.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import jana60.model.Book;
import jana60.model.Image;

public interface ImageRepository extends CrudRepository<Image, Integer> {
  public List<Image> findByBook(Book book);
}
