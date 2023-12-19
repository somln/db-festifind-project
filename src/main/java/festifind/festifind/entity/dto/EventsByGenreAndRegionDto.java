package festifind.festifind.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventsByGenreAndRegionDto {

    private Long id;
    private String title;
    private String genre;
    private String region;
}
