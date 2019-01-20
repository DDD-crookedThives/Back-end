package crookedThives.record;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import crookedThives.dog.Dogs;
import crookedThives.user.User;

public interface RecordsRepository extends JpaRepository<Records, Long>{
	
	List<Records> findByDogid(Dogs dogid);

	List<Records> findByUserid(User userid);

}
