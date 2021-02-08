$(document).ready(function(){
  $("#searchComm").on('input', function(event){
        var value = $(this).val().toLowerCase();
       $("#comments li").filter(function() {
             $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
           });
  });
});