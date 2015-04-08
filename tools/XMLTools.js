var xmldoc = require('xmldoc');

exports.getSAMLAttributes = function(xmlData, callback){
	
	var xmlDocument = new xmldoc.XmlDocument(xmlData);
	//var responseChildren = results.childNamed('saml2:Response');
	var xmlAttr = {};

	xmlDocument.eachChild(function(child){
		
		if(child.name === 'saml2:Assertion'){

			child.eachChild(function(attrStatement){

				if(attrStatement.name === 'saml2:AttributeStatement'){

					attrStatement.eachChild(function(attribute){

						if(attribute.name === 'saml2:Attribute'){

							var attrName = attribute.attr.Name.split('/').pop();

							attribute.eachChild(function(attributeValue){

								if(attrName === 'eIdentifier'){

									var eid = attributeValue.val.split('/').join('-');
									addAttributeToDictionary(attrName, eid, xmlAttr);

								}else if(attributeValue.val === ''){//Complex Attribute
									var subAttrs = {};
								
									attributeValue.eachChild(function(subAttr){
							 			var subAttrName = subAttr.name.split(":").pop();
							 			subAttrs[subAttrName] = subAttr.val;
							 		});

									addAttributeToDictionary(attrName, subAttrs, xmlAttr);
					
								}else{

									addAttributeToDictionary(attrName, attributeValue.val, xmlAttr);
									
								}
							});
						}
					});
				}
			});
		}
	});

	return callback(xmlAttr);

}

function addAttributeToDictionary(name, value, dictionary){

	if(!(name in dictionary)){
		dictionary[name] = [value];
	}else{
		if(!containsValue(dictionary[name], value)){
			dictionary[name].push(value);
			dictionary[name].sort();
		}
	}
}

//Checks if the array contains the given value
function containsValue(array, value){
	var result = false;

	array.some(function(val, index, array){
		result = (JSON.stringify(val) === JSON.stringify(value));
	});

	return result;
}


	

