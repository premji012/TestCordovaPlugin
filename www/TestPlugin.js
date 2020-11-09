var cordova = require('cordova');

window.printNativeLog = function(str, callback) {
    cordova.exec(callback, function(err) {
        callback('Nothing to echo.');
    }, "AndroidLog", "printLog", [str]);
};
