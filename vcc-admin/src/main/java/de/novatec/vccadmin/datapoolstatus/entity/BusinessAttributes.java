package de.novatec.vccadmin.datapoolstatus.entity;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class BusinessAttributes {
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

    public String getCountriesAsString() {
        return String.join(", ", Arrays.stream(countries).mapToObj(c -> Integer.toString(c)).collect(Collectors.toList()));
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