package or.labos.application.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Table(name = "najvisi_vrhovi")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HighestPeakEntity {

    @Id
    @Column(name = "sifvrh")
    private Integer peakID;

    @Column(name = "nazvrh")
    private String peakName;

    @Column(name = "visina")
    private Integer peakHeight;

    @OneToOne(mappedBy = "peakOfPark")
    private ParkEntity park;
}
