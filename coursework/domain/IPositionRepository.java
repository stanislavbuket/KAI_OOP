/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.List;
import java.util.Optional;

/**
 * Defines the contract for data access operations related to Position entities.
 * This interface is part of the domain layer and is implemented in the data-access layer.
 */
public interface IPositionRepository {
    void save(Position position) throws DuplicateEntityException;
    void update(Position position);
    void delete(String positionId);
    Optional<Position> findById(String positionId);
    List<Position> findAll();
}
