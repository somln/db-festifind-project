package festifind.festifind.controller;

import festifind.festifind.entity.dto.EventsByGenreAndRegionDto;
import festifind.festifind.entity.dto.NearestDto;
import festifind.festifind.entity.dto.SearchDto;
import festifind.festifind.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    @GetMapping("/condition")
    public ResponseEntity<List<EventsByGenreAndRegionDto>> searchEventsByGenreAndRegion(
            @RequestParam(name = "selectedGenreId", required = false) Long selectedGenreId,
            @RequestParam(name = "selectedRegionId", required = false) Long selectedRegionId) {

        List<EventsByGenreAndRegionDto> eventsByGenreAndRegionDtos = eventService.searchEventsByGenreAndRegion(selectedGenreId, selectedRegionId);
        return ResponseEntity.ok(eventsByGenreAndRegionDtos);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchDto>> searchEventsByKeyword(
            @RequestParam(name = "keyword", required = false) String keyword) {

        List<SearchDto> searchDtos = eventService.searchEventsByKeyword(keyword);
        return ResponseEntity.ok(searchDtos);
    }

    @GetMapping("/nearest-events")
    public ResponseEntity<List<NearestDto>> getNearestEvents(
            @RequestParam(name = "userLatitude") double userLatitude,
            @RequestParam(name = "userLongitude") double userLongitude) {

        List<NearestDto> nearestEvents = eventService.getNearestEvents(userLatitude, userLongitude);
        return ResponseEntity.ok(nearestEvents);
    }

}
