$(document).ready(function(){
  $("#searchComm").on('input', function(event){
        var value = $(this).val().toLowerCase();
       $("#comments li").filter(function() {
             $(this).toggle($(this).children('.comment_body').text().toLowerCase().indexOf(value) > -1)
           });
  });
});