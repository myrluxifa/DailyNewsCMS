
	var validator;
	var $makeMoneyAddForm = $("#makeMoney-add-form");
	
	var $lineOne = $makeMoneyAddForm.find("input[name='lineOne']");
	var $lineTwo = $makeMoneyAddForm.find("input[name='lineTwo']");
	var $lineThree = $makeMoneyAddForm.find("input[name='lineThree']");
	var $lineFour = $makeMoneyAddForm.find("input[name='lineFour']");
	
	
	var E = window.wangEditor;
    var editor = new E('#editor');
	var $text1 = $('#introduce');
    // 或者 var editor = new E( document.getElementById('editor') )
	//editor.customConfig.uploadImgShowBase64 = true   // 使用 base64 保存图片
	editor.customConfig.customUploadImg = function (files, insert) {
	    // files 是 input 中选中的文件列表
	    // insert 是获取图片 url 后，插入到编辑器的方法

	    // 上传代码返回结果之后，将图片插入到编辑器中
		var fm = new FormData();
		fm.append('file', files[0]);
		$.ajax({
			url:'makeMoney/uploadImg',
			type:'POST',
			data:fm,
			contentType:false,
			processData:false,
			success: function(result){
				insert(result);
			}
		});
	}
	editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $text1.val(html);
    }
	editor.create();
	E.fullscreen.init('#editor');
	$text1.val(editor.txt.html());
    
   

	$(function() {
		
	    
		validateRule();
	    
	    $makeMoneyAddForm.find("input[name='type']").change(function() {
	        var $value = $makeMoneyAddForm.find("input[name='type']:checked").val();
	        if ($value == "0") {
	        	$lineOne.parent().prev().text("任务限时：");
	            $lineTwo.parent().prev().text("注册方式：");
	            $lineThree.parent().prev().text("产品属性：");
	            $lineFour.parent().prev().text("高额返利：");
	        } else {
	        	$lineOne.parent().prev().text("投资期限：");
	            $lineTwo.parent().prev().text("投资金额：");
	            $lineThree.parent().prev().text("年化收益：");
	            $lineFour.parent().prev().text("额外返利：");
	        }
	    });
	    

	    $("#makeMoney-add .btn-save").click(function() {
	        var name = $(this).attr("name");
	        var validator = $makeMoneyAddForm.validate();
	        var flag = validator.form();
	        if (flag) {
	        	var imgs='';
            	$("input[name='_imgs']").each(function(j,item){
            		if(imgs=='') imgs=item.value;
            		else imgs=imgs+'$lvmq$'+item.value;
            	})
            	
            	$("#imgs").val(imgs);
            	
	            if (name == "save") {
	                $.post(ctx + "makeMoney/add", $makeMoneyAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("easyMoneyTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	            if (name == "update") {
	                $.post(ctx + "makeMoney/update", $makeMoneyAddForm.serialize(), function(r) {
	                    if (r.code == 0) {
	                        closeModal();
	                        $MB.n_success(r.msg);
	                        $MB.refreshTable("easyMoneyTable");
	                    } else $MB.n_danger(r.msg);
	                });
	            }
	        }
	    });

	    $("#makeMoney-add .btn-close").click(function() {
	        closeModal();
	    });

	});

	function closeModal() {
		$('.img-div').remove();
		validator.resetForm();
		$MB.closeAndRestModal("makeMoney-add");
	}

	function validateRule() {
	    var icon = "<i class='zmdi zmdi-close-circle zmdi-hc-fw'></i> ";
	    validator = $makeMoneyAddForm.validate({
	        rules: {
	            title: {
	            	//不做限制
	                required: true,
	                minlength: 1,
	                maxlength: 20,
	               
	            },
	            logo:{
	            	required: true
	            },
	            cash:{
	            	required: true,
	            	number: true
	            },
	            lineOne:{
	            	required: true
	            },
	            lineTwo:{
	            	required: true
	            },
	            lineThree:{
	            	required: true
	            },
	            lineFour:{
	            	required: true,
	            	number: true
	            },
	            participantsNum:{
	            	required: true
	            },
	            rewardsType:{
	            	required: true
	            },
	            cycle:{
	            	required: true,
	            	digits:true
	            },
	            
	            time_limit:{
	            	required: true,
	            	digits:true
	            },
	            textare: {
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
	            title: {
	                required: icon + "请输入标题",
	                minlength: icon + "标题长度3到10个字符"
	            },
	            logo:{
	            	required: icon+"此项不能为空"
	            },
	            cash:{
	            	required: icon+"此项不能为空"
	            },
	            lineOne:{
	            	required: icon+"此项不能为空"
	            },
	            lineTwo:{
	            	required: icon+"此项不能为空"
	            },
	            lineThree:{
	            	required: icon+"此项不能为空"
	            },
	            lineFour:{
	            	required: icon+"此项不能为空"
	            },
	            rewardsType:{
	            	required: icon+"此项不能为空"
	            },
	            participantsNum:{
	            	required: icon+"此项不能为空"
	            },
	            cycle:{
	            	required: icon+"此项不能为空",
	            	digits:icon+"请输入整数"
	            },
	            
	            time_limit:{
	            	required: icon+"此项不能为空",
	            	digits:icon+"请输入整数"
	            },
	            textare: icon + "请填写内容详情"
	        }
	    });
	}

	function getObjectURL(file){
		var url = null;
		if(window.createObjectURL!=undefined){
			url = window.createObjectURL(file);
		}else if(window.URL!=undefined){
			url = window.URL.createObjectURL(file);
		}else if(window.webkitURL!=undefined){
			url = window.webkitURL.createObjectURL(file);
		}
		return url;
	}
	
	$("#img_up").change(function(){
		
		var reader=new FileReader();
		  reader.onload=function(e){
		  console.log( reader.result);  //或者 e.target.result都是一样的，都是base64码
		}  
		reader.readAsDataURL(this.files[0]);
		
		reader.onload = function (e) {
		       base64Code=this.result;
		        //把得到的base64赋值到img标签显示
		       $("#img_show").attr("src",base64Code);
		       $('#logo').val(base64Code);
		       $('#imgOld').val('');
		     }
		
		
//		var objUrl = getObjectURL(this.files[0]);
//		if(objUrl){
//			//$(this).next("img").attr("src", objUrl);
//			$("#img1").attr("src",objUrl);
//			$("#img").val(objUrl);
//		}
	})
	
	
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
				$("#imgs_up").before('<div class="img-div" style="margin-top:10px;">'
			       		+'<img style="width:250px;height:200px" src="'+result+'" >'
			       		+'<input type="hidden" name="_imgs" value="'+result+'">'
			       		+'<button  type="button"   class="btn btn-secondary remove-img">移除</button>'
			       	+'</div>');
			}
		});
		
	})
	
	$('.imgs-div').on("click","button",function(){
		$(this).parent().remove();
	})
	
	function removeImg(){
		$(this).prev().remove();
	}
	
	

	