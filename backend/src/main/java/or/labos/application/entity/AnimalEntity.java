package or.labos.application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "zivotinje")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalEntity  {

    @Id
    @Column(name = "sifzivotinja")
    private Integer animalID;

    @Column(name = "nazzivotinja")
    private String animalName;

    @Column(name = "vrstazivotinja")
    private String speciesOfAnimal;

    @ManyToMany(mappedBy = "parkAnimals")
    Set<ParkEntity> hasAnimal = new HashSet<>();
}
