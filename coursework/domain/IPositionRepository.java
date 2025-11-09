import java.util.List;
import java.util.Optional;

/**
 * Defines the contract for data access operations related to Position entities.
 */
public interface IPositionRepository {
    void save(Position position) throws DuplicateEntityException;
    void update(Position position);
    void delete(String positionId);
    Optional<Position> findById(String positionId);
    List<Position> findAll();
}
