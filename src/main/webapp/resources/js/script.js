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
	$('.icon-close').click(function() {
		$('.success, .error').hide();
	});

	$(function() {
		$(".accordion").accordion({
			heightStyle : "content"
		});
	});
	
	$(".draggable").draggable({ handle: "#toolbar_header" });
	
	
	//add point	click event 
	jQuery("button.add_point").click(function () {
		var pointIndex = jQuery(".points").children().length;
		jQuery(".points").append(renderPoint(pointIndex));
	});

	//delete point click event 
	jQuery(document).on("click", "span.icon-close", function () {
		var parent = jQuery(this).parent();
		var container = jQuery(this).parent().parent();
		
		parent.fadeOut(500, function() {
			jQuery(this).remove();
			updateIds(container);		
		});
	});
	
	//Adjustment elements highlighting
	jQuery(document).on("focus",".points input, .points select, .clusters input, .clusters select", function() {
		jQuery('.adjustment_form').find('.focused').removeClass('focused');
		jQuery(this).parents('.window').addClass('focused');
	});
	jQuery(document).on("blur", ".points input, .points select, .clusters input, .clusters select", function() {
		jQuery(this).parents('.window').removeClass('focused');
	});
	
	jQuery(document).on("click", ".title_bar", function() {
		jQuery('.adjustment_form').find('.focused').removeClass('focused');
		var element = jQuery(this).parents('.window').addClass('focused');
	});

	
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

function updateIds(container) {
	var str = container.prop('class').split(' ')[0];
	var regex = new RegExp(str + "\\[[0-9]+\\]");

	container.children().each(function(index) {
		//update inputs 
		jQuery(this).find('input, select').each(function() {
			var name = jQuery(this).prop('name').replace(regex, str + '[' + index + ']');
			jQuery(this).prop('name', name);
			
			var id = jQuery(this).prop('id').replace(regex, str + '[' + index + ']');
			jQuery(this).prop('id', id);
		});
		//update labels 
		jQuery(this).find('label').each(function() {
			var _for = jQuery(this).prop('for').replace(regex, str + '[' + index + ']');
			jQuery(this).prop('for', _for);
			
		});
		
	});
}


