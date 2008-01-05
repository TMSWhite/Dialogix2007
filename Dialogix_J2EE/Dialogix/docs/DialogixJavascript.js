var now=null;
var val=null;
var msg=null;
var target=null;
var targetType=null;
var targetName=null;
var targetText=null;
var startTime=new Date();
var loadTime=null;

function addListener(towhom) {
	if (!document.getElementById) {
		return;
	}
	
	tome = document.getElementById(towhom.id);
	tome._dlxType = towhom.type;
	tome._dlxName = towhom.id;
		
	if (window.addEventListener) { //DOM method for binding an event
		tome.addEventListener('blur', eventHandler, false);
		tome.addEventListener('change', eventHandler, false);
		tome.addEventListener('click', eventHandler, false);
		tome.addEventListener('focus', eventHandler, false);
		tome.addEventListener('load', eventHandler, false);
		tome.addEventListener('mouseup', eventHandler, false);
		tome.addEventListener('submit', eventHandler, false);
	}
	else if (window.attachEvent) { //IE exclusive method for binding an event
		tome.attachEvent('onblur', eventHandler);
		tome.attachEvent('onchange', eventHandler);
		tome.attachEvent('onclick', eventHandler);
		tome.attachEvent('onfocus', eventHandler);
		tome.attachEvent('onload', eventHandler);
		tome.attachEvent('onmouseup', eventHandler);
		tome.attachEvent('onsubmit', eventHandler);
	}
	else {
		tome.onblur=eventHandler;
		tome.onchange=eventHandler;
		tome.onclick=eventHandler;
		tome.onfocus=eventHandler;
		tome.onload=eventHandler;
		tome.onmouseup=eventHandler;
		tome.onsubmit=eventHandler;
	}
}


function init() {
	for (i =0; i< _dlxObjects.length; ++i) {
		addListener(_dlxObjects[i]);
	}
	loadTime = new Date();
}

function eventHandler(evt) {
	now = new Date();
	target = (evt.target || evt.srcElement);
	if (target) {
		targetType = target._dlxType;
		targetName = target._dlxName;
		targetText = target.text;
	}
	
	if (evt && evt.type) {
		switch (evt.type) {
			default: {
  			msg = targetName + ',Default-' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
  			break;
  		}
		  case ('blur'): { 
		  	switch (targetType) {
		  		case ('button'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  		case ('option'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('checkbox'): {
                                     if (target.checked) { val = target.value; } else { val = 'null'; }
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + targetText;
		  			break;
		  		}
		  		case ('radio'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('select-one'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + target.options[target.selectedIndex].text;
		  			break;
		  		}
		  		case ('submit'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			var name = document.dialogixForm.elements['DIRECTIVE_' + targetName].value;
		  			target.value='    ' + name + '    ';
		  			document.dialogixForm.DIRECTIVE.value = targetName;
		  			break;
		  		}
		  		case ('text'): case ('password'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}
		  		case ('textarea'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}		  		
		  		default: {
		  			msg = targetName + ',default-' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  	}
		  	break;
		  	}								
		  case ('change'): { 
		  	switch (targetType) {
		  		case ('button'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  		case ('option'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('checkbox'): {
                                     if (target.checked) { val = target.value; } else { val = 'null'; }
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + targetText;
		  			break;
		  		}
		  		case ('radio'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('select-one'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + target.options[target.selectedIndex].text;
		  			break;
		  		}
		  		case ('submit'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			document.dialogixForm.DIRECTIVE.value = targetName;
		  			break;
		  		}
		  		case ('text'): case ('password'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}
		  		case ('textarea'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}		  		
		  		default: {
		  			msg = targetName + ',default-' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  	}
		  		break;		  	
		  	}						
		  case ('click'): { 
		  	switch (targetType) {
		  		case ('button'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  		case ('option'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('radio'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('checkbox'): {
                                     if (target.checked) { val = target.value; } else { val = 'null'; }
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + targetText;
		  			break;
		  		}
		  		case ('select-one'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + target.options[target.selectedIndex].text;
		  			break;
		  		}
		  		case ('submit'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			document.dialogixForm.DIRECTIVE.value = targetName;
		  			break;
		  		}
		  		case ('text'): case ('password'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}
	  			case ('textarea'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}		  		
		  		default: {
		  			msg = targetName + ',default-' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  	}
				break;		  	
		  	}													
		  case ('focus'): { 
		  	switch (targetType) {
		  		case ('button'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  		case ('option'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('radio'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('checkbox'): {
                                     if (target.checked) { val = target.value; } else { val = 'null'; }
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + targetText;
		  			break;
		  		}
		  		case ('select-one'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + target.options[target.selectedIndex].text;
		  			break;
		  		}
		  		case ('submit'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			var name = document.dialogixForm.elements['DIRECTIVE_' + targetName].value;
		  			if (targetName == 'next') {
		  				target.value='    ' + name + '>>';
		  			}
		  			else if (targetName == 'previous') {
		  				target.value='<<' + name + '    ';
		  			}
		  			else {
		  				target.value='<<' + name + '>>';
		  			}
		  			document.dialogixForm.DIRECTIVE.value = targetName;
		  			break;
		  		}
		  		case ('text'): case ('password'): {
		  			target.select();
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}
		  		case ('textarea'): {
		  			target.select();
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}		  		
		  		default: {
		  			msg = targetName + ',default-' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  	}
				break;		  	
		  	}													
		  case ('keyup'): { 
		  	switch (targetType) {
		  		case ('button'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  		case ('option'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('radio'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('checkbox'): {
                                     if (target.checked) { val = target.value; } else { val = 'null'; }
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + targetText;
		  			break;
		  		}
		  		case ('select-one'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + target.options[target.selectedIndex].text;
		  			break;
		  		}
		  		case ('submit'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			document.dialogixForm.DIRECTIVE.value = targetName;
		  			break;
		  		}
		  		case ('text'): case ('password'): {
		  			var val = null;
		  			if (evt.keyCode) {
		  				val = String.fromCharCode(evt.keyCode);
		  			}
		  			else if (evt.which) {
		  				val = String.fromCharCode(evt.which);
		  			}
		  			else {
		  				val = 'null';
		  			}
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + target.value;
		  			break;
		  		}
		  		case ('textarea'): {
		  			var val = null;
		  			if (evt.keyCode) {
		  				val = String.fromCharCode(evt.keyCode);
		  			}
		  			else if (evt.which) {
		  				val = String.fromCharCode(evt.which);
		  			}
		  			else {
		  				val = 'null';
		  			}
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + target.value;
		  			break;
		  		}		  		
		  		default: {
		  			msg = targetName + ',default-' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  	}
				break;		  	
		  	}													
		  case ('load'): { 
		  	msg = 'load' + ',' + evt.type + ',' + 'load' + ',' + now.getTime() + ',' + (now.getTime() - startTime.getTime()) + ',' + 'null' + ',' + 'null';
				break;		  	
		  	}													
		  case ('mouseup'): { 
		  	switch (targetType) {
		  		case ('button'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  		case ('option'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('radio'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('checkbox'): {
                                     if (target.checked) { val = target.value; } else { val = 'null'; }
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + targetText;
		  			break;
		  		}
		  		case ('select-one'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + target.options[target.selectedIndex].text;
		  			break;
		  		}
		  		case ('submit'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			document.dialogixForm.DIRECTIVE.value = targetName;
		  			break;
		  		}
		  		case ('text'): case ('password'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}
		  		case ('textarea'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}		  		
		  		default: {
		  			msg = targetName + ',default-' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  	}
				break;		  	
		  	}													
		  case ('submit'): { 
		  	switch (targetType) {
		  		case ('button'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  		case ('option'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('radio'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + targetText;
		  			break;
		  		}
		  		case ('checkbox'): {
                                     if (target.checked) { val = target.value; } else { val = 'null'; }
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + val + ',' + targetText;
		  			break;
		  		}
		  		case ('select-one'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + target.options[target.selectedIndex].text;
		  			break;
		  		}
		  		case ('submit'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			document.dialogixForm.DIRECTIVE.value = targetName;
		  			break;
		  		}
		  		case ('text'): case ('password'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}
		  		case ('textarea'): {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + target.value + ',' + 'null';
		  			break;
		  		}		  		
		  		default: {
		  			msg = targetName + ',' + evt.type + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
		  			break;
		  		}
		  	}
				break;		  	
		  	}														
  	}
	}
	else {
		msg = targetName + ',' + 'null' + ',' + targetType + ',' + now.getTime() + ',' + (now.getTime() - loadTime.getTime()) + ',' + 'null' + ',' + 'null';
	}
	msg += '	';
	document.dialogixForm.EVENT_TIMINGS.value += msg;
	return true;
		
}	