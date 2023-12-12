package or.labos.application.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "najvisi_vrhovi")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighestPeakEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "peak_generator")
    @SequenceGenerator
            (name="peak_generator", sequenceName = "peak_seq",
                    initialValue = 6,
                    allocationSize=1)
    @Column(name = "sifvrh")
    private Integer peakID;

    @Column(name = "nazvrh")
    private String peakName;

    @Column(name = "visina")
    private Integer peakHeight;

    @OneToOne(mappedBy = "peakOfPark")
    private ParkEntity park;
}
