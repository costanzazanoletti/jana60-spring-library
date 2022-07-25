package jana60.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import jana60.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

  public List<Category> findAllByOrderByName(); // select * from category order by name;


}
