package com.project.role_management.servicesImpl;

import com.project.role_management.dto.locations.Coordinates;
import com.project.role_management.services.LocationService;
import com.project.role_management.utils.openStreetMapApi.GeoLocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final GeoLocationService geoLocationService;

    public LocationServiceImpl(GeoLocationService geoLocationService) {
        this.geoLocationService = geoLocationService;
    }

    @Override
    public void getAddressByCoordinate(Coordinates coordinate) {
        log.info("LocationServiceImpl, getAddressByCoordinate() Method started");

        // FIX 1: Change <String, String> to <String, Object> to handle nested objects/numbers
        Map<String, Object> objects = geoLocationService.getAddressFromCoordinates(coordinate.getLatitude(), coordinate.getLongitude());

        // Debug: Print the raw map to ensure data exists
        System.out.println("Raw Response: " + objects);

        if (objects == null) {
            System.out.println("readable: Address not available (Null Response)");
            return;
        }

        // 1. EXTRACT the nested 'address' map safely
        Object addressObj = objects.get("address");

        if (addressObj instanceof Map) {
            // FIX 2: Safely cast to Map<String, Object> first, then handle values
            // We suppress warnings because we know the structure from the log
            @SuppressWarnings("unchecked")
            Map<String, Object> rawAddressMap = (Map<String, Object>) addressObj;

            // 2. Pass to the builder
            System.out.println("readable: " + buildFormattedAddress(rawAddressMap));
            return;
        }

        // Fallback: Use 'display_name' if address object is missing
        if (objects.containsKey("display_name")) {
            String response = (String) objects.get("display_name");
            System.out.println("readable (fallback): " + response);
            return;
        }

        System.out.println("readable: Address not available");
    }

    public static String buildFormattedAddress(Map<String, Object> addressMap) {
        if (addressMap == null || addressMap.isEmpty()) {
            return "no address found";
        }

        List<String> parts = new ArrayList<>();

        // 1. Street / Building
        String street = getFirstNonNull(addressMap, "road", "pedestrian", "highway");
        String houseNumber = getString(addressMap, "house_number");

        if (houseNumber != null && street != null) {
            parts.add(houseNumber + ", " + street);
        } else if (street != null) {
            parts.add(street);
        }

        // 2. Locality
        String locality = getFirstNonNull(addressMap, "suburb", "neighbourhood", "residential");
        addIfNew(parts, locality);

        // 3. City Level
        String cityPart = getFirstNonNull(addressMap, "city", "town", "village", "hamlet");
        addIfNew(parts, cityPart);

        // 4. District
        String district = getFirstNonNull(addressMap, "state_district", "county");
        addIfNew(parts, district);

        // 5. State & Country
        addIfNew(parts, getString(addressMap, "state"));
        addIfNew(parts, getString(addressMap, "postcode")); // Safer: handles if postcode is Int or String
        addIfNew(parts, getString(addressMap, "country"));

        return String.join(", ", parts);
    }

    // Helper: Safely gets a String from the map, even if the value is an Integer
    private static String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? String.valueOf(val) : null;
    }

    // Helper: Checks multiple keys
    private static String getFirstNonNull(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            String val = getString(map, key);
            if (val != null && !val.trim().isEmpty()) {
                return val;
            }
        }
        return null;
    }

    private static void addIfNew(List<String> parts, String value) {
        if (value != null && !value.trim().isEmpty() && !parts.contains(value)) {
            parts.add(value);
        }
    }
}
