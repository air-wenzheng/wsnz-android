/*
 * Copyright 2015 Adam Speakman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package speakman.whatsshakingnz.utils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import speakman.whatsshakingnz.model.LocalPlace;

/**
 * Created by Adam on 11/24/2015.
 */
public class DistanceUtil {

    public enum Direction {
        North,
        NorthEast,
        East,
        SouthEast,
        South,
        SouthWest,
        West,
        NorthWest
    }

    // For calculating distance
    private static final double PIx = 3.141592653589793;
    private static final double RADIO = 6378.16; // Radius of the earth, in km

    /**
     * Default places - North to South.
     * For the choice of places, I used a map of NZ on my wall
     * and simply selected all the places that were in a large font.
     * For the latitude & longitude of the places, I used Google Maps.
     */
    private static List<LocalPlace> places;
    public static final LocalPlace Whangarei = new LocalPlace("Whangarei", new LatLng(-35.725156, 174.323735));
    public static final LocalPlace Auckland = new LocalPlace("Auckland", new LatLng(-36.848457, 174.763351));
    public static final LocalPlace Tauranga = new LocalPlace("Tauranga", new LatLng(-37.687798, 176.165149));
    public static final LocalPlace Hamilton = new LocalPlace("Hamilton", new LatLng(-37.787009, 175.279268));
    public static final LocalPlace Whakatane = new LocalPlace("Whakatane", new LatLng(-37.953419, 176.990813));
    public static final LocalPlace Rotorua = new LocalPlace("Rotorua", new LatLng(-38.136875, 176.249759));
    public static final LocalPlace Gisborne = new LocalPlace("Gisborne", new LatLng(-38.662354, 178.017648));
    public static final LocalPlace Taupo = new LocalPlace("Taupo", new LatLng(-38.685686, 176.070214));
    public static final LocalPlace NewPlymouth = new LocalPlace("New Plymouth", new LatLng(-39.055622, 174.075247));
    public static final LocalPlace Napier = new LocalPlace("Napier", new LatLng(-39.492839, 176.912026));
    public static final LocalPlace Hastings = new LocalPlace("Hastings", new LatLng(-39.639558, 176.839247));
    public static final LocalPlace Wanganui = new LocalPlace("Wanganui", new LatLng(-39.930093, 175.047932));
    public static final LocalPlace PalmerstonNorth = new LocalPlace("Palmerston North", new LatLng(-40.352309, 175.608204));
    public static final LocalPlace Levin = new LocalPlace("Levin", new LatLng(-40.622243, 175.286181));
    public static final LocalPlace Masterton = new LocalPlace("Masterton", new LatLng(-40.951114, 175.657356));
    public static final LocalPlace UpperHutt = new LocalPlace("Upper Hutt", new LatLng(-41.124415, 175.070785));
    public static final LocalPlace Porirua = new LocalPlace("Porirua", new LatLng(-41.133935, 174.840628));
    public static final LocalPlace LowerHutt = new LocalPlace("Lower Hutt", new LatLng(-41.209163, 174.90805));
    public static final LocalPlace Wellington = new LocalPlace("Wellington", new LatLng(-41.28647, 174.776231));
    public static final LocalPlace Nelson = new LocalPlace("Nelson", new LatLng(-41.270632, 173.284049));
    public static final LocalPlace Blenheim = new LocalPlace("Blenheim", new LatLng(-41.513444, 173.961261));
    public static final LocalPlace Greymouth = new LocalPlace("Greymouth", new LatLng(-42.450398, 171.210765));
    public static final LocalPlace Christchurch = new LocalPlace("Christchurch", new LatLng(-43.532041, 172.636268));
    public static final LocalPlace Timaru = new LocalPlace("Timaru", new LatLng(-44.396999, 171.255005));
    public static final LocalPlace Queenstown = new LocalPlace("Queenstown", new LatLng(-45.031176, 168.662643));
    public static final LocalPlace Dunedin = new LocalPlace("Dunedin", new LatLng(-45.878764, 170.502812));
    public static final LocalPlace Invercargill = new LocalPlace("Invercargill", new LatLng(-46.413177, 168.35376));

    static {
        places = new ArrayList<>();
        places.add(Whangarei);
        places.add(Auckland);
        places.add(Tauranga);
        places.add(Hamilton);
        places.add(Whakatane);
        places.add(Rotorua);
        places.add(Gisborne);
        places.add(Taupo);
        places.add(NewPlymouth);
        places.add(Napier);
        places.add(Hastings);
        places.add(Wanganui);
        places.add(PalmerstonNorth);
        places.add(Levin);
        places.add(Masterton);
        places.add(UpperHutt);
        places.add(Porirua);
        places.add(LowerHutt);
        places.add(Wellington);
        places.add(Nelson);
        places.add(Blenheim);
        places.add(Greymouth);
        places.add(Christchurch);
        places.add(Timaru);
        places.add(Queenstown);
        places.add(Dunedin);
        places.add(Invercargill);
    }

    private static double radians(double x) {
        return x * PIx / 180;
    }

    /**
     * Returns distance in kilometers between point1 and point2.
     * As seen here: http://stackoverflow.com/questions/27928/how-do-i-calculate-distance-between-two-latitude-longitude-points
     */
    public static double distanceBetweenPlaces(LatLng point1, LatLng point2) {
        double lon1 = point1.longitude;//getLongitudeE6() / 1E6;
        double lat1 = point1.latitude;//getLatitudeE6() / 1E6;
        double lon2 = point2.longitude;//getLongitudeE6() / 1E6;
        double lat2 = point2.latitude;//getLatitudeE6() / 1E6;
        double dlon = radians(lon2 - lon1);
        double dlat = radians(lat2 - lat1);

        double a = (Math.sin(dlat / 2) * Math.sin(dlat / 2))
                + Math.cos(radians(lat1)) * Math.cos(radians(lat2))
                * (Math.sin(dlon / 2) * Math.sin(dlon / 2));
        double angle = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return angle * RADIO;
    }

    public static LocalPlace getClosestPlace(LatLng quakeEpicenter) {
        // Find the distance from the closest town
        double closestTownDistance = -1;
        LocalPlace closestTown = null;
        for (LocalPlace place : places) {
            if (closestTownDistance < 0) {
                closestTownDistance = distanceBetweenPlaces(quakeEpicenter, place.location);
                closestTown = place;
            } else {
                double distance = distanceBetweenPlaces(quakeEpicenter, place.location);
                if (distance < closestTownDistance) {
                    closestTownDistance = distance;
                    closestTown = place;
                }
            }
        }
        return closestTown;
    }

    public static Direction getDirection(LatLng fromPoint, LatLng toPoint) {
        double dLon = Math.abs(toPoint.longitude - fromPoint.longitude);
        double dLat = Math.abs(toPoint.latitude - fromPoint.latitude);
        double brng = Math.atan(dLat / dLon);

        Direction direction;
        Direction eastOrWest;
        Direction northOrSouth;
        // Quake is West of town
        if (toPoint.longitude < fromPoint.longitude) {
            eastOrWest = Direction.West;
        } else { // Quake is East of town
            eastOrWest = Direction.East;
        }

        // Quake is North of town
        if (toPoint.latitude > fromPoint.latitude) {
            northOrSouth = Direction.North;
        } else { // Quake is South of town
            northOrSouth = Direction.South;
        }

        if (brng < Math.PI / 8) {
            direction = eastOrWest;
        } else if (brng < 3 * Math.PI / 8) {
            if (northOrSouth == Direction.North) {
                if (eastOrWest == Direction.East) {
                    direction = Direction.NorthEast;
                } else { // West
                    direction = Direction.NorthWest;
                }
            } else { // South
                if (eastOrWest == Direction.East) {
                    direction = Direction.SouthEast;
                } else { // West
                    direction = Direction.SouthWest;
                }
            }
        } else {
            direction = northOrSouth;
        }

        return direction;
    }
}