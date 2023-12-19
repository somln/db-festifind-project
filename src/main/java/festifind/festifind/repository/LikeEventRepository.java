package festifind.festifind.repository;

import festifind.festifind.entity.LikeEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeEventRepository extends JpaRepository<LikeEvent, Long> {
}
