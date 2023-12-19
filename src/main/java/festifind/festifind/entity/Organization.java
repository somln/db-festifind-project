package festifind.festifind.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
public class Organization {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "org_id")
    private Long id;

    private String name;

    @Column(length = 1024)
    private String url;

    public Organization(String name, String url) {
        this.name = name;
        this.url = url;
    }
}
