
	var validator;
	var $sysMessageForm = $("#sysMessage-add-form");
	
	
	var E = window.wangEditor;
    var editor = new E('#editor');
	var $text1 = $('#detail');
    // 或者 var editor = new E( document.getElementById('editor') )
	editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
	editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text1.val(html);
    }
	editor.create();
	E.fullscreen.init('#editor');
	editor.txt.html('');
	$text1.val(editor.txt.html());
	
	

	$(function() {
		
	    validateRule();
	    

	    $("#sysMessage-add-button").click(function() {
	        var name = $(this).attr("name");
	        
	        var validator = $sysMessageForm.validate();
	        var flag = validator.form();
	        if (flag) {
	        	
	        	if (name == "save") {
	                $.post(ctx + "sysMessage/add", $sysMessageForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("sysMessageTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	            if (name == "update") {
	                $.post(ctx + "sysMessage/update", $sysMessageForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("sysMessageTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#sysMessage-edit .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		editor.txt.html('');
		$MB.closeAndRestModal("sysMessage-edit");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $sysMessageForm.validate({
	        rules: {
	            detail: {
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
	            
	            detail: icon + "请填写参数"
	        }
	    });
	}


	

function updatesysMessage() {
    var selected = $("#sysMessageTable").bootstrapTable('getSelections');
    var selected_length = selected.length;
    if (!selected_length) {
        $MB.n_warning('请勾选需要修改的参数！');
        return;
    }
    if (selected_length > 1) {
        $MB.n_warning('一次只能修改一个参数！');
        return;
    }
    var sysMessageId = selected[0].id;
    $.post(ctx + "sysMessage/getSysMessage", { "id": sysMessageId }, function(r) {
        if (r.code == 0) {
            var $form = $('#sysMessage-edit');
            $form.modal();
            var sysMessage = r.msg;
            $("#sysMessage-add-modal-title").html('修改参数');
            $form.find("input[name='id']").val(sysMessage.id);
            $form.find("input[name='title']").val(sysMessage.title);
            $form.find("textarea[name='detail']").val(sysMessage.detail);
            
            editor.txt.html(sysMessage.detail);
            $("#sysMessage-add-button").attr("name", "update");
        } else {
            $MB.n_danger(r.msg);
        }
    });
}