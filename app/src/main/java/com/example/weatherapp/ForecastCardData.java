package com.example.weatherapp;

import java.io.Serializable;

public class ForecastCardData implements Serializable {
    private int temperature;
    private int minTemperature;
    private int maxTemperature;
    private int humidity;
    private String weatherInfo;
    private String icon;
    private String time;
    private String day;
    private int colorDay;
    private int colorHour;


    public ForecastCardData() {
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getColorDay() {
        return colorDay;
    }

    public void setColorDay(int colorDay) {
        this.colorDay = colorDay;
    }

    public int getColorHour() {
        return colorHour;
    }

    public void setColorHour(int colorHour) {
        this.colorHour = colorHour;
    }
}
