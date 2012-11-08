$(document).ready(function() {

	// DROPDOWN MENU behaviour
	$('.active_link').click(function() {
		$('#account_dropdown').slideToggle(200);
		return false;
	});

	$('#account_dropdown').click(function(e) { //not hide when click on dropdown menu
		e.stopPropagation();
	});

	$(document).click(function() {
		$('#account_dropdown').slideUp(200);
	});
});