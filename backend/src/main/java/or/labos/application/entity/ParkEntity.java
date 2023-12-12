package or.labos.application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

@Entity
@Table(name = "parkovi")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkEntity extends RepresentationModel<ParkEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "park_generator")
    @SequenceGenerator
            (name="park_generator", sequenceName = "park_seq",
                    initialValue = 11,
                    allocationSize=1)
    @Column(name = "sifpark")
    private Integer parkID;

    @Column(name = "nazpark")
    private String parkName;

    @Column(name = "godosnutka")
    private Integer yearOfFoundation;

    @Column(name = "povrsina")
    private Double area;

    @Column(name = "nazatrakcija")
    private String atraction;

    @Column(name = "nazdoggod")
    private String event;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sifvrh", referencedColumnName = "sifvrh")
    private HighestPeakEntity peakOfPark;

    @ManyToMany
    @JoinTable(
            name = "nalazise",
            joinColumns = @JoinColumn(name = "sifpark"),
            inverseJoinColumns = @JoinColumn(name = "sifzupanija")
    )
    Set<CountyEntity> parkCounties;

    @ManyToMany
    @JoinTable(
            name = "imazivotinju",
            joinColumns = @JoinColumn(name = "sifpark"),
            inverseJoinColumns = @JoinColumn(name = "sifzivotinja")
    )
    Set<AnimalEntity> parkAnimals;

    @ManyToOne
    @JoinColumn(name = "siftippark")
    private TypeOfParkEntity typeOfPark;

}
