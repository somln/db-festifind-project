package festifind.festifind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Region {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    private String name;

    private Double longitude;
    private Double latitude;

    public Region(String name, String longitude, String latitude) {
        this.name = name;
        this.longitude = stringToDouble(longitude);
        this.latitude = stringToDouble(latitude);
    }

    private Double stringToDouble(String string){
        Double doubleValue= 0.0;
        try {
            doubleValue = Double.parseDouble(string);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
       return doubleValue;
    }
}
