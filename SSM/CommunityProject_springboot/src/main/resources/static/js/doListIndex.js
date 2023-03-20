$(document).ready(function () {
    // var link = document.getElementById("dsj");
    // console.log(link)
    axios.get('/o/doListIndex',
        {
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            let list = response.data;
            console.log(list)
            let tableBody = document.querySelector('#tbody');
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