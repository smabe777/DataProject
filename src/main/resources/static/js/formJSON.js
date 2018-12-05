 /**
               * Checks if an element’s value can be saved (e.g. not an unselected checkbox).
               * @param  {Element} element  the element to check
               * @return {Boolean}          true if the value should be added, false if not
               */
var isValidValue = function isValidValue(element) {
  return !['checkbox', 'radio'].includes(element.type) || element.checked;
};
/**
    * Checks that an element has a non-empty `name` and `value` property.
    * @param  {Element} element  the element to check
    * @return {Bool}             true if the element is an input, false if not
    */
var isValidElement = function isValidElement(element) {
  return element.name /*&& element.value*/;
};
/**
    * Retrieves input data from a form and returns it as a JSON object.
    * @param  {HTMLFormControlsCollection} elements  the form elements
    * @return {Object}                               form data as an object literal
    */
var formToJSON = function formToJSON(elements) {
	return [].reduce.call(elements, function (data, element) {
    if (isValidElement(element) /* && isValidValue(element)*/) {
        if (element.className.includes("radio") || element.className.includes("checkbox"))
        data[element.value] = element.checked;else

        data[element.name] = element.value;
      }
    return data;

  }, {});
};

var popupJSON = function popupJSON(){
		var form = document.getElementById("PopupForm");
		  var data = formToJSON(form.elements);
		  var senddata = JSON.stringify(data, null, "  ");
		  alert(data);
		  alert(senddata);
		  sendJSON(senddata);
		  };
		  
	var sendJSON = function sendJSON(json){
		 xhttp=new XMLHttpRequest();

		xhttp.onreadystatechange = function() {
			  if (this.readyState == 4 && this.status == 200) {
				var dataContainer = document.getElementsByClassName('results__display')[0];
				  dataContainer.textContent = this.responseText;
			  }
			};
			xhttp.open("POST", "http://localhost:8080/Calendar", true);
			xhttp.setRequestHeader('Content-Type', 'application/json');
			xhttp.send(json); 
	};
	var formToJSON = function formToJSON(elements) {
		return [].reduce.call(elements, function (data, element) {
	    if (isValidElement(element) /* && isValidValue(element)*/) {
	        if (element.className.includes("radio") || element.className.includes("checkbox"))
	        data[element.value] = element.checked;else

	        data[element.name] = element.value;
	      }
	    return data;

	  }, {});};