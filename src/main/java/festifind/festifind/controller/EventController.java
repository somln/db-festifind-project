package festifind.festifind.controller;

import festifind.festifind.entity.Event;
import festifind.festifind.entity.Region;
import festifind.festifind.entity.dto.EventsByGenreAndRegionDto;
import festifind.festifind.entity.dto.NearestDto;
import festifind.festifind.entity.dto.SearchDto;
import festifind.festifind.repository.event.EventCustomRepository;
import festifind.festifind.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/events")
public class EventController {

    private final EventCustomRepository eventCustomRepository;
    private final EventRepository eventRepository;

    @GetMapping("/condition")
    public ResponseEntity<List<EventsByGenreAndRegionDto>> searchEventsByGenreAndRegion(
            @RequestParam(name = "selectedGenreId", required = false) Long selectedGenreId,
            @RequestParam(name = "selectedRegionId", required = false) Long selectedRegionId) {
        List<Event> events = eventCustomRepository.searchEventsByGenreAndRegion(selectedGenreId, selectedRegionId);

        // 엔티티를 DTO로 변환
        List<EventsByGenreAndRegionDto> eventsDto = events.stream()
                .map(event -> {
                    EventsByGenreAndRegionDto dto = new EventsByGenreAndRegionDto();
                    dto.setId(event.getId());
                    dto.setTitle(event.getTitle());

                    // 엔티티에서 필요한 정보를 DTO로 복사
                    if (event.getGenre() != null) {
                        dto.setGenre(event.getGenre().getName());
                    }
                    if (event.getRegion() != null) {
                        dto.setRegion(event.getRegion().getName());
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // 값 출력
        log.info("Received request with selectedGenreId: {}, selectedRegionId: {}", selectedGenreId, selectedRegionId);
        log.info("Resulting events: {}", eventsDto);

        return ResponseEntity.ok(eventsDto);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchDto>> searchEventsByKeyword(
            @RequestParam(name = "keyword", required = false) String keyword) {

        // 키워드 검색
        List<Event> events = eventCustomRepository.searchEventsByKeyword(keyword);

        List<SearchDto> eventsDto = events.stream()
                .map(event -> {
                    SearchDto dto = new SearchDto();
                    dto.setId(event.getId());
                    dto.setTitle(event.getTitle());
                    dto.setPlace(event.getPlace());

                    // 엔티티에서 필요한 정보를 SearchDto로 복사
                    if (event.getGenre() != null) {
                        dto.setGenre(event.getGenre().getName());
                    }
                    if (event.getRegion() != null) {
                        dto.setRegion(event.getRegion().getName());
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // 값 출력
        log.info("Received request with keyword: {}", keyword);
        log.info("Resulting events: {}", eventsDto);

        return ResponseEntity.ok(eventsDto);
    }

    @GetMapping("/nearest-events")
    public ResponseEntity<List<NearestDto>> getNearestEvents(
            @RequestParam(name = "userLatitude") double userLatitude,
            @RequestParam(name = "userLongitude") double userLongitude) {

        List<Event> events = eventRepository.findNearest(userLatitude, userLongitude);
        // 엔티티를 NearestDto로 변환 및 바로 반환
        List<NearestDto> eventsDto = events.stream()
                .map(event -> {
                    NearestDto dto = new NearestDto();
                    dto.setId(event.getId());
                    dto.setTitle(event.getTitle());
                    dto.setPlace(event.getPlace());

                    Region region = event.getRegion();
                    if (region != null) {
                        dto.setRegion(region.getName());
                        dto.setLongitude(region.getLongitude());
                        dto.setLatitude(region.getLatitude());
                    }

                    return dto;
                })
                .collect(Collectors.toList());

        // 값 출력
        log.info("Received request with userLatitude: {}, userLongitude: {} ", userLatitude, userLongitude);
        log.info("Resulting events: {}", eventsDto);

        return ResponseEntity.ok(eventsDto);
    }

}
