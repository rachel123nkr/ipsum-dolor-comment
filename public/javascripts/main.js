//console.log("JS main")
////$("#comments").hide();
//
//$(document).ready(function(){
//  $("#serachBtn").closest('form').on('click', function(event){
//    event.preventDefault();
//    $("#comments").css({"visibility": "visible"});
//    this.submit();
//  });
//});

$(document).ready(function(){
  $("#searchComm").on('input', function(event){
        var value = $(this).val().toLowerCase();
        console.log(value)
       $("#comments li").filter(function() {
             $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
           });
//        $.ajax({
//            url: "/" + window.location.search,
//            type: 'post',
//            data: {
//                'search-body': 'laudantium'
//            },
//            headers: {
//                'Access-Control-Allow-Origin' : '*',
//                'Access-Control-Allow-Methods' : '*',
//                'Access-Control-Allow-Headers' : 'api-key,content-type',
//                'Access-Control-Allow-Credentials' : true,
//            },
//            dataType: 'json',
//            success: function (data) {
//                console.info(data);
//            }
//        });

  });
});