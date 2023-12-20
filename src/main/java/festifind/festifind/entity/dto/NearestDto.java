package festifind.festifind.entity.dto;

import festifind.festifind.entity.Event;
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

    public NearestDto(Event event) {
        this.id = event.getId();
        this.region = event.getRegion().getName();
        this.longitude = event.getRegion().getLongitude();
        this.latitude = event.getRegion().getLatitude();
        this.title = event.getTitle();
        this.place = event.getPlace();
    }
}
