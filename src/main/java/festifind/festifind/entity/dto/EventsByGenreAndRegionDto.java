package festifind.festifind.entity.dto;

import festifind.festifind.entity.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsByGenreAndRegionDto {

    private Long id;
    private String title;
    private String genre;
    private String region;

    public EventsByGenreAndRegionDto(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.genre = event.getGenre().getName();
        this.region = event.getRegion().getName();
    }
}
