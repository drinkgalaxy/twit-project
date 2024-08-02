document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const title = document.querySelector('input[name="title"]').value;
    const description = document.querySelector('input[name="description"]').value;
    const contents = document.querySelector('textarea[name="contents"]').value;

    const postData = {
        title: title,
        description: description,
        contents: contents
    };

    fetch('/api/posts', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(postData)
    })
        .then(response => {
            if (response.ok) {
                console.log('Success:', response.json());
                alert("게시글 업로드가 완료되었습니다.");
                location.href = '/';
            } else {
                return response.text().then(errorMessage => {
                    alert(errorMessage);
                });
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("게시글 업로드가 실패했습니다.")
        });
});