package festifind.festifind.service;

import festifind.festifind.entity.Event;
import festifind.festifind.entity.dto.EventsByGenreAndRegionDto;
import festifind.festifind.entity.dto.NearestDto;
import festifind.festifind.entity.dto.SearchDto;
import festifind.festifind.repository.event.EventCustomRepository;
import festifind.festifind.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventCustomRepository eventCustomRepository;
    private final EventRepository eventRepository;

    public List<EventsByGenreAndRegionDto> searchEventsByGenreAndRegion(Long selectedGenreId, Long selectedRegionId) {
        List<Event> events = eventCustomRepository.searchEventsByGenreAndRegion(selectedGenreId, selectedRegionId);

        return events.stream()
                .map(EventsByGenreAndRegionDto::new)
                .collect(Collectors.toList());
    }

    public List<SearchDto> searchEventsByKeyword(String keyword) {
        List<Event> events = eventCustomRepository.searchEventsByKeyword(keyword);
        return events.stream()
                .map(SearchDto::new)
                .collect(Collectors.toList());
    }

    public List<NearestDto> getNearestEvents(double userLatitude, double userLongitude) {
        List<Event> events = eventRepository.findNearest(userLatitude, userLongitude);
        return events.stream()
                .map(NearestDto::new)
                .collect(Collectors.toList());
    }

}
