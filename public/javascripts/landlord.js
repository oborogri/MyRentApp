$( document ).ready(function() {	
    $('#editresidence').dropdown('clear');
    $('#deleteresidence').dropdown('clear');
    
    $('.ui.form').form({
      fields : {
        eircode_delete : {
          identifier : 'eircode_delete',
          rules : [{
            type : 'empty',
            prompt : 'Select residence to delete'
          }]
        },
        
        eircode_edit : {
          identifier : 'eircode_edit',
          rules : [{
            type : 'empty',
            prompt : 'Select residence to edit'
          }]
        }
      }
    });
});	