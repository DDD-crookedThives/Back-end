package crookedThives.dog;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crookedThives.user.User;

@Repository
public interface DogsRepository extends JpaRepository<Dogs, Long> {

	List<Dogs> findByUserid(User userid);
}
