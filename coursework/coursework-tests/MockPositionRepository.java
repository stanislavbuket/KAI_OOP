import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MockPositionRepository implements IPositionRepository {
    private final Map<String, Position> positions = new ConcurrentHashMap<>();

    @Override
    public void save(Position position) throws DuplicateEntityException {
        if (positions.values().stream().anyMatch(p -> p.getName().equalsIgnoreCase(position.getName()))) {
            throw new DuplicateEntityException("Duplicate position name");
        }
        positions.put(position.getId(), position);
    }

    @Override
    public void update(Position position) {
        positions.put(position.getId(), position);
    }

    @Override
    public void delete(String positionId) {
        positions.remove(positionId);
    }

    @Override
    public Optional<Position> findById(String positionId) {
        return Optional.ofNullable(positions.get(positionId));
    }

    @Override
    public List<Position> findAll() {
        return new ArrayList<>(positions.values());
    }

    public void clear() {
        positions.clear();
    }
}
