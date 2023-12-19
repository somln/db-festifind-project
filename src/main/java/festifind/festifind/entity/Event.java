package festifind.festifind.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Event {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String title;
    private String place;
    private LocalDate startDate;
    private LocalDate endDate;
    private String userTarget;
    private String userFee;
    @Column(length = 1024)
    private String player;
    @Column(length = 1024)
    private String program;
    private String mainImg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    private Organization organization;

    public Event(String title, String place, String startDate, String endDate, String userTarget, String userFee, String player, String program, String mainImg, Genre genre, Region region, Organization organization) {
        this.title = title;
        this.place = place;
        this.startDate = StringToLocalDate(startDate);
        this.endDate = StringToLocalDate(endDate);
        this.userTarget = userTarget;
        this.userFee = userFee;
        this.player = player;
        this.program = program;
        this.mainImg = mainImg;
        this.genre = genre;
        this.region = region;
        this.organization = organization;
    }

    private LocalDate StringToLocalDate(String dateString){
        if (dateString != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(dateString.substring(0, 10), formatter);
            return localDate;
        } else {
            return null;
        }
    }
}
