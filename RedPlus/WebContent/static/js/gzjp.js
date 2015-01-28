var forDayInterval = 24 * 60*60 * 1000;
if(! window.console){
	window.console = {};
	console = {};
	console.log = function(e){ 
		//alert(e);
	};
	window.console = console;
}