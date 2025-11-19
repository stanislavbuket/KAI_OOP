/**
 * Done by:
 * Student Name: Stanislav Buket
 * Variant: 4
 * Student Group: 121
 * Coursework
 */

import java.util.*;

/**
 * Defines the contract for data access operations related to Position entities. This interface is
 * part of the domain layer and is implemented in the data-access layer.
 */
public interface IPositionRepository {
  /**
   * Saves a new position.
   *
   * @param position The position to save.
   * @throws DuplicateEntityException If a position with the same name already exists.
   */
  void save(Position position) throws DuplicateEntityException;

  /**
   * Updates an existing position.
   *
   * @param position The position to update.
   */
  void update(Position position);

  /**
   * Deletes a position by its ID.
   *
   * @param positionId The ID of the position to delete.
   */
  void delete(String positionId);

  /**
   * Finds a position by its ID.
   *
   * @param positionId The ID of the position to find.
   * @return An Optional containing the position if found, or an empty Optional otherwise.
   */
  Optional<Position> findById(String positionId);

  /**
   * Finds all positions.
   *
   * @return A list of all positions.
   */
  List<Position> findAll();
}
