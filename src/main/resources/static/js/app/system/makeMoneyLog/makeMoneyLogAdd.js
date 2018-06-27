
	var validator;
	var $makeMoneyLogAddForm = $("#makeMoneyLog-add-form");
	
	
	
    
   

	$(function() {
		
	    
		$makeMoneyLogAddForm.find("input[name='status']").change(function() {
	        var $value = $makeMoneyLogAddForm.find("input[name='status']:checked").val();
	    });
	    

	    $("#makeMoneyLog-add .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $makeMoneyLogAddForm.validate();
	        var flag = validator.form();
	        if (flag) {
            	
	            if (name == "update") {
	                $.post(ctx + "makeMoneyLog/update", $makeMoneyLogAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("easyMoneyTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#makeMoneyLog-add .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		$('.img-div').remove();
		$MB.closeAndRestModal("makeMoneyLog-add");
	}


	
	
	

	