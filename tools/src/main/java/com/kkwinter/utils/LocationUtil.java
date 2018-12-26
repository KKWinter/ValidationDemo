package com.kkwinter.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import java.math.BigDecimal;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/*
 * Returns the last known location of the device using its GPS and network location providers.
 * May be null if:
 * - Location permissions are not requested in the Android manifest file
 * - The location providers don't exist
 */
public class LocationUtil {

    public static Location getLastKnownLocation(Context context) {
        return getLastKnownLocation(context, 6, LocationAwareness.NORMAL);
    }

    private static Location getLastKnownLocation(Context context, int locationPrecision, LocationAwareness locationAwareness) {
        Location result;

        if (locationAwareness == LocationAwareness.DISABLED) {
            return null;
        }

        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) return null;

        if (context.checkCallingOrSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED
                && context.checkCallingOrSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            return null;
        }

        Location gpsLocation = null;
        try {
            gpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException e) {
            Log.d("LocationUtil", "Failed to retrieve GPS location: access appears to be disabled.");
        } catch (IllegalArgumentException e) {
            Log.d("LocationUtil", "Failed to retrieve GPS location: device has no GPS provider.");
        }

        Location networkLocation = null;
        try {
            networkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (SecurityException e) {
            Log.d("LocationUtil", "Failed to retrieve network location: access appears to be disabled.");
        } catch (IllegalArgumentException e) {
            Log.d("LocationUtil", "Failed to retrieve network location: device has no network provider.");
        }

        if (gpsLocation == null && networkLocation == null) {
            return null;
        } else if (gpsLocation != null && networkLocation != null) {
            if (gpsLocation.getTime() > networkLocation.getTime()) {
                result = gpsLocation;
            } else {
                result = networkLocation;
            }
        } else if (gpsLocation != null) {
            result = gpsLocation;
        } else {
            result = networkLocation;
        }

        // Truncate latitude/longitude to the number of digits specified by locationPrecision.
        if (locationAwareness == LocationAwareness.TRUNCATED) {
            double lat = result.getLatitude();
            double truncatedLat = BigDecimal.valueOf(lat)
                    .setScale(locationPrecision, BigDecimal.ROUND_HALF_DOWN)
                    .doubleValue();
            result.setLatitude(truncatedLat);

            double lon = result.getLongitude();
            double truncatedLon = BigDecimal.valueOf(lon)
                    .setScale(locationPrecision, BigDecimal.ROUND_HALF_DOWN)
                    .doubleValue();
            result.setLongitude(truncatedLon);
        }

        return result;
    }

    private enum LocationAwareness {NORMAL, TRUNCATED, DISABLED}
}
