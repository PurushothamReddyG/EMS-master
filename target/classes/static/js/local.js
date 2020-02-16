
$(document).ready(function() {
    $('#regForm').bootstrapValidator({
    	 framework: 'bootstrap',
    	feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	id: {
                validators: {
                	notEmpty: {
                         message: 'Please enter your Employee Id'
                     },
                     numeric: {
                         message: 'Invalid Employee Id',
                         
                     },
                     callback: {
                         callback: function(value, validator) {
                             if (value != null && value.length > 0 && value == 0) {
                                 return {
                                     valid: false,
                                     message: 'Employee Id should not be zero'
                                 }
                             }
                             
                             return true;
                         }
                     }
                }
            },
            name: {
                validators: {
                    notEmpty: {
                        message: 'Please enter Employee Name'
                    },
                    regexp: {
                        regexp: /^[a-z\s]+$/i,
                        message: 'Employee Name must be String'
                    }
            
                }
            },
            salary: {
                validators: {
                    notEmpty: {
                        message: 'Please enter Salary'
                    },
                   numeric: {
                	   message: 'Invalid Salary',
                
                   }
                }
            }
            
            }
        });
});


