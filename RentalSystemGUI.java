package RENTALMANAGEMENTSYSTEM;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RentalSystemGUI {
    private static List<PropertyBean> properties = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            String[] options = {"Normal Citizen", "Landlord/Landlady", "Exit"};
            int userType = JOptionPane.showOptionDialog(
                    null,
                    "Welcome to the Kenya Rental Homes System!\nSelect User Type:",
                    "User Type",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            if (userType == 2 || userType == -1) {
                JOptionPane.showMessageDialog(null, "Thank you for using the Kenya Rental Homes System!");
                break;
            }

            String phone = JOptionPane.showInputDialog("Enter Phone Number (10 digits):");
            String nationalId = JOptionPane.showInputDialog("Enter National ID (8 digits):");

            if (!isValidPhone(phone) || !isValidNationalId(nationalId)) {
                JOptionPane.showMessageDialog(null, "Invalid phone number or national ID!", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            if (userType == 0) { // Normal Citizen
                citizenMenu();
            } else if (userType == 1) { // Landlord/Landlady
                landlordMenu();
            }
        }
    }

    // Menu for Citizens
    private static void citizenMenu() {
        String[] options = {"Search Vacant Houses", "Book a House", "Back"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Citizen Menu:\nSelect an option:",
                "Citizen Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            String county = JOptionPane.showInputDialog("Enter County:");
            String houseType = JOptionPane.showInputDialog("Enter House Type (Single, Double, etc.):");

            List<PropertyBean> results = searchProperties(county, houseType);
            if (results.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No properties found matching your criteria.");
            } else {
                StringBuilder sb = new StringBuilder("Available Properties:\n");
                for (PropertyBean property : results) {
                    sb.append(property).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        } else if (choice == 1) {
            String houseId = JOptionPane.showInputDialog("Enter House ID to Book:");
            PropertyBean property = findPropertyById(houseId);

            if (property == null || property.isBooked()) {
                JOptionPane.showMessageDialog(null, "Invalid House ID or the house is already booked!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String mpesaNumber = JOptionPane.showInputDialog("Enter Mpesa Number (10 digits):");
                if (!isValidPhone(mpesaNumber)) {
                    JOptionPane.showMessageDialog(null, "Invalid Mpesa number!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    property.setBooked(true);
                    JOptionPane.showMessageDialog(null, "House booked successfully! Mpesa payment confirmed.");
                }
            }
        }
    }

    // Menu for Landlords/Landladies
    private static void landlordMenu() {
        String[] options = {"Add New Vacant House", "Back"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Landlord/Landlady Menu:\nSelect an option:",
                "Landlord/Landlady Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            String houseId = JOptionPane.showInputDialog("Enter House ID (e.g., H1):");
            String county = JOptionPane.showInputDialog("Enter County:");
            String houseType = JOptionPane.showInputDialog("Enter House Type (Single, Double, etc.):");
            String rentInput = JOptionPane.showInputDialog("Enter Monthly Rent (2500-50000):");

            try {
                double rent = Double.parseDouble(rentInput);
                if (rent < 2500 || rent > 50000) {
                    JOptionPane.showMessageDialog(null, "Invalid rent amount. Must be between 2500 and 50000!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    PropertyBean property = new PropertyBean(houseId, county, houseType, rent);
                    properties.add(property);
                    JOptionPane.showMessageDialog(null, "Property added successfully!");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid rent amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Validate phone number
    private static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    // Validate national ID
    private static boolean isValidNationalId(String id) {
        return id != null && id.matches("\\d{8}");
    }

    // Search properties based on criteria
    private static List<PropertyBean> searchProperties(String county, String houseType) {
        List<PropertyBean> results = new ArrayList<>();
        for (PropertyBean property : properties) {
            if (!property.isBooked() &&
                property.getCounty().equalsIgnoreCase(county) &&
                property.getHouseType().equalsIgnoreCase(houseType)) {
                results.add(property);
            }
        }
        return results;
    }

    // Find property by ID
    private static PropertyBean findPropertyById(String houseId) {
        for (PropertyBean property : properties) {
            if (property.getHouseId().equalsIgnoreCase(houseId)) {
                return property;
            }
        }
        return null;
    }
}

// PropertyBean Class
class PropertyBean {
    private String houseId;
    private String county;
    private String houseType;
    private double rent;
    private boolean booked;

    public PropertyBean(String houseId, String county, String houseType, double rent) {
        this.houseId = houseId;
        this.county = county;
        this.houseType = houseType;
        this.rent = rent;
        this.booked = false;
    }

    public String getHouseId() { return houseId; }
    public String getCounty() { return county; }
    public String getHouseType() { return houseType; }
    public double getRent() { return rent; }
    public boolean isBooked() { return booked; }
    public void setBooked(boolean booked) { this.booked = booked; }

    @Override
    public String toString() {
        return "House ID: " + houseId + ", County: " + county + ", Type: " + houseType +
               ", Rent: " + rent + ", Status: " + (booked ? "Booked" : "Available");
    }
}