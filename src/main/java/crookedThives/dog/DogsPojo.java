package crookedThives.dog;

import crookedThives.config.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class DogsPojo {
	
	private String name;
	private String photo;
	private Gender gender;
	private String birth;

}
