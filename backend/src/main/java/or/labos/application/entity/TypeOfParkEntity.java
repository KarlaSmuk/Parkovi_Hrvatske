package or.labos.application.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "tipovi_parka")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeOfParkEntity {

    @Id
    @Column(name = "siftippark")
    private Integer typeOfParkID;

    @Column(name = "naztippark")
    private String typeOfParkName;

    @OneToMany(mappedBy = "typeOfPark")
    private Set<ParkEntity> parks;

}
