//$(document).ready(function() {

  $('#deletetenant').dropdown();
  $('#deletelandlord').dropdown();
  
  /**
   * Validate delete landlord dropdown
   * Invoke controller action and retrieve data using jQuery ajax
   */
  
  $('.ui.form.landlord').form({
    fields: {
      landlord : {
        identifier : 'email_landlord',
        rules : [{
          type : 'empty',
          prompt : 'Please select landlord'
        },],
      },
    },
  
  
    onSuccess : function(event, fields) {
     updateLandlord();      
     event.preventDefault();
    },
  });

  function updateLandlord() {
    var formData = $('.ui.form.landlord').serialize(); 
    $.ajax({
      type : 'POST',
      url : '/administrators/deletelandlord',
      data : formData,
      success : function(response) {
        console.log('delete landlord response : ' + response);
        $('#notificationLandlord').html('Landlord deleted : ' + $('#form_delete_landlord').val());
        // response format: eircode, lat, lng, marker message
        ADMIN_MAP.updateMarkers(response);
        
        let email = $('#deletelandlord').dropdown('get text');
        removeItemLandlordDropdown(email);
      }
    });
  }
 
  function removeItemLandlordDropdown(email) {
    let $obj = $('.item.landlord');
    for (let i = 0; i < $obj.length; i += 1) {
      if($obj[i].getAttribute('data-value').localeCompare(email) == 0) {
        $obj[i].remove();
        $('#deletelandlord').dropdown('clear');
        break;
      }
    }
  }
  
  /**
   * Validate delete tenant dropdown
   * Invoke controller action and retrieve data using jQuery ajax
   */
  $('.ui.form.tenant').form({
    fields: {
      tenant : {
        identifier : 'email_tenant',
        rules : [{
          type : 'empty',
          prompt : 'Please select tenant'
        },],
      },
    },

    onSuccess : function(event, fields) {
     updateTenant();      
     event.preventDefault();
    },
});

function updateTenant() {
  var formData = $('.ui.form.tenant').serialize(); 
  $.ajax({
    type : 'POST',
    url : '/administrators/deletetenant',
    data : formData,
    success : function(response) {
      // response format: eircode, lat, lng, marker message
      $('#notificationTenant').html('Tenant deleted : ' + $('#form_delete_tenant').val());
      ADMIN_MAP.updateMarkers(response);
      
      let email = $('#deletetenant').dropdown('get text');
      removeItemTenantDropdown(email);
    }
  });
}

function removeItemTenantDropdown(email) {
  let $obj = $('.item.tenant');
  for (let i = 0; i < $obj.length; i += 1) {
    if($obj[i].getAttribute('data-value').localeCompare(email) == 0) {
      $obj[i].remove();
      $('#deletetenant').dropdown('clear');
      break;
    }
  }
}

//});