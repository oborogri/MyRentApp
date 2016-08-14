$( document ).ready(function() {	
    $('#eircode_residence').dropdown('clear');
        
    $('.ui.form').form({
      fields : {
    	  eircode_vacancy : {
          identifier : 'eircode_vacancy',
          rules : [{
            type : 'empty',
            prompt : 'Please select vacant residence'
          }]
        }
      }
    });
});	