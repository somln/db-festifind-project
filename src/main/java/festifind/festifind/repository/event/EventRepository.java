package festifind.festifind.repository.event;

import festifind.festifind.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

        @Query("SELECT e FROM Event e " +
            "JOIN Region r ON e.region.id = r.id " +
            "ORDER BY " +
            "ACOS(SIN(RADIANS(:userLatitude)) * SIN(RADIANS(r.latitude)) + " +
            "     COS(RADIANS(:userLatitude)) * COS(RADIANS(r.latitude)) * " +
            "     COS(RADIANS(:userLongitude - r.longitude))) * 6371")

        List<Event> findNearest(@Param("userLatitude") double userLatitude,
                                @Param("userLongitude") double userLongitude);
}
