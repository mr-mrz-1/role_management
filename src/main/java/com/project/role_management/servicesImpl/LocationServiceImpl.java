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
    public String getAddressByCoordinate(Coordinates coordinate) {
        log.info("LocationServiceImpl, getAddressByCoordinate() Method started");

        Map<String, Object> objects = geoLocationService.getAddressFromCoordinates(
                coordinate.getLatitude(), coordinate.getLongitude());

//        System.out.println("Raw Response: " + objects);

        if (objects == null) {
//            System.out.println("readable: Address not available (Null Response)");
            return "Address not available";
        }

        Object addressObj = objects.get("address");

        if (addressObj instanceof Map) {
            // Suppress warning of convertion.
            @SuppressWarnings("unchecked")
            Map<String, Object> rawAddressMap = (Map<String, Object>) addressObj;

//            System.out.println("readable: " + buildFormattedAddress(rawAddressMap));
            return buildFormattedAddress(rawAddressMap);
        }

        // Fallback: Use 'display_name' if address object is missing
        if (objects.containsKey("display_name")) {
            String response = (String) objects.get("display_name");
//            System.out.println("readable (fallback): " + response);
            return response;
        }

        return "Address not available";
    }

    public static String buildFormattedAddress(Map<String, Object> addressMap) {
        if (addressMap == null || addressMap.isEmpty()) {
            return "No address found";
            // we can also say service is down this time.
        }

        List<String> parts = new ArrayList<>();

        // 1. Street / Building
        String street = getFirstAvailableAddresses(addressMap, "road", "pedestrian", "highway");
        String houseNumber = getString(addressMap, "house_number");

        if (houseNumber != null && street != null) {
            parts.add(houseNumber + ", " + street);
        } else if (street != null) {
            parts.add(street);
        }

        // 2. Locality
        String locality = getFirstAvailableAddresses(addressMap, "suburb", "neighbourhood", "residential");
        addInListIfNewNameOnly(parts, locality);

        // 3. City Level
        String cityPart = getFirstAvailableAddresses(addressMap, "city", "town", "village", "hamlet");
        addInListIfNewNameOnly(parts, cityPart);

        // 4. District
        String district = getFirstAvailableAddresses(addressMap, "state_district", "county");
        addInListIfNewNameOnly(parts, district);

        // 5. State & Country
        addInListIfNewNameOnly(parts, getString(addressMap, "state"));
        addInListIfNewNameOnly(parts, getString(addressMap, "postcode"));
        addInListIfNewNameOnly(parts, getString(addressMap, "country"));

        return String.join(", ", parts);
    }

    // Safely gets a String from the map, even if the value is an Integer
    private static String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        return val != null ? String.valueOf(val) : null;
    }

    // Checks multiple keys
    private static String getFirstAvailableAddresses(Map<String, Object> map, String... keys) {
        for (String key : keys) {
            String val = getString(map, key);
            if (val != null && !val.trim().isEmpty()) {
                return val;
            }
        }
        return null;
    }

    private static void addInListIfNewNameOnly(List<String> parts, String value) {
        if (value != null && !value.trim().isEmpty() && !parts.contains(value)) {
            parts.add(value);
        }
    }
}
