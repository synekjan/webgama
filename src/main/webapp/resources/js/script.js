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

	//delete element click event 
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
	/*jQuery(document).on("blur", ".points input, .points select, .clusters input, .clusters select", function() {
		jQuery(this).parents('.window').removeClass('focused');
	});*/
	
	jQuery(document).on("click", ".title_bar", function() {
		jQuery('.adjustment_form').find('.focused').removeClass('focused');
		var element = jQuery(this).parents('.window').addClass('focused');
	});

	//TOOLBAR BUTTON EVENTS
	//add point	click event 
	jQuery("button.add_point").click(function () {
		var pointIndex = jQuery(".points").children().length;
		jQuery(".points").append(renderPoint(pointIndex));
	});
	
	//add observation click event 
	jQuery("button.add_observation").click(function () {
		var clusterIndex = jQuery(".clusters").children().length;
		jQuery(".clusters").append(renderObservation(clusterIndex));
	});
	
	//add direction click event
	jQuery("button.add_direction").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		var directionIndex = container.find(".directions").children().length;
		container.find(".directions").append(renderDirection(clusterIndex, directionIndex));
	});
	
	//add distance click event
	jQuery("button.add_distance").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		var distanceIndex = container.find(".distances").children().length;
		container.find(".distances").append(renderDistance(clusterIndex, distanceIndex));
	});
	
	//add angle click event
	jQuery("button.add_angle").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		var angleIndex = container.find(".angles").children().length;
		container.find(".angles").append(renderAngle(clusterIndex, angleIndex));
	});
	
	//add slope distancce click event
	jQuery("button.add_slopeDistance").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		var slopeDistanceIndex = container.find(".slopeDistances").children().length;
		container.find(".slopeDistances").append(renderSlopeDistance(clusterIndex, slopeDistanceIndex));
	});
	
	//add zenith angle click event
	jQuery("button.add_zenithAngle").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		var zenithAngleIndex = container.find(".zenithAngles").children().length;
		container.find(".zenithAngles").append(renderZenithAngle(clusterIndex, zenithAngleIndex));
	});
	
	//add height differences wrap click event 
	jQuery("button.add_heightDifferences").click(function () {
		var clusterIndex = jQuery(".clusters").children().length;
		var container = jQuery(renderHeightDifferences(clusterIndex)).appendTo('.clusters');
		container.find(".heightDifferences").append(renderHeightDifference(clusterIndex, 0));
	});
	
	//add height difference click event
	jQuery("button.add_heightDifference").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		var heightDifferenceIndex = container.find(".heightDifferences").children().length;
		container.find(".heightDifferences").append(renderHeightDifference(clusterIndex, heightDifferenceIndex));
	});
	
	//add coordinates wrap click event 
	jQuery("button.add_coordinates").click(function () {
		var clusterIndex = jQuery(".clusters").children().length;
		var container = jQuery(renderCoordinates(clusterIndex)).appendTo('.clusters');
		container.find(".coordinates").append(renderCoordinate(clusterIndex, 0));
	});
	
	//add coordinate click event
	jQuery("button.add_coordinate").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		var coordinateIndex = container.find(".coordinates").children().length;
		container.find(".coordinates").append(renderCoordinate(clusterIndex, coordinateIndex));
	});
	
	//add vectors wrap click event 
	jQuery("button.add_vectors").click(function () {
		var clusterIndex = jQuery(".clusters").children().length;
		var container = jQuery(renderVectors(clusterIndex)).appendTo('.clusters');
		container.find(".vectors").append(renderVector(clusterIndex, 0));
	});
	
	//add vector click event
	jQuery("button.add_vector").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		var vectorIndex = container.find(".vectors").children().length;
		container.find(".vectors").append(renderVector(clusterIndex, vectorIndex));
	});
	
	jQuery("button.add_covMat").click(function () {
		var container = jQuery('.clusters').children('.focused');
		if (container.length == 0) {
			return false;
		}
		var regex = /\[([0-9]+)\]/;
		var matches = container.find('input:hidden').prop('id').match(regex);
		if (matches) {
		    var clusterIndex = matches[1];
		}
		container.find(".covMatWrap").html(renderCovMat(clusterIndex));
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


