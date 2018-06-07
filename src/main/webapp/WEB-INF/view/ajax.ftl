<!DOCTYPE HTML>
<html>
<head>
<script type="text/javascript" src="/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
    $(function(){
        //按钮单击时执行
        $("#testAjax").click(function(){
              
              //Ajax调用处理
            $.ajax({
               type: "POST",
               url: "/hello/ajax",
               data: "name=小昌哥",
               success: function(data){
                        $("#myDiv").html('<h2>'+data+'</h2>');
                  }
            });
            
         });
    });
</script>    
</head>
    <body>
        <div id="myDiv"><h2>通过 AJAX 改变文本</h2></div>
        <button id="testAjax" type="button">Ajax改变内容</button>
        
        
        <a href="/hello/say?name=小昌哥"><button>jsp页面模板demo</button></a>
        <a  href="/hello/say2?name=小昌哥"><button>freemarker页面模板demo</button></a>
    </body>
</html>