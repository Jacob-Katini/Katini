public class RentalSystem {
    private String houseId;  // Unique identifier for the house
    private String county;   // County where the house is located
    private String houseType; // Type of the house (e.g., single, double, etc.)
    private double rent;     // Monthly rent amount
    private boolean booked;  // Booking status of the house
    private String details;  // Additional house details (e.g., tiled, ceiling, etc.)

    // Constructor
    public RentalSystem(String houseId, String county, String houseType, double rent, String details) {
        this.houseId = houseId;
        this.county = county;
        this.houseType = houseType;
        this.rent = rent;
        this.details = details;
        this.booked = false; // Default to not booked
    }

    // Getters and Setters
    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public double getRent() {
        return rent;
    }

    public void setRent(double rent) {
        if (rent >= 2500 && rent <= 50000) {
            this.rent = rent;
        } else {
            throw new IllegalArgumentException("Rent must be between 2500 and 50000.");
        }
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    // Method to display house information
    public String displayHouseInfo() {
        return "House ID: " + houseId +
               "\nCounty: " + county +
               "\nType: " + houseType +
               "\nRent: " + rent +
               "\nDetails: " + details +
               "\nStatus: " + (booked ? "Booked" : "Available");
    }

    // Method to book the house
    public boolean bookHouse(String mpesaNumber) {
        if (!booked && isValidMpesaNumber(mpesaNumber)) {
            this.booked = true;
            return true;
        }
        return false;
    }

    // Helper method to validate Mpesa number
    private boolean isValidMpesaNumber(String mpesaNumber) {
        return mpesaNumber != null && mpesaNumber.matches("\\d{10}");
    }
}