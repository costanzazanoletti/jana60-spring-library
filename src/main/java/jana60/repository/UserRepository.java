package jana60.repository;

import org.springframework.data.repository.CrudRepository;
import jana60.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

}
