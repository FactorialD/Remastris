package pl.factoriald.remastris.Entity;
import lombok.Data;

@Data
public class Gravity {
    private Cell point;
    private Direction direction;

    public Gravity(Cell point, Direction direction) {
        this.point = point;
        this.direction = direction;
    }
}
