$(document).ready(function() {
	
	// show success box when form is successfully updated
	if(location.search == "?success=true"){
		  $('div.success').show();
		}

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

	$('#account_dropdown').click(function(e) { // not hide when click on
		// dropdown
		e.stopPropagation();
	});

	$('#locale_dropdown').click(function(e) { // not hide when click on
		// dropdown
		e.stopPropagation();
	});

	$(document).click(function() {
		$('#account_dropdown').slideUp(200);
		$('#locale_dropdown').slideUp(200);
	});

	$('#ajax_reset').click(function() {
		$('#ajax_result').html("");
		return false;
	});
	$('.success, .error').delay(8000).hide(1000);
	
	//TODO
	/*$('input.submit_btn').click(function() {
		var url = window.location.pathname;
		url = url.slice( 0, url.indexOf('?') );
	});*/
	
});

function wizardAjaxPostNext(stepUrl) {
	jQuery.ajax({
		type : 'POST',
		url : stepUrl,
		dataType : 'html',
		async : true,
		success : function(result) {
			jQuery('#wizard').hide("drop", {
				direction : "left"
			}, 500, function() {
				jQuery('#wizard').html(result);
			});
			jQuery('#wizard').show("drop", {
				direction : "right"
			}, 500);
			return false;
		}
	});
}

function wizardAjaxPostPrevious(stepUrl) {
	jQuery.ajax({
		type : 'POST',
		url : stepUrl,
		dataType : 'html',
		async : true,
		success : function(result) {
			jQuery('#wizard').hide("drop", {
				direction : "right"
			}, 500, function() {
				jQuery('#wizard').html(result);
			});
			jQuery('#wizard').show("drop", {
				direction : "left"
			}, 500);
			return false;
		}
	});
}



