
	var validator;
	var $inviteImgBannerAddForm = $("#inviteImgBanner-add-form");
	

	$(function() {
		validateRule();

	    $("#inviteImgBanner-add .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $inviteImgBannerAddForm.validate();
	        var flag = validator.form();
	        if (flag) {
	        	var imgs='';
            	$("input[name='_imgs']").each(function(j,item){
            		if(imgs=='') imgs=item.value;
            		else imgs=imgs+'$lvmq$'+item.value;
            	})
            	
            	$("#imgs").val(imgs);
            	
	            if (name == "save") {
	                $.post(ctx + "inviteImgBanner/add", $inviteImgBannerAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("inviteImgBannerTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	            if (name == "update") {
	                $.post(ctx + "inviteImgBanner/update", $inviteImgBannerAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("inviteImgBannerTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#inviteImgBanner-add .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		$('.img-div').remove();
		validator.resetForm();
		$MB.closeAndRestModal("inviteImgBanner-add");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $inviteImgBannerAddForm.validate({
	        rules: {
	            title: {
	                required: true,
	                minlength: 3,
	                maxlength: 10,
	               
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
	            title: {
	                required: icon + "请输入标题",
	                minlength: icon + "标题长度3到10个字符"
	            }
	        }
	    });
	}

	
	
	
	$("#imgs_up").change(function(){
		
//		var reader=new FileReader();
//		  reader.onload=function(e){
//		  console.log( reader.result);  //或者 e.target.result都是一样的，都是base64码
//		}  
//		reader.readAsDataURL(this.files[0]);
//		
//		reader.onload = function (e) {
//		       base64Code=this.result;
//		        //把得到的base64赋值到img标签显示
//		       $("#imgs_up").before('<div class="img-div" style="margin-top:10px;">'
//			       		+'<img style="width:250px;height:200px" src="'+base64Code+'" >'
//			       		+'<button  type="button"   class="btn btn-secondary remove-img">移除</button>'
//			       	+'</div>');
//		     }
//		
		var fm = new FormData();
		fm.append('file', this.files[0]);
		
		$.ajax({
			url:'makeMoney/uploadImg',
			type:'POST',
			data:fm,
			contentType:false,
			processData:false,
			success: function(result){
				$('#add-img').attr("src",result);
				$("input[name='imgUrl']").val(result);
			}
		});
		
	})
	
	
	
	

	