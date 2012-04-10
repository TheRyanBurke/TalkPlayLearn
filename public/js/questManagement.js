$(function() {
	$('button.create').click(function(){
		
		
	});
	
	$('button.addObjective').click(function(){
		var objective = {};
		objective.description = $('.objectives #description').val();
		objective.xp = $('.objectives #xp').val();
		objective.requiredCompletions = $('.objectives #completions').val();
		objective.bonus = $('.objectives #bonus').prop("checked");
		
		$.post('/objectivecontroller/create', {objective: JSON.stringify(objective)}, function(returnedObjective) {
			$('.objectiveList').append(ich.objective(returnedObjective));
			$('.objectiveList').last().find('.engaged').hide();
		});
		
	});
	
	
});