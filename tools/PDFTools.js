var fs = require('fs');
var moment = require('moment-timezone');
var qr = require('qr-image');
var uuid = require('node-uuid');
var currentDate = moment().tz("Europe/London").format('dddd , Do MMMM YYYY h:mm:ss [CET]');
var PDFDocument = require('pdfkit');
var rest = require('restler');
var java = require('java');
var PdfPrinter = require('pdfmake');

var imagesDir = './public/images/';
var pdfDirPath = './private/pdfs/';

var configs = GLOBAL.configs;
var samlReturnUrl = configs.tads.samlReturnUrl;

//java.classpath.push('./java/STORKTADSUtils-1.4.0.jar');

var DiplomaSupplement = java.newInstanceSync('eu.stork.tads.EDS.DiplomaSupplement');
var CurrentStudies = java.newInstanceSync('eu.stork.tads.EDS.CurrentStudies');

var fonts = {
	Roboto: {
		normal: './public/fonts/Roboto-Regular.ttf',
		bold: './public/fonts/Roboto-Medium.ttf',
		italics: './public/fonts/Roboto-Italic.ttf',
		bolditalics: './public/fonts/Roboto-Italic.ttf'
	}
};

//Constants
var DIPLOMA_SUPPLEMENT = 'diplomaSupplement';
var CURRENT_STUDIES = 'currentStudiesSupplement';


exports.create = function(userId, attributes, callback){

	var pdfDir = pdfDirPath + userId + '/';
	var qrCodePath = pdfDir;
	var PDFuuid = 'TADS-' + configs.tads.country + '-' + uuid.v1();
	var	qrCodeNamePath = qrCodePath + PDFuuid + '.png';
	var pdfWritePath = pdfDir + PDFuuid + '.pdf';
	var formattedDiplomaSupplements = [];
	var formattedCurrentStudiesSupplements = [];
	var docDefinition = null;
	var attributesToPrint = null;

	generateQRCode(qrCodeNamePath, PDFuuid, function(err){

  		//Checks if EDS Exists
		var count = 0;
		if(DIPLOMA_SUPPLEMENT in attributes){
			attributes[DIPLOMA_SUPPLEMENT].forEach(function(s){
				formattedDiplomaSupplements.push(createDiplomaPDF(DIPLOMA_SUPPLEMENT, s));
			});
		}
		if(CURRENT_STUDIES in attributes){
			attributes[CURRENT_STUDIES].forEach(function(s){
				formattedCurrentStudiesSupplements.push(createDiplomaPDF(CURRENT_STUDIES, s));
			});
		}

		// Building Attributes
		attributesToPrint = formatAttributesForPDF(attributes);

		docDefinition = {
			pageMargins: [ 50, 20, 50, 80 ],
			footer: function(currentPage, pageCount) { 

				return a = {
					margin: 20,
					columnGap: 40,
					columns: [
					{ image: imagesDir + 'ama.jpg', width: 130 },
					{ image: imagesDir + 'ist.png', width: 130 },
					{
						stack: [
						{ text: 'Emails: tads@ama.pt, tads@tecnico.ulisboa.pt', color: 'grey', fontSize: 8},        
						{ text: 'Phone: +351 218 417 506', color: 'grey', fontSize: 8},
						{ text: 'Address: Instituto Superior Técnico,', color: 'grey', fontSize: 8},
						{ text: 'Av. Rovisco Pais, 1, 1049-001 Lisboa , Portugal', color: 'grey', fontSize: 8}
						]
					}
					]
				}
			},

			content: [
			{
				columns: [
				{
				  // if you specify both width and height - image will be stretched
				  image: imagesDir + 'stork.jpg',
				  alignment: 'left',
				  width: 126.85032 
				},
				{ 
					stack: [
					{text: 'Trustworthy', fontSize: 25, alignment: 'right'},        
					{text: 'Validation Service', fontSize: 25, alignment: 'right'}
					]
				},
				]
			},
			'\n\n',
			{ text: 'Instructions of Use', fontSize: 12, alignment: 'center' },
			{
				table: {
					body: [
					['To validate this document please check the site http://tads.tecnico.ulisboa.pt and follow the instructions in the screen.' +
					' You will be asked to accept the usage of the webcam in your computer to read the QRCode' +
					' below. If you have no camera or are unwilling to use it for this purpose you will be' +
					' asked to enter the number below the QR-Code.' ],
					]
				}
			},
			'\n',
			{
				columns: [
				{
				  // if you specify both width and height - image will be stretched
				  image: qrCodeNamePath,
				  alignment: 'left',
				  width: 126.85032

				},
				{
					width: 'auto',
					height: 600,
					table: {
						body: [
						[ 'Document Digital Signature' ],
						]
					},
					alignment: 'right'
				}
				]
			},
			{
				width: 'auto',
				table: {
					body: [
					[ PDFuuid ],
					]
				},
				alignment: 'left'
			},
			{
				canvas: [
				{
					type: 'line',
					x1: 0, y1: 20,
					x2: 495, y2: 20,
					lineWidth: 2
				}
				]
			},
			'\n',
			{ text: 'Certified Information', bold: 'true', fontSize: 18, alignment: 'center' },
			'\n',
			{
				columns: [
				{ 
					alignment: 'left',
					text: [
					{text: 'Retrieved From: ', color: 'grey'},
					'ES-PEPS'
					]
				},
				{ 
					width: 'auto',
					text: [
					{text: 'On Date: ', color: 'grey'},
					currentDate
					]
				}
				]
			},
			'\n',
			{
				canvas: [
				{
					type: 'line',
					x1: 0, y1: 20,
					x2: 495, y2: 20,
					lineWidth: 2
				}
				]
			},
			'\n',
			{ text: 'Disclaimer', bold: 'true', fontSize: 18, alignment: 'center' },
			'\n',
			{
				text: 'This service is provided to you in accordance with the terms of the STORK 2.0 Memorandum of Understanding. ' 
				+ 'Security and quality assurance measures which we deem to be appropriate have been implemented in accordance with the ' 
				+ 'Memorandum. Please note however that the Memorandum excludes any assurances or liabilities for the availability, '
				+ 'trustworthiness or accuracy of the identity information provided via its infrastructure. '
				+ 'You should assess and decide for yourself whether these terms are acceptable for your individual use case.',
				fontSize: 11
			},
			{
				canvas: [
				{
					type: 'line',
					x1: -3, y1: 20,
					x2: 495, y2: 20,
					lineWidth: 2
				}
				],
				pageBreak: 'after'
			},
			'\n',
			{ text: 'Attributes', bold: 'true', fontSize: 18, alignment: 'center'},
			'\n',

			attributesToPrint,

			formattedDiplomaSupplements,

			formattedCurrentStudiesSupplements

			],
			styles: {
				attrStyle: {
					fontSize: 12,
					margin: [5, 5]
				},
				header: {
					fontSize: 14,
					bold: true,
					margin: [0, 0, 0, 10]
				},
				subheader: {
					fontSize: 13,
					bold: true,
					margin: [0, 10, 0, 5]
				},
				tableExample: {
					margin: [0, 0, 0, 0, 0, 0]
				},
				tableHeader: {
					bold: true,
					fontSize: 12,
					color: 'black'
				}
			},
			defaultStyle: {
				columnGap: 100
			}
		};

		var printer = new PdfPrinter(fonts);
		var pdfDoc = printer.createPdfKitDocument(docDefinition);
		var wstream = fs.createWriteStream(pdfWritePath);
	
		pdfDoc.pipe(wstream);
		pdfDoc.end();

		wstream.on('finish', function(){
			return callback(null, PDFuuid);	
		});
	});
};

var formatAttributesForPDF = function(attributes){

	var attrs = [];
	for(var attrName in attributes){
		if(attrName === DIPLOMA_SUPPLEMENT || attrName === CURRENT_STUDIES)
			continue
	  	//Attribute is complex
	  	attributes[attrName].forEach(function(attr){
	  		if(attr instanceof Object){      
	  			attrs.push({ text: attrName, bold: 'true' });
	  			for(var innerElement in attr){
	  				attrs.push({ text: innerElement + ': ' + attr[innerElement]});
	  			}
	  			attrs.push('\n');
	  		//Attribute is simple
			}else{
				attrValStr = attr;
				attrs.push({ text: [
					{ text: attrName + ': ', bold: 'true' },
						attrValStr
					]});
				attrs.push('\n');
			}
		});
	}
	return attrs;
};

var createDiplomaPDF = function(diplomaTypeString, diplomaXMLString){

	var EDSContent = '';
	var EDSTitle = '';
	var EDSDetails = '';

	EDSDetails = {
		title : [
		[
		{ text: 'Execution Year', style: 'tableHeader' }, 
		{ text: 'Curricular Unit', style: 'tableHeader' }, 
		{ text: 'Type', style: 'tableHeader' },
		{ text: 'Duration', style: 'tableHeader' }, 
		{ text: 'ECTS Credits', style: 'tableHeader' }, 
		{ text: 'Local Grade', style: 'tableHeader' }]
		]
	};

	var result = null;

	if(diplomaTypeString === DIPLOMA_SUPPLEMENT){
		result = DiplomaSupplement.getDiplomaSupplementJsonSync(diplomaXMLString); 
		EDSTitle = 'European Diploma Supplement';
	}
	if(diplomaTypeString === CURRENT_STUDIES){
		result = CurrentStudies.getCurrentStudiesJsonSync(diplomaXMLString);
		EDSTitle = 'Current Studies Diploma';
	}

	jsJson = JSON.parse(result);

	var number1 = jsJson.InformationIdentifyingTheHolderOfTheQualification;
	var number2 = jsJson.InformationIdentifyingTheQualification;
	var number3 = jsJson.InformationOnTheLevelOfTheQualification;
	var number4 = jsJson.InformationOnTheContentsAndResultsGained;
	var number5 = jsJson.InformationOnTheFunctionOfTheQualification;
	var number6 = jsJson.AdditionalInformation;
	var number7 = jsJson.CertificationoftheSupplement;
	var number8 = jsJson.InformationOnTheNationalHigherEducationSystem;

	pd = number4['Programme details'];

	for(var el in pd){
		EDSDetails.title.push(
			[ pd[el]['Academic Year'], pd[el].Name, pd[el].Type, 'Semestral', pd[el]['ECTS Credits'].toString(), pd[el]['Local Grade']]
		);	
	}

	EDSContent = [
	{ text: EDSTitle, bold: 'true', fontSize: 18, alignment: 'center', pageBreak: 'before'},
	'\n',

	{ text: '1. Information Identifying The Holder Of The Qualification', style: 'subheader' },

	'1.1 Family Name(s): ' + number1['First Name'],
	'1.2 Given Name(s): ' + number1.Surname,
	'1.3 Date of birth (year/month/day): ' + number1['Date Of Birth'],
	'1.4 Student identification number or code (if available): ' + number1['Student identification number'],

	{ text: '2. Information Identifying The Qualification', style: 'subheader' },

	'2.1 Name of qualification and title conferred: ',
	{ text: getAttributeFromJson(number2, 'Name of qualification and title conferred'), style: 'attrStyle' },

	'2.2 Main field(s) of study for the qualification: ',
	{ text: getAttributeFromJson(number2, 'Main field(s) of study for the qualification'), style: 'attrStyle' },

	'2.3 Name and status of awarding institution: ',
	{ text: getAttributeFromJson(number2, 'Name and status of awarding institution'), style: 'attrStyle' },

	'2.4 Name and status of institution administering studies: ',
	{ text: getAttributeFromJson(number2, 'Name and status of institution administering studies'), style: 'attrStyle' },

	'2.5 Language(s) of instruction/examination: ',
	{ text: getAttributeFromJson(number2, 'Language(s) of instruction/examination'), style: 'attrStyle' },


	{ text: '3. Information On The Level Of The Qualification', style: 'subheader' },

	'3.1 Level of qualification: ',
	{ text: getAttributeFromJson(number3, 'Level of qualification'), style: 'attrStyle' },

	'3.2 Official length of programme: ',
	{ text: getAttributeFromJson(number3, 'Official length of programme').toString(), style: 'attrStyle' },

	'3.3 Access requirements: ',
	{ text: getAttributeFromJson(number3, 'Access Requirements'), style: 'attrStyle' },


	{ text: '4. Information On The Contents And Results Gained', style: 'subheader' },

	'4.1 Mode of study: ',
	{ text: getAttributeFromJson(number4, 'Mode of study'), style: 'attrStyle' },

	'4.2 Programme requirements: ',
	{ text: getAttributeFromJson(number4, 'Programme requirements'), style: 'attrStyle' },

	'4.3 Programme details: ',
	{
		style: 'tableExample',
		table: {
			headerRows: 1,
			body: EDSDetails.title
		},
		layout: 'lightHorizontalLines'
	},

	'4.4 Grading scheme and, if available, grade distribution guidance: ',
	{ text: getAttributeFromJson(number4, 'Grading scheme and, if available, grade distribution guidance'), style: 'attrStyle' },

	'4.5 Overral classification of the qualification (in original language): ',
	{ text: getAttributeFromJson(number4, 'Overral classification of the qualification (in original language)'), style: 'attrStyle' },


	{ text: '5. Information On The Function Of The Qualification', style: 'subheader' },

	'5.1 Access to Further Study: ',
	{ text: getAttributeFromJson(number5, 'Access to Further Study'), style: 'attrStyle' },

	'5.2 Professional Status: ',
	{ text: getAttributeFromJson(number5, 'Professional Status'), style: 'attrStyle' },


	{ text: '6. Additional Information', style: 'subheader' },

	'6.1 Additional information: ',
	{ text: getAttributeFromJson(number6, 'Additional Information'), style: 'attrStyle' },

	'6.2 Further information sources: ',
	{ text: getAttributeFromJson(number6, 'Further information sources'), style: 'attrStyle' },

	{ text: '7. Certification of the Supplement', style: 'subheader' },

	'7.1 Date: ',
	{ text: getAttributeFromJson(number7, 'Date'), style: 'attrStyle' },

	'7.2 Signature and Capacity: ',
	{ text: putSignatureAndCapacity(number7) , style: 'attrStyle' },

	'7.3 Official stamp or seal: ',

	{ text: '8. Information On The National Higher Education System', style: 'subheader' }
	];

	return EDSContent;

};

var getAttributeFromJson = function(attributes, attributeName){

	var attrToReturn = '';
	if(attributes.length != 0){
		if(attributes[attributeName].length > 1 && (attributes[attributeName].constructor === Array)){
			for(var el in attributes[attributeName]){
				// doc.text(attributes[attributename][el], doc.x, doc.y);
				attrToReturn += attributes[attributeName][el] + '\n';
			}
		}else{
			// doc.text(attributes[attributename], doc.x, doc.y);
			attrToReturn = attributes[attributeName];
		}
	}else{
		attrToReturn = '\n';
	}
	return attrToReturn;
};

var putSignatureAndCapacity = function(attributes){

	if(attributes.length != 0){

		var details = attributes['Signature and Capacity'];
		var attrToReturn = '';

		for(var official in details){  
			attrToReturn += details[official].Name + '\n';
			attrToReturn += details[official].Capacity + '\n';
		}
	}else{
		attrToReturn = '\n';
	}
	return attrToReturn;
};


var generateQRCode = function(qrCodeNamePath, uuid, callback){

	var qr_png = qr.image(uuid, { 
		type: 'png'
	});

	var temp = fs.createWriteStream(qrCodeNamePath);

	temp.on('finish', function(err){
		if(err) return callback(err);
		return callback(null);
	});

	temp.on('error', function(err){
		return callback(err);
	});
	
	qr_png.pipe(temp);
}

exports.signPDF = function(userId, PDFuuid, callback){
	var pdfDir = pdfDirPath + userId + '/';
	var pdfPath = pdfDir + PDFuuid + '.pdf';
	var endpoint = configs.signserver.endpoint;

	var options = {
		encoding: 'base64',
		flag: 'r'
	};

	fs.readFile(pdfPath, options, function(err, pdfData){

		if(err) return callback(err);

		rest.post(endpoint, {
			data: {
				workerName: 'PDFSigner',
				encoding: 'base64',
				processType: 'signDocument',
				data: pdfData
			}
		}).on('complete', function(data, res){
			if(res === null){
				console.error('----ERROR----');
				console.error('There was a problem contacting SignServer');
				console.error('Please check that SignServer is running and the endpoint in tads.properties is well configured.');
				throw data;
			}else if(res.statusCode === 200){
				fs.writeFileSync(pdfPath, res.raw);
				return callback(null);
			}else{
				var err = 'There was a problem with the SignServer response, code: '+res.statusCode;
				return callback(err);
			}
		});
	});
}
