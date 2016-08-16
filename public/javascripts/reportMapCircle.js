const CIRCLEMAP= (function(context) { 
  let map;
  let circle;
  const markers = [];
   
  context.onclick = function requestReportCircle() {
    const center = circle.getCenter();
    const latcenter = center.lat().toString();
    const lngcenter = center.lng().toString();
    const radius = circle.getRadius().toString();
    $('#radius').val(radius);
    $('#latcenter').val(latcenter);
    $('#lngcenter').val(lngcenter);
  }
  
  function initialize() {
    const center = new google.maps.LatLng(53.347298, -7.268344);
    const initRadius = 40000;
    const mapProp = {
      center: center,
      zoom: 6,
      mapTypeId: google.maps.MapTypeId.ROADMAP,
    };
    const mapDiv = document.getElementById('map-canvas');
    map = new google.maps.Map(mapDiv, mapProp);
    circle = new google.maps.Circle({
      center: center,
      radius: initRadius,
      strokeColor: '#0000FF',
      strokeOpacity: 0.4,
      strokeWeight: 1,
      fillColor: '#0000FF',
      fillOpacity: 0.4,
      draggable: true,
    });
    circle.setEditable(true);
    circle.setMap(map);
    
    // get marker locations and render on map
    retrieveMarkerLocations();
  }
  
  /**
   * Use ajax call to get markers
   * pass returned array marker locations to positionMarkers method
   * Here is the format in which marker data stored
   * geoObj[0] is eircode             
   * geoObj[1] is latitude                              
   * geoObj[2] is longitude
   * geoObj[3] is rented status message  
   * We use selection of geoObj in the infoWindow. 
   * Click on marker reveals the message
   */
  function retrieveMarkerLocations()
  {
    const latlng = [];
      $(function() {
          $.get("/tenants/vacantresidences", function(data) {
          }).done(function(data) {
               $.each(data, function(index, geoObj) 
               {
                     console.log(geoObj[0] + " " + geoObj[1] + " " + geoObj[2] + " " + geoObj[3]);
               });
               positionMarkers(data);
          });
      });
   }
      
  /**
   * we've got the marker location from data in ajax call
   * we now put data into an array
   * the format is 'zzz zzz, xx.xxxx, yy.yyyyy, sssssss ' -> (eircode, lat, lng, tenant)
   * then invoke 'fitBounds' to render the markers, centre map and create infoWindow to display firstName
   */
  function positionMarkers(data)
  {
    //removeMarkers();
    latlngStr = [];
    $.each(data, function(index, geoObj) 
    {
        latlngStr.push(geoObj);
        });
        fitBounds(latlngStr);
  }
  
  /**
   * A helper function to convert the latlng string to individual numbers
   * and thence to a google.maps.LatLng object
   * @param str str is list of strings : eircode, lat, lon, tenant  
   * str[0] is eircode                
   * str[1] is latitude                              
   * str[2] is longitude    
   * str[3] is tenant name                       
   * 
   * We extract the latitude:longitude pair from the parameter
   * 
   * @param The object 'str' holding an individual marker data set
   * @return A google.maps.LatLng object containing the marker coordinates.
   */
  function getLatLng(str)
  { 
  
    const lat = Number(str[1]);
    const lon = Number(str[2]);
    return new google.maps.LatLng(lat, lon);
  }
   
  /**
   * creates and positions markers
   * sets zoom level so that all markers visible
   */
  function fitBounds(latlngStr)
  {
      const bounds = new google.maps.LatLngBounds();
      const infowindow = new google.maps.InfoWindow();
      
      for (i = 0; i < latlngStr.length; i++) 
      {
        marker = new google.maps.Marker({
            position: getLatLng(latlngStr[i]),
            map: map
        });
          /*click marker displays message (infowindow) */
        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
              infowindow.setContent('Eircode ' + latlngStr[i][0] + " : " + latlngStr[i][3]);
                infowindow.open(map, marker);
            }
        })(marker, i));
        
        bounds.extend(marker.position);
        
        markers.push(marker); // to facilitate removel of markers
      }

      map.fitBounds(bounds);
  }

  /**
   * Method intended to be used where markers replaced on exist map without changing bounds.
   * we've got the marker location from data in ajax call
   * we now put data into an array
   * the format is 'zzz zzz, xx.xxxx, yy.yyyyy, sssssss ' -> (eircode, lat, lng, tenant)
   */
   
  function updateMarkers(data)
  {
    removeMarkers();
    latlngStr = [];
    $.each(data, function(index, geoObj) 
    {
        latlngStr.push(geoObj);
     });
  
    const infowindow = new google.maps.InfoWindow();
    
    for (i = 0; i < latlngStr.length; i++) 
    {
        marker = new google.maps.Marker({
            position: getLatLng(latlngStr[i]),
            map: map
        });
        
        /*respond to click on marker by displaying user (donor) name */
        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
              infowindow.setContent('Eircode ' + latlngStr[i][0] + " : " + latlngStr[i][3]);
                infowindow.open(map, marker);
            }
        })(marker, i));
                  
        markers.push(marker); // to facilitate removel of markers
    }
  }
  
  function removeMarkers()  
  {
    for(i = 0; i < markers.length; i += 1)  {
      markers[i].setMap(undefined);
    }
  }

    /**
   * Use ajax call to get markers
   * pass returned array marker locations to positionMarkers method
   * Here is the format in which marker data stored
   * geoObj[0] is eircode             
   * geoObj[1] is latitude                              
   * geoObj[2] is longitude
   * geoObj[3] is rented status message  
   * We use selection of geoObj in the infoWindow. 
   * Click on marker reveals the message
   * Bounds remain unaltered
   */
  function refreshMarkers()
  {
    const latlng = [];
      $(function() {
          $.get("/tenants/vacantresidences", function(data) {
          }).done(function(data) {
               $.each(data, function(index, geoObj) 
               {
                     console.log(geoObj[0] + " " + geoObj[1] + " " + geoObj[2] + " " + geoObj[3]);
               });
               updateMarkers(data);
          });
      });
   }
  
  return {
    initialize,
    updateMarkers,
    refreshMarkers,
  };
}(this));

google.maps.event.addDomListener(window, 'load', CIRCLEMAP.initialize);

