package or.labos.application.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@Entity
@Table(name = "zupanije")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CountyEntity {

    @Id
    @Column(name = "sifzupanija")
    private Integer countyID;

    @Column(name = "nazzupanija")
    private String countyName;

    @ManyToMany(mappedBy = "parkCounties")
    Set<ParkEntity> hasCounty;

}
