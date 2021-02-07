console.log("JS main")
$(".comment").hide();

$(document).ready(function(){
  $("#serachBtn").on('click', function(event){
     event.preventDefault();
    $(".comment").show();
    this.submit();
  });
});
