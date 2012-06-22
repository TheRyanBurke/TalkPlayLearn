var objectives = [];

$(function() {
	$('.newQuestForm').html(ich.newQuest());
	
	$('button.create').click(function(){
		var quest = {};
		quest.title = $('.questDetails #title').val();
		quest.description = $('.questDetails #description').val();
		quest.repeatability = $('.questDetails #repeatable').val();
		
		var rewards = {};
		rewards.academic = $('.rewards #academic').val();
		rewards.creativity = $('.rewards #creativity').val();
		rewards.enthusiasm = $('.rewards #enthusiasm').val();
		rewards.productivity = $('.rewards #productivity').val();
		rewards.socialness = $('.rewards #socialness').val();
		rewards.gamer = $('.rewards #gamer').val();
		
		quest.rewards = rewards;
		
		quest.objectives = objectives;
		
		$.post('/quest/new', {quest: JSON.stringify(quest)}, function(returnedQuest) {
			$('.questList').append(ich.quest(returnedQuest));
			$('.newQuestForm').html(ich.newQuest());
			objectives = [];
		});
		
	});
	
	$('button.addObjective').click(function(){
		var objective = {};
		objective.description = $('.objectives #description').val();
		objective.xp = $('.objectives #xp').val();
		objective.requiredCompletions = $('.objectives #completions').val();
		objective.bonus = $('.objectives #bonus').prop("checked");
		
		$.post('/objective/new', {objective: JSON.stringify(objective)}, function(returnedObjective) {
			$('.objectiveList').append(ich.objective(returnedObjective));
			$('.objectiveList').last().find('.engaged').hide();
			objectives.push(returnedObjective);
		});
		
	});
	
	
});