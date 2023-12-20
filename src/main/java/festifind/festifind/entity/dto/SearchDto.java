package festifind.festifind.entity.dto;

import festifind.festifind.entity.Event;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {

    private Long id;
    private String genre;
    private String region;
    private String title;
    private String place;

    public SearchDto(Event event) {
        this.id = event.getId();
        this.genre = event.getGenre().getName();
        this.region = event.getRegion().getName();
        this.title = event.getTitle();
        this.place = event.getPlace();
    }
}
