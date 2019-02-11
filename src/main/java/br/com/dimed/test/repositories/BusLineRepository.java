package br.com.dimed.test.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.dimed.test.domain.BusLineModel;
import br.com.dimed.test.domain.SpotModel;

public interface BusLineRepository extends JpaRepository<BusLineModel, Long> {
	List<BusLineModel> findByNomeContainingIgnoreCase(String nome);
	
	Optional<BusLineModel> findById(Long id);
	
	@Query(value=
			"SELECT distinct b.* " +
			"FROM bus_line b " +
			"inner join spot s on b.id = s.line_id " +
			"WHERE st_distance_sphere(ST_MakePoint(s.lng, s.lat),ST_MakePoint(:lng, :lat)) <= :radiusInMeters ",
			nativeQuery = true)
	List<BusLineModel> findBySpotInRadius(@Param("lat") Double lat, @Param("lng") Double lng, @Param("radiusInMeters") Long radiusInMeters);

}
