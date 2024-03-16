JotForm.newDefaultTheme = true;
JotForm.extendsNewTheme = false;
JotForm.singleProduct = false;
JotForm.newPaymentUIForNewCreatedForms = false;
JotForm.texts = {
	"confirmEmail": "E-mail does not match",
	"pleaseWait": "Please wait...",
	"validateEmail": "You need to validate this e-mail",
	"confirmClearForm": "Are you sure you want to clear the form",
	"lessThan": "Your score should be less than or equal to",
	"incompleteFields": "There are incomplete required fields. Please complete them.",
	"required": "This field is required.",
	"requireOne": "At least one field required.",
	"requireEveryRow": "Every row is required.",
	"requireEveryCell": "Every cell is required.",
	"email": "Enter a valid e-mail address",
	"alphabetic": "This field can only contain letters",
	"numeric": "This field can only contain numeric values",
	"alphanumeric": "This field can only contain letters and numbers.",
	"cyrillic": "This field can only contain cyrillic characters",
	"url": "This field can only contain a valid URL",
	"currency": "This field can only contain currency values.",
	"fillMask": "Field value must fill mask.",
	"uploadExtensions": "You can only upload following files:",
	"noUploadExtensions": "File has no extension file type (e.g. .txt, .png, .jpeg)",
	"uploadFilesize": "File size cannot be bigger than:",
	"uploadFilesizemin": "File size cannot be smaller than:",
	"gradingScoreError": "Score total should only be less than or equal to",
	"inputCarretErrorA": "Input should not be less than the minimum value:",
	"inputCarretErrorB": "Input should not be greater than the maximum value:",
	"maxDigitsError": "The maximum digits allowed is",
	"minCharactersError": "The number of characters should not be less than the minimum value:",
	"maxCharactersError": "The number of characters should not be more than the maximum value:",
	"freeEmailError": "Free email accounts are not allowed",
	"minSelectionsError": "The minimum required number of selections is ",
	"maxSelectionsError": "The maximum number of selections allowed is ",
	"pastDatesDisallowed": "Date must not be in the past.",
	"dateLimited": "This date is unavailable.",
	"dateInvalid": "This date is not valid. The date format is {format}",
	"dateInvalidSeparate": "This date is not valid. Enter a valid {element}.",
	"ageVerificationError": "You must be older than {minAge} years old to submit this form.",
	"multipleFileUploads_typeError": "{file} has invalid extension. Only {extensions} are allowed.",
	"multipleFileUploads_sizeError": "{file} is too large, maximum file size is {sizeLimit}.",
	"multipleFileUploads_minSizeError": "{file} is too small, minimum file size is {minSizeLimit}.",
	"multipleFileUploads_emptyError": "{file} is empty, please select files again without it.",
	"multipleFileUploads_uploadFailed": "File upload failed, please remove it and upload the file again.",
	"multipleFileUploads_onLeave": "The files are being uploaded, if you leave now the upload will be cancelled.",
	"multipleFileUploads_fileLimitError": "Only {fileLimit} file uploads allowed.",
	"dragAndDropFilesHere_infoMessage": "Drag and drop files here",
	"chooseAFile_infoMessage": "Choose a file",
	"maxFileSize_infoMessage": "Max. file size",
	"generalError": "There are errors on the form. Please fix them before continuing.",
	"generalPageError": "There are errors on this page. Please fix them before continuing.",
	"wordLimitError": "Too many words. The limit is",
	"wordMinLimitError": "Too few words.  The minimum is",
	"characterLimitError": "Too many Characters.  The limit is",
	"characterMinLimitError": "Too few characters. The minimum is",
	"ccInvalidNumber": "Credit Card Number is invalid.",
	"ccInvalidCVC": "CVC number is invalid.",
	"ccInvalidExpireDate": "Expire date is invalid.",
	"ccInvalidExpireMonth": "Expiration month is invalid.",
	"ccInvalidExpireYear": "Expiration year is invalid.",
	"ccMissingDetails": "Please fill up the credit card details.",
	"ccMissingProduct": "Please select at least one product.",
	"ccMissingDonation": "Please enter numeric values for donation amount.",
	"disallowDecimals": "Please enter a whole number.",
	"restrictedDomain": "This domain is not allowed",
	"ccDonationMinLimitError": "Minimum amount is {minAmount} {currency}",
	"requiredLegend": "All fields marked with * are required and must be filled.",
	"geoPermissionTitle": "Permission Denied",
	"geoPermissionDesc": "Check your browser's privacy settings.",
	"geoNotAvailableTitle": "Position Unavailable",
	"geoNotAvailableDesc": "Location provider not available. Please enter the address manually.",
	"geoTimeoutTitle": "Timeout",
	"geoTimeoutDesc": "Please check your internet connection and try again.",
	"selectedTime": "Selected Time",
	"formerSelectedTime": "Former Time",
	"cancelAppointment": "Cancel Appointment",
	"cancelSelection": "Cancel Selection",
	"noSlotsAvailable": "No slots available",
	"slotUnavailable": "{time} on {date} has been selected is unavailable. Please select another slot.",
	"multipleError": "There are {count} errors on this page. Please correct them before moving on.",
	"oneError": "There is {count} error on this page. Please correct it before moving on.",
	"doneMessage": "Well done! All errors are fixed.",
	"invalidTime": "Enter a valid time",
	"doneButton": "Done",
	"reviewSubmitText": "Review and Submit",
	"nextButtonText": "Next",
	"prevButtonText": "Previous",
	"seeErrorsButton": "See Errors",
	"notEnoughStock": "Not enough stock for the current selection",
	"notEnoughStock_remainedItems": "Not enough stock for the current selection ({count} items left)",
	"soldOut": "Sold Out",
	"justSoldOut": "Just Sold Out",
	"selectionSoldOut": "Selection Sold Out",
	"subProductItemsLeft": "({count} items left)",
	"startButtonText": "START",
	"submitButtonText": "Submit",
	"submissionLimit": "Sorry! Only one entry is allowed. Multiple submissions are disabled for this form.",
	"reviewBackText": "Back to Form",
	"seeAllText": "See All",
	"progressMiddleText": "of",
	"fieldError": "field has an error.",
	"error": "Error"
};
JotForm.newPaymentUI = true;
JotForm.isLaterCharge = "immediately";
JotForm.replaceTagTest = true;
JotForm.submitError = "jumpToFirstError";
window.addEventListener('DOMContentLoaded', function() {
	window.brandingFooter.init({
		"formID": 240735485454462,
		"campaign": "powered_by_jotform_le",
		"isCardForm": false,
		"isLegacyForm": true
	})
});
JotForm.init(function() {
	/*INIT-START*/
	productID = {
		"0": "input_24_1001",
		"1": "input_24_1003"
	};
	paymentType = "product";
	JotForm.setCurrencyFormat('USD', true, 'point');
	JotForm.totalCounter({
		"input_24_1001": {
			"price": "50.00",
			"quantityField": "input_24_quantity_1001_0",
			"custom_1": "input_24_custom_1001_1",
			"custom_2": "input_24_custom_1001_2",
			"custom_3": "input_24_custom_1001_3"
		},
		"input_24_1003": {
			"price": "47.00",
			"quantityField": "input_24_quantity_1003_0",
			"custom_1": "input_24_custom_1003_1",
			"custom_2": "input_24_custom_1003_2",
			"custom_3": "input_24_custom_1003_3"
		}
	});
	$$('.form-product-custom_quantity').each(function(el, i) {
		el.observe('blur', function() {
			isNaN(this.value) || this.value < 1 ? this.value = '0' : this.value = parseInt(this.value)
		})
	});
	$$('.form-product-custom_quantity').each(function(el, i) {
		el.observe('focus', function() {
			this.value == 0 ? this.value = '' : this.value
		})
	});
	JotForm.setStripeSettings('', 'none', 'none', '', 'none', '0');
	JotForm.handleProductLightbox();
	JotForm.setPhoneMaskingValidator('input_22_full', '\u0028\u0023\u0023\u0023\u0029 \u0023\u0023\u0023\u002d\u0023\u0023\u0023\u0023');
	JotForm.alterTexts({
		"couponApply": "Apply",
		"couponBlank": "Please enter a coupon.",
		"couponChange": "",
		"couponEnter": "Enter coupon",
		"couponExpired": "Coupon is expired. Please try another one.",
		"couponInvalid": "Coupon is invalid.",
		"couponValid": "Coupon is valid.",
		"shippingShipping": "Shipping",
		"taxTax": "Tax",
		"totalSubtotal": "Subtotal",
		"totalTotal": "Total"
	}, true);
	/*INIT-END*/
});

setTimeout(function() {
	JotForm.paymentExtrasOnTheFly([null, null, {
		"name": "submit",
		"qid": "2",
		"text": "Order",
		"type": "control_button"
	}, null, null, null, null, null, null, null, null, null, null, null, null, null, {
			"name": "fullName16",
			"qid": "16",
			"text": "Full Name",
			"type": "control_fullname"
		}, {
			"name": "address17",
			"qid": "17",
			"text": "Address",
			"type": "control_address"
		}, null, null, null, null, {
			"name": "phoneNumber22",
			"qid": "22",
			"text": "Phone Number",
			"type": "control_phone"
		}, {
			"name": "email23",
			"qid": "23",
			"subLabel": "example@example.com",
			"text": "E-mail",
			"type": "control_email"
		}, {
			"name": "menu",
			"qid": "24",
			"text": "Menu",
			"type": "control_stripe"
		}, null, {
			"name": "onlineFood",
			"qid": "26",
			"text": "Online Food Order Form",
			"type": "control_head"
		}]);
}, 20);