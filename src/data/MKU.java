package data;

import com.fasterxml.jackson.annotation.JsonCreator;
import data.enums.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MKU extends Component {

    private final ComponentType type = ComponentType.MKU;
    private int number;
    private int countOfLandings;
    private int startsOnMainChannel;
    private int startsOnReserveChannel;
    private int flightOperatingTime;
    private String attachedToAircraft;

    @JsonCreator
    public MKU() {
    }
}
