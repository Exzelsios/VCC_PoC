package de.novatec.vccstatusrepository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class BusinessAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int[] countries;

    public BusinessAttributes() {
    }

    public BusinessAttributes(int[] countries) {
        this.countries = countries;
    }

    public int[] getCountries() {
        return countries;
    }

    public void setCountries(int[] countries) {
        this.countries = countries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessAttributes that = (BusinessAttributes) o;
        return Objects.equals(id, that.id) &&
                Arrays.equals(countries, that.countries);
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(countries);
        return result;
    }

    @Override
    public String toString() {
        return "BusinessAttributes{" +
                "id=" + id +
                ", countries=" + Arrays.toString(countries) +
                '}';
    }
}