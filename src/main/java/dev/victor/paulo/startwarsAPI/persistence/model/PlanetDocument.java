package dev.victor.paulo.startwarsAPI.persistence.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "planets")
public class PlanetDocument {

    @Id
    private final String id;
    private final String name;
    private final String climate;
    private final String terrain;
    private final Integer numberOfFilmAppearances;

    public PlanetDocument(String id, String name, String climate, String terrain, Integer numberOfFilmAppearances) {
        this.id = id;
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
        this.numberOfFilmAppearances = numberOfFilmAppearances;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClimate() {
        return climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public Integer getNumberOfFilmAppearances() {
        return numberOfFilmAppearances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlanetDocument that = (PlanetDocument) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
