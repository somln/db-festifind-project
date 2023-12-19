package festifind.festifind.entity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NearestDto {

    private Long id;
    private String region;
    private Double longitude;
    private Double latitude;
    private String title;
    private String place;
}
