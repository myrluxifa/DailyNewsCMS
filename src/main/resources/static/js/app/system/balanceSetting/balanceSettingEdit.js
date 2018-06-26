
	var validator;
	var $balanceSettingForm = $("#balanceSetting-add-form");
	
	
	

	$(function() {
		
		
	    validateRule();
	    

	    $("#balanceSetting-edit .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $balanceSettingForm.validate();
	        var flag = validator.form();
	        if (flag) {
	            
	            if (name == "update") {
	                $.post(ctx + "balanceSetting/update", $balanceSettingForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("balanceSettingTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#balanceSetting-add .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		validator.resetForm();
		$MB.closeAndRestModal("balanceSetting-edit");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $balanceSettingForm.validate({
	        rules: {
	            gold: {
	                required: true
	            }
	        },
	        errorPlacement: function(error, element) {
	            if (element.is(":checkbox") || element.is(":radio")) {
	                error.appendTo(element.parent().parent());
	            } else {
	                error.insertAfter(element);
	            }
	        },
	        messages: {
	            
	            gold: icon + "请填写参数"
	        }
	    });
	}


	

function updatebalanceSetting() {
    var selected = $("#balanceSettingTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的参数！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个参数！');
        return;
    }
    var balanceSettingId = selected[0].id;
    $.post(ctx + "balanceSetting/getbalanceSetting", { "id": balanceSettingId }, function(r) {
        if (r.code == 0) {
            var $form = $('#balanceSetting-edit');
            $form.modal();
            var balanceSetting = r.msg;
            $("#balanceSetting-add-modal-title").html('修改参数');
            $form.find("input[name='id']").val(balanceSetting.id);
            $form.find("input[name='explain']").val(balanceSetting.explain);
            $form.find("input[name='money']").val(balanceSetting.money);
            $("#balanceSetting-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}