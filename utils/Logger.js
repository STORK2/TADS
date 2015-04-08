var winston = require('winston');

options={
  filename: './logs/tads.log',
  colorize: false,
  timestamp: true,
  json: false
}

winston.add(winston.transports.File, options);
//winston.remove(winston.transports.Console);

exports.log = function(type, message, meta){
  if(typeof meta === 'undefined'){
    winston.log(type, message);  
  }else{
    winston.log(type, message, meta);
  }
}
