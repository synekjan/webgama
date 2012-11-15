$(document).ready(function() {

	// DROPDOWN MENU behaviour
	$('a#account').click(function() {
		$('#locale_dropdown').slideUp(200);
		$('#account_dropdown').slideToggle(200);
		return false;
	});
	
	$('a#locales').click(function() {
		$('#account_dropdown').slideUp(200);
		$('#locale_dropdown').slideToggle(200);
		return false;
	});

	$('#account_dropdown').click(function(e) { //not hide when click on dropdown menu
		e.stopPropagation();
	});
	
	$('#locale_dropdown').click(function(e) { //not hide when click on dropdown menu
		e.stopPropagation();
	});

	$(document).click(function() {
		$('#account_dropdown').slideUp(200);
		$('#locale_dropdown').slideUp(200);
	});
});