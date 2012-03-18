$(function() {

	$.get('/usercontroller/getcurrentuserforview', function(user) {
		if(user.id != null)
			$('.currentUser').html(ich.user(user));
	});
	
	
	$('.collapse').collapse();
	
	
	$('.toggleQuest').click(function(){
		$('.collapse').collapse('toggle');
	});
	
	$('.beginQuestForm').submit(function(event) {
		event.preventDefault();
		
		//get relative questid
		 /* get some values from elements on the page: */
	    var $form = $( this ),
	        questid = $form.find( 'input[name="questid"]' ).val(),
	        url = $form.attr( 'action' );

		$.post( url, {questid : questid}, 
				function(data){
			//update page quest log
			
			$('.questLog').find("div.quest:first").prepend();
		});
	});
	
	$('.userHeader').click(function(){
		var userid = this.id.split("-")[1];
		$.ajax({
			type: 'get',
			url: '/usercontroller/user/' + userid,
			error: function(response) {
				
			},
			success: function(response) {
				$('.userSpotlight').html(ich.user(response));
			}
		});
		
	});
});
