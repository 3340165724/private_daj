$("#button").click(function (event) {
    axios.post(
        '/doLogin',
        {"username": $("#username").val(), "password": $("#password").val()},
        {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(function (response) {
        if (response.data.result) {
            window.location.href = "/o/index";
        } else {
            $("#prompt").innerHTML("请正确输入用户名或密码");
        }
    })
})