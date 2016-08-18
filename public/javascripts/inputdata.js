$('.ui.dropdown').dropdown();
$('.ui.form')
.form({
  residenceType : {
    identifier : 'residence.residenceType',
    rules: [
      {
          type : 'empty',
          prompt: 'Please select a residence type'
      }
    ]
  }
});