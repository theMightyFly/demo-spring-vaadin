package demo.data;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CityRepository extends MongoRepository<City, String>{
	
	List<City> findByNameAndCountryOrderByNameAsc(String pName,String pCountry);
	
	List<City> findByCountryOrderByNameAsc(String pCountry);
}
