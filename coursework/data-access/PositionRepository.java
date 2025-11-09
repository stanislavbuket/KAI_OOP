import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implements the IPositionRepository interface using a JSON file for persistence.
 */
public class PositionRepository implements IPositionRepository {
    private static final String FILE_PATH = "positions.json";
    private final Map<String, Position> positions;
    private final Gson gson;

    public PositionRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.positions = new ConcurrentHashMap<>();
        loadFromFile();
    }

    @Override
    public void save(Position position) throws DuplicateEntityException {
        if (positions.values().stream().anyMatch(p -> p.getName().equalsIgnoreCase(position.getName()))) {
            throw new DuplicateEntityException("Error: Position with name '" + position.getName() + "' already exists.");
        }
        positions.put(position.getId(), position);
        saveToFile();
    }

    @Override
    public void update(Position position) {
        if (positions.containsKey(position.getId())) {
            positions.put(position.getId(), position);
            saveToFile();
        }
    }

    @Override
    public void delete(String positionId) {
        if (positions.containsKey(positionId)) {
            positions.remove(positionId);
            saveToFile();
        }
    }

    @Override
    public Optional<Position> findById(String positionId) {
        return Optional.ofNullable(positions.get(positionId));
    }

    @Override
    public List<Position> findAll() {
        return new ArrayList<>(positions.values());
    }

    private synchronized void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(positions, writer);
        } catch (IOException e) {
            System.err.println("Error saving positions to file: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try (Reader reader = new FileReader(file)) {
            Type type = new TypeToken<Map<String, Position>>() {}.getType();
            Map<String, Position> loaded = gson.fromJson(reader, type);
            if (loaded != null) {
                positions.putAll(loaded);
            }
        } catch (IOException e) {
            System.err.println("Error loading positions from file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing positions file: " + e.getMessage());
        }
    }
}
