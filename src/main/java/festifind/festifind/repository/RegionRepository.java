package festifind.festifind.repository;

import festifind.festifind.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    Region findRegionByName(String regionName);
}
