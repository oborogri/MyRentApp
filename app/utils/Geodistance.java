package utils;

public class Geodistance
{

  /**
   * Determines if a point is within a circle
   * 
   * @param point
   * @param circle
   *          : radius units expected in metres
   * @return true if point within circle else false
   */
  public static boolean inCircle(LatLng resLocn, Circle circle)
  {
    return Math.abs(1000 * haversine(resLocn, circle)) < circle.getRadius() ? true : false;
  }

  /**
   * [Following 3 reference sites accessed June 2013]
   * http://www.movable-type.co.uk/scripts/latlong.html
   * http://www.movable-type.co.uk/scripts/latlon.js Calculates the distance
   * between two points (latitude, longitude) on spherical surface
   * 
   * @return the distance in km
   */
  public static double geodistance(double latOrig, double lonOrig, double latDest, double lonDest)
  {
    double R = 6371; // km
    double lat1 = Math.toRadians(latOrig);
    double lon1 = Math.toRadians(lonOrig);
    double lat2 = Math.toRadians(latDest);
    double lon2 = Math.toRadians(lonDest);
    double dLat = lat2 - lat1;
    double dLon = lon2 - lon1;

    double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2)
        * Math.sin(dLon / 2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

    return R * c;
  }

  private static double haversine(LatLng p1, LatLng p2)
  {
    return geodistance(p1.getLatitude(), p1.getLongitude(), p2.getLatitude(), p2.getLongitude());
  }

  private static double haversine(LatLng p1, Circle circle)
  {
    LatLng center = circle.getCenter();
    return haversine(p1, center);
  }
}