package com.example.deliverytracker.Services;
import java.util.regex.Pattern;

public class TrackingNumberValidator {

    // Регулярные выражения для различных служб
    private static final Pattern UPU_REGEX = Pattern.compile("^[A-Z]{2}\\d{9}[A-Z]{2}$");
    private static final Pattern FEDEX_REGEX = Pattern.compile("^\\d{12,14}$");
    private static final Pattern UPS_REGEX = Pattern.compile("^1Z[0-9A-Z]{16}$");
    private static final Pattern USPS_REGEX = Pattern.compile("^\\d{20,22}$");
    private static final Pattern DHL_REGEX = Pattern.compile("^\\d{10}$");

    public static String validateTrackingNumber(String trackingNumber) {
        if (UPU_REGEX.matcher(trackingNumber).matches()) {
            return "Universal Postal Union (UPU) Format";
        }else if (FEDEX_REGEX.matcher(trackingNumber).matches()) {
            return "FedEx";
        } else if (UPS_REGEX.matcher(trackingNumber).matches()) {
            return "UPS";
        } else if (USPS_REGEX.matcher(trackingNumber).matches()) {
            return "USPS";
        } else if (DHL_REGEX.matcher(trackingNumber).matches()) {
            return "DHL";
        } else {
            return "Unknown";
        }
    }

    public static boolean check(String trackingNumber) {
        String service = validateTrackingNumber(trackingNumber);

        if (!service.equals("Unknown")) {
           return true;
        } else {
            return false;
        }
    }
}
