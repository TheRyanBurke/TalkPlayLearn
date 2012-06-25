$(function() {

	function paintCurrentUser(userModel) {
		if(userModel != null) {
			paintUser(userModel);
		} else {
			$.get('/userapi/getcurrentuserforview', function(user) {
				if(user != null && user != "") {
					paintUser(user);
				}
			});
		}
	}
	
	function paintUser(user) {
		$('.currentUser').html(ich.user(user));
		$('.currentUser .collapse').collapse();
		$('.currentUser .quests .unengaged').hide();
		$('.currentUser .quests .engaged.completeIneligible').hide();
	}
	
	paintCurrentUser();
	
	$.get('/quests/questlist', function(quests) {
		$('.questList').append("<h4>Quests</h4>");
		$.each(quests, function(index, value) {
			$('.questList').append(ich.quest(value));
			
			var $lastQuest = $('.questList .quest').last();
			$lastQuest.find('button.beginQuest').click(function () {
				$.post('/user/beginquest', {questid: $lastQuest.prop("id")}, 
					function (data) {
						paintCurrentUser();
				});
			});
			
		});
		$('.questList .engaged').hide();
		$('.collapse').collapse();
		
	});
	
	
	$("body").on("click", ".toggleQuest", function() {
		$(this).parent().find('.collapse').collapse('toggle');
	});
	
	$("body").on("click", ".tick", function() {
		var uptick = ($(this).hasClass("uptick")) ? true : false;
		
		$.post("/user/tickobjective", 
				{questId: $(this).closest(".quest").prop("id"), 
				objectiveId: $(this).closest("li").prop("id"),
				uptick: uptick}, 
				function(user) {
			paintCurrentUser(user);
		});
	});
	
	$("body").on("click", ".completeQuest", function() {
		var questid = $(this).closest(".quest").prop("id");
		$.post("/user/completequest",
				{questid: questid},
				function(user) {
					paintCurrentUser(user);
				});
	});
	
	
	$('.userHeader').click(function(){
		var userid = this.id.split("-")[1];
		$.ajax({
			type: 'get',
			url: '/user/user/' + userid,
			error: function(response) {
				
			},
			success: function(response) {
				$('.userSpotlight').html(ich.user(response));
			}
		});
		
	});
});
