/** mongoDB **/
var mongoose = require('mongoose').connect('mongodb://localhost/test');
var db = mongoose.connection;
db.on('error', function(err){
	console.error('Error connecting to mongoDB, please start the daemon and restart TADS');
	throw err;
});

var userSchema = mongoose.Schema({
	electronicId: {type: String, index: {unique: true}},
  pdfs: [{
  	uuid: String
  }]
});

var verifierSchema = mongoose.Schema({
	url: String
});

var User = mongoose.model('user', userSchema);
var VerifierUrl = mongoose.model('verifierurl', verifierSchema);

exports.createUser = function(id, callback){
	var newUser = new User({electronicId : id});
	newUser.save(function (err, newUser) {
		if(err){
			return callback(err);
		}else{
			return callback();
		}
	});
}

exports.addUserPdf = function(userId, uuid, callback){
	userFind(userId, function(err, user){
		if(err) return callback(err);

		if(user === null){
			return callback(null, null);
		}else{
			user.pdfs.push({uuid: uuid});
			user.save(function(err){
				if(err){
					return callback(err);
				}
				return callback(null, user);
			});
		}
	});
}

exports.removeUserPdf = function(userId, uuid, callback){
	userFind(userId, function(err, user){
		if(err) return callback(err);
		if(!user){
			return callback(err, null);
		}else{
			for(var index in user.pdfs){
				if(user.pdfs[index].uuid === uuid){
					user.pdfs.splice(index, 1);	
				}	
			}
			user.save(function(err){
				if(err) return callback(err);
				return callback(null, user);
			});
		}
	});
}

exports.getUserData = function(userId, callback){
	userFind(userId, function(err, user){
		if(err){
			return callback(err);
		}else{
			if(!user){
				return callback(null, null);
			} else {
				return callback(null, user);	
			}
		}
	});
}

exports.getPdfUser = function(uuid, callback){
	User.findOne({"pdfs.uuid": uuid}, function(err, user){
		if(err) return callback(err);
		if(!user){
			return callback(null, null);
		}else{
			return callback(null, user);
		}
	});
}

exports.addVerifierUrl = function(uuid, callback){
	var newVerifierUrl = new VerifierUrl({url : uuid});
	newVerifierUrl.save(function (err, newVerifierUrl) {
		if(err){
			return callback(err);
		}else{
			return callback();
		}
	});
}

//Returns true if url exists, false otherwise
exports.checkVerifierUrl = function(url, callback){
	VerifierUrl.findOne({url: url}, function (err, url){
		if(err) return callback(err);
		if(!url){
			return callback(null, false);
		}else{
			return callback(null, true);
		}
	});
}

exports.delVerifierUrl = function(url, callback){
	VerifierUrl.remove({url: url}, function(err, res){
		if(err) return callback(err);
		if(!res){ 
			return callback(null, null);
		}else{
			return callback(null, res);
		}
	});
}

function userFind(id, callback){
	User.findOne({electronicId: id}, function (err, user){
		if(err) return callback(err);
		if(!user){
			return callback(null, null);
		}else{
			return callback(null, user);
		}
	});
}
