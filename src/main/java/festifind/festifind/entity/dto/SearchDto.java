package festifind.festifind.entity.dto;

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
}
