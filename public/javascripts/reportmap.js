var circle;
function requestReport() {
    var center = circle.getCenter();
    var latcenter = center.lat().toString();
    var lngcenter = center.lng().toString();
    var radius = circle.getRadius().toString();
    //alert('radius');
    $("#radius").val(radius);
    $("#latcenter").val(latcenter);
    $("#lngcenter").val(lngcenter);    
}

function initialize()
{
    var center =new google.maps.LatLng(53.347298,-6.268344);
    var initRadius = 10000;
    var mapProp = {
            center:center,
            zoom:7,
            mapTypeId:google.maps.MapTypeId.ROADMAP
    };
    var mapDiv = document.getElementById("map-canvas");
    var map = new google.maps.Map(mapDiv,mapProp);
    mapDiv.style.width = '500px';
    mapDiv.style.height = '500px';

        circle = new google.maps.Circle({
        center:center,
        radius:initRadius,
        strokeColor:"#0000FF",
        strokeOpacity:0.4,
        strokeWeight:1,
        fillColor:"#0000FF",
        fillOpacity:0.4,
        draggable: true
        });
    circle.setEditable(true);//allows varying radius be dragging anchor point
    circle.setMap(map);
}
google.maps.event.addDomListener(window, 'load', initialize);
