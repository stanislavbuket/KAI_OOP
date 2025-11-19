import java.util.*;
import java.util.concurrent.*;

/** A mock implementation of the IPositionRepository for testing purposes. */
public class MockPositionRepository implements IPositionRepository {
  private final Map<String, Position> positions = new ConcurrentHashMap<>();

  /** {@inheritDoc} */
  @Override
  public void save(Position position) throws DuplicateEntityException {
    if (positions.values().stream()
        .anyMatch(p -> p.getName().equalsIgnoreCase(position.getName()))) {
      throw new DuplicateEntityException("Duplicate position name");
    }
    positions.put(position.getId(), position);
  }

  /** {@inheritDoc} */
  @Override
  public void update(Position position) {
    positions.put(position.getId(), position);
  }

  /** {@inheritDoc} */
  @Override
  public void delete(String positionId) {
    positions.remove(positionId);
  }

  /** {@inheritDoc} */
  @Override
  public Optional<Position> findById(String positionId) {
    return Optional.ofNullable(positions.get(positionId));
  }

  /** {@inheritDoc} */
  @Override
  public List<Position> findAll() {
    return new ArrayList<>(positions.values());
  }

  /** Clears all positions from the repository. */
  public void clear() {
    positions.clear();
  }
}
