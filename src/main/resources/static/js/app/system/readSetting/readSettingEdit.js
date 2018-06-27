
	var validator;
	var $readSettingForm = $("#readSetting-add-form");
	
	
	

	$(function() {
		
		
	    validateRule();
	    

	    $("#readSetting-edit .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $readSettingForm.validate();
	        var flag = validator.form();
	        if (flag) {
	            if (name == "update") {
	                $.post(ctx + "readSetting/update", $readSettingForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("readSettingTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#readSetting-edit .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		validator.resetForm();
		$MB.closeAndRestModal("readSetting-edit");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $readSettingForm.validate({
	        rules: {
	        	
	        	dailyCnt: {
	                required: true,
	                number: true
	            },
	            gold: {
	                required: true,
	                number: true
	            },
	            horCnt: {
	                required: true,
	                number: true
	            },
	            horMoney: {
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
	        	dailyCnt:{
	            	required: icon+"此项不能为空"
	            },
	            gold:{
	            	required: icon+"此项不能为空"
	            },
	            horCnt:{
	            	required: icon+"此项不能为空"
	            },
	            horMoney:{
	            	required: icon+"此项不能为空"
	            }
	            
	        }
	    });
	}


	

function updatereadSetting() {
    var selected = $("#readSettingTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的参数！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个参数！');
        return;
    }
    var readSettingId = selected[0].id;
    $.post(ctx + "readSetting/getReadSetting", { "id": readSettingId }, function(r) {
        if (r.code == 0) {
            var $form = $('#readSetting-edit');
            $form.modal();
            var readSetting = r.msg;
            $("#readSetting-add-modal-title").html('修改参数');
            $form.find("input[name='id']").val(readSetting.id);
            $form.find("input[name='explain']").val(readSetting.explain);
            $form.find("input[name='dailyCnt']").val(readSetting.dailyCnt);
            $form.find("input[name='gold']").val(readSetting.gold);
            $form.find("input[name='horCnt']").val(readSetting.horCnt);
            $form.find("input[name='horMoney']").val(readSetting.horMoney);
            $("#readSetting-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}