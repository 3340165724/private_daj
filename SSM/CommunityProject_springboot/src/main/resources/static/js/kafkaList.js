$("#kafka").click(function () {
    console.log($("#kafka").val())
    axios.post(
        '/o/likeList',
        {"menu": $("#kafka").val()},
        {
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(response => {
        let list = response.data;
        console.log(list)
        // document.close('#tbody')
        let tableBody = document.querySelector('#tbody');
        tableBody.innerHTML="";
        list.forEach(item => {
            let row = tableBody.insertRow();
            // row.insertCell().innerText = item.id;
            row.insertCell().innerText = item.issue;
            row.insertCell().innerText = item.interpretation;
            row.insertCell().innerText = item.knowledge;
            row.insertCell().innerText = item.comments;
        });
    })
        .catch(error => {
            console.log(error);
        });
});