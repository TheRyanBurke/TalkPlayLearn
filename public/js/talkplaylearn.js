$(function() {
	$('.collapse').collapse();
	
	
	$('.toggleQuest').click(function(){
		$('.collapse').collapse('toggle');
	});
	
	$('.userHeader').click(function(){
		var userid = this.id.split("-")[1];
		$.ajax({
			type: 'get',
			url: '/usercontroller/user/' + userid,
			error: function(response) {
				
			},
			success: function(response) {
				$('.userSpotlight').html("got back user: " + response.displayname);
			}
		});
		
	});
});

//$.ajax({
//    data: postData,
//    type: method,
//    url: requestUrl,
//    'beforeSend': function(xhr) {
//  	  xhr.setRequestHeader ('Authorization', 'Basic ' + ringDNA.utils.base64encode(controllerConstants.getBasicAuthUsername() + ':' + controllerConstants.getBasicAuthPassword()));
//    },
//    contentType: 'application/x-www-form-urlencoded; charset=iso-8859-1',
//    error: function (response) {
//      var json = JSON.stringify(response);
//      logger.info('error: ' + json);
//      logger.info('message: ' + response.message);
//      logger.info('status: ' + response.status);
//      logger.info('detail: ' + response.detail);
//      ringDNA.calls.responseHandler(response, type);
//    },
//    success: function (response) {
//      ringDNA.calls.responseHandler(response, type);
//    }
//  });
//};