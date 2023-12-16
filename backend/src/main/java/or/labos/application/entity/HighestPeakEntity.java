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
    @GeneratedValue(generator="my_seq")
    @SequenceGenerator(initialValue=100, name="my_seq", sequenceName="peak_seq", allocationSize=1)
    @Column(name = "sifvrh")
    private Integer peakID;

    @Column(name = "nazvrh")
    private String peakName;

    @Column(name = "visina")
    private Integer peakHeight;

    @OneToOne(mappedBy = "peakOfPark")
    private ParkEntity park;
}
