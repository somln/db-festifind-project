package festifind.festifind.repository.event;

import festifind.festifind.entity.Event;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventCustomRepository {
    List<Event> searchEventsByGenreAndRegion(Long selectedGenreId, Long selectedRegionId);
    List<Event> searchEventsByKeyword(String keyword);
}
