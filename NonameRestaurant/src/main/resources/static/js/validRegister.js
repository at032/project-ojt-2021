$(document).ready(function() {
	$.validator.addMethod("regex", function(value, element, regularExpression) {
		var re = new RegExp(regularExpression)
		return this.optional(element) || re.test(value);
	});
	$("#regisForm").validate({
		// preventDefault();
		rules: {
			cusName:  {
				required: true,
			},
			'loginId.encrytedPassword': {
				required: true,
			},
			repassword: {
				required: true,
				equalTo: '#Password1',
			},
			birthday: {
				required: true,
			},
			'loginId.email': {
				required: true,
				email: true
			},
			'loginId.phone': {
				required: true,
				regex: /([0]+([0-9]{9})\b)/g,
			},
			gender: {
				required: true
			}

		},
		messages: {
			cusName: {
				required: "Can't be empty",
			},
			'loginId.encrytedPassword': {
				required: "Can't be empty",
			},
			repassword: {
				required: "Can't be empty",
				equalTo: "Password does not match",
			},
			birthday: {
				required: "Can't be empty",
			},
			'loginId.email': {
				required: "Can't be empty",
				email: "Invalid email"
			},
			'loginId.phone': {
				required: "Can't be empty",
				regex: "Invalid phone number"
			},
			gender: {
				required: "Please choose your gender"
			}

		},
		errorPlacement: function(error, element) {
			if (element.is(":radio")) {
				error.appendTo(element.parents('.form-group'));

			} else {
				error.insertAfter(element.parent("div"));
			}
		}
	});
});