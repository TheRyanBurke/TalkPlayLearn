$(function() {

	function paintCurrentUser() { 
		$.get('/usercontroller/getcurrentuserforview', function(user) {
			if(user.id != null) {
				$('.currentUser').html(ich.user(user));
				$('.currentUser .quests .unengaged').hide();
			}
		});
	}
	
	paintCurrentUser();
	
	$.get('/quests/questlist', function(quests) {
		$('.questList').append("Quests");
		$.each(quests, function(index, value) {
			$('.questList').append(ich.quest(value));
			
			var $lastQuest = $('.questList .quest').last();
			$lastQuest.find('button.beginQuest').click(function () {
				$.post('/usercontroller/beginquest', 
						{questid: $lastQuest.find("input[type='hidden']").val()}, 
						function(data) {
							paintCurrentUser();
				});
			});
			
		});
		$('.questList .objective .engaged').hide();
		$('.collapse').collapse();
		
	});
	
	
	$("body").on("click", ".toggleQuest", function() {
		$(this).parent().find('.collapse').collapse('toggle');
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
