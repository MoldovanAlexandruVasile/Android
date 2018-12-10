package com.alex.spottingauto;

public class Announcement {
    private Integer ID;
    private String image_url;
    private String autor;
    private String title;
    private String offerType;
    private String price;
    private String currency;
    private String brand;
    private String model;
    private String year;
    private String color;
    private String fuelType;
    private String transmission;
    private String onBoardKM;
    private String kmOrMiles;
    private String engineCapacity;
    private String doorsNumber;
    private String seatsNumber;
    private String contact;
    private String description;

    public Announcement(String image_url, String autor, String title, String offerType, String price, String currency, String brand,
                        String model, String year, String color, String fuelType, String transmission, String onBoardKM, String kmOrMiles,
                        String engineCapacity, String doorsNumber, String seatsNumber, String contact, String description) {
        this.image_url = image_url;
        this.autor = autor;
        this.title = title;
        this.offerType = offerType;
        this.price = price;
        this.currency = currency;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.onBoardKM = onBoardKM;
        this.kmOrMiles = kmOrMiles;
        this.engineCapacity = engineCapacity;
        this.doorsNumber = doorsNumber;
        this.seatsNumber = seatsNumber;
        this.contact = contact;
        this.description = description;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getOnBoardKM() {
        return onBoardKM;
    }

    public void setOnBoardKM(String onBoardKM) {
        this.onBoardKM = onBoardKM;
    }

    public String getKmOrMiles() {
        return kmOrMiles;
    }

    public void setKmOrMiles(String kmOrMiles) {
        this.kmOrMiles = kmOrMiles;
    }

    public String getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(String engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getDoorsNumber() {
        return doorsNumber;
    }

    public void setDoorsNumber(String doorsNumber) {
        this.doorsNumber = doorsNumber;
    }

    public String getSeatsNumber() {
        return seatsNumber;
    }

    public void setSeatsNumber(String seatsNumber) {
        this.seatsNumber = seatsNumber;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "image_url='" + image_url + '\'' +
                ", autor='" + autor + '\'' +
                ", title='" + title + '\'' +
                ", offerType='" + offerType + '\'' +
                ", price='" + price + '\'' +
                ", currency='" + currency + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", color='" + color + '\'' +
                ", fuelType='" + fuelType + '\'' +
                ", transmission='" + transmission + '\'' +
                ", onBoardKM='" + onBoardKM + '\'' +
                ", kmOrMiles='" + kmOrMiles + '\'' +
                ", engineCapacity='" + engineCapacity + '\'' +
                ", doorsNumber='" + doorsNumber + '\'' +
                ", seatsNumber='" + seatsNumber + '\'' +
                ", contact='" + contact + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
