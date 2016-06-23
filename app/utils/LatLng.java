package utils;

public class LatLng
{

  private double lat;
  private double lng;

  public LatLng(double lat, double lng)
  {
    this.lat = lat;
    this.lng = lng;
  }

  public String toString()
  {
    return lat + "," + lng;
  }

  /**
   * @param latlng
   *          : a string comprising a lat,lng : eg 53.444,-5.455
   * @return a LatLng object whose fields obtained by parsing argument
   */
  public static LatLng toLatLng(String latlng)
  {
    String[] latLng = latlng.split(",");
    return new LatLng(Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]));
  }


  public double getLatitude()
  {
    return lat;
  }

  public double getLongitude()
  {
    return lng;
  }

}