package com.backjoongwon.cvi.dto;

public class WorldVaccinationData {

    private String date;
    private long total_vaccinations;
    private long people_vaccinated;
    private long people_fully_vaccinated;
    private long daily_vaccinations_raw;
    private long daily_vaccinations;
    private double total_vaccinations_per_hundred;
    private double people_vaccinated_per_hundred;
    private double people_fully_vaccinated_per_hundred;
    private long daily_vaccinations_per_million;

    public WorldVaccinationData() {
    }

    public WorldVaccinationData(String date, long total_vaccinations, long people_vaccinated, long people_fully_vaccinated, long daily_vaccinations_raw, long daily_vaccinations, double total_vaccinations_per_hundred, double people_vaccinated_per_hundred, double people_fully_vaccinated_per_hundred, long daily_vaccinations_per_million) {
        this.date = date;
        this.total_vaccinations = total_vaccinations;
        this.people_vaccinated = people_vaccinated;
        this.people_fully_vaccinated = people_fully_vaccinated;
        this.daily_vaccinations_raw = daily_vaccinations_raw;
        this.daily_vaccinations = daily_vaccinations;
        this.total_vaccinations_per_hundred = total_vaccinations_per_hundred;
        this.people_vaccinated_per_hundred = people_vaccinated_per_hundred;
        this.people_fully_vaccinated_per_hundred = people_fully_vaccinated_per_hundred;
        this.daily_vaccinations_per_million = daily_vaccinations_per_million;
    }

    public String getDate() {
        return date;
    }

    public long getTotal_vaccinations() {
        return total_vaccinations;
    }

    public long getPeople_vaccinated() {
        return people_vaccinated;
    }

    public long getPeople_fully_vaccinated() {
        return people_fully_vaccinated;
    }

    public long getDaily_vaccinations_raw() {
        return daily_vaccinations_raw;
    }

    public long getDaily_vaccinations() {
        return daily_vaccinations;
    }

    public double getTotal_vaccinations_per_hundred() {
        return total_vaccinations_per_hundred;
    }

    public double getPeople_vaccinated_per_hundred() {
        return people_vaccinated_per_hundred;
    }

    public double getPeople_fully_vaccinated_per_hundred() {
        return people_fully_vaccinated_per_hundred;
    }

    public long getDaily_vaccinations_per_million() {
        return daily_vaccinations_per_million;
    }
}
