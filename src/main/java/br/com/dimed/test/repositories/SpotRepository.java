package br.com.dimed.test.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.dimed.test.domain.SpotModel;
import br.com.dimed.test.domain.SpotModel.SpotCompositeId;

public interface SpotRepository extends CrudRepository<SpotModel, SpotCompositeId> {

	@Query(value=
			"SELECT * " + 
			"FROM spot s " + 
			"WHERE line_id = :lineId " + 
			"and st_distance_sphere(ST_MakePoint(s.lng, s.lat),ST_MakePoint(:lng,:lat)) <= :radiusInMeters",
			nativeQuery = true)
	List<SpotModel> findByLineAndSpotInRadius(@Param("lat") Double lat, @Param("lng") Double lng, @Param("lineId") Long lineId, @Param("radiusInMeters") Long radiusInMeters);

}
