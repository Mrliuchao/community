function post() {
    var qusertionId = $("#question_id").val();
    var content = $("#comment_content").val();

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:"application/json",
        data:JSON.stringify( {
            "parentId":qusertionId,
            "content":content,
            "type":1
        }),
        success: function (response) {
            console.log(response);
            if (response.code  ==200){
                $("#comment_section").hide();
            }else{
                if (response.code == 2003){
                    //未登录
                  var isAccepted  = confirm(response.message);
                  if (isAccepted){
                      window.open("https://github.com/login/oauth/authorize?client_id=9819dd2d0ba8d132088d&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                      window.localStorage.setItem("closable",true);
                  }
                }
                alert(response.message);
            }
        },
        dataType: "json"
    });
}