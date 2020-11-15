var cordova = require('cordova');

window.loadModule = function(str, callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "AndroidLog", "loadModule", [str]);
};

window.pageLoaded = function(str, callback) {
	setTimeout(function(){ 
		cordova.exec(callback, function(err) {
		    callback('Nothing to echo.');
		}, "AndroidLog", "pageLoaded", [str]); 
    }, 4000);
};