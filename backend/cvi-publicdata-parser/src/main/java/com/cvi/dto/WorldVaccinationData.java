package com.cvi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorldVaccinationData {

    private String date;
    @JsonProperty(value = "total_vaccinations")
    private long totalVaccinations;
    @JsonProperty(value = "people_vaccinated")
    private long peopleVaccinated;
    @JsonProperty(value = "people_fully_vaccinated")
    private long peopleFullyVaccinated;
    @JsonProperty(value = "daily_vaccinations_raw")
    private long dailyVaccinationsRaw;
    @JsonProperty(value = "daily_vaccinations")
    private long dailyVaccinations;
    @JsonProperty(value = "total_vaccinations_per_hundred")
    private double totalVaccinationsPerHundred;
    @JsonProperty(value = "people_vaccinated_per_hundred")
    private double peopleVaccinatedPerHundred;
    @JsonProperty(value = "peopleFullyVaccinated_per_hundred")
    private double peopleFullyVaccinatedPerHundred;
    @JsonProperty(value = "daily_vaccinations_per_million")
    private long dailyVaccinationsPerMillion;

    public WorldVaccinationData() {
    }

    public WorldVaccinationData(String date, long totalVaccinations, long peopleVaccinated, long peopleFullyVaccinated, long dailyVaccinationsRaw, long dailyVaccinations, double totalVaccinationsPerHundred, double peopleVaccinatedPerHundred, double peopleFullyVaccinatedPerHundred, long dailyVaccinationsPerMillion) {
        this.date = date;
        this.totalVaccinations = totalVaccinations;
        this.peopleVaccinated = peopleVaccinated;
        this.peopleFullyVaccinated = peopleFullyVaccinated;
        this.dailyVaccinationsRaw = dailyVaccinationsRaw;
        this.dailyVaccinations = dailyVaccinations;
        this.totalVaccinationsPerHundred = totalVaccinationsPerHundred;
        this.peopleVaccinatedPerHundred = peopleVaccinatedPerHundred;
        this.peopleFullyVaccinatedPerHundred = peopleFullyVaccinatedPerHundred;
        this.dailyVaccinationsPerMillion = dailyVaccinationsPerMillion;
    }

    public String getDate() {
        return date;
    }

    public long getTotalVaccinations() {
        return totalVaccinations;
    }

    public long getPeopleVaccinated() {
        return peopleVaccinated;
    }

    public long getPeopleFullyVaccinated() {
        return peopleFullyVaccinated;
    }

    public long getDailyVaccinationsRaw() {
        return dailyVaccinationsRaw;
    }

    public long getDailyVaccinations() {
        return dailyVaccinations;
    }

    public double getTotalVaccinationsPerHundred() {
        return totalVaccinationsPerHundred;
    }

    public double getPeopleVaccinatedPerHundred() {
        return peopleVaccinatedPerHundred;
    }

    public double getPeopleFullyVaccinatedPerHundred() {
        return peopleFullyVaccinatedPerHundred;
    }

    public long getDailyVaccinationsPerMillion() {
        return dailyVaccinationsPerMillion;
    }
}
