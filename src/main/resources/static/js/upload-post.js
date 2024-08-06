document.getElementById('uploadForm').addEventListener('submit', function(event) {
    event.preventDefault(); // 폼 제출 기본 동작 방지

    const formData = new FormData(this); // FormData 객체 생성

    // 디버깅을 위해 FormData 내용 로그 출력
    for (let pair of formData.entries()) {
        console.log(pair[0] + ', ' + pair[1]);
    }

    fetch('/api/posts', {
        method: 'POST',
        body: formData // FormData 객체를 요청 본문에 포함
    })
        .then(response => {
            if (response.ok) {
                return response.json(); // JSON 응답을 파싱
            } else {
                return response.text().then(errorMessage => {
                    alert(errorMessage);
                });
            }
        })
        .then(data => {
            console.log('Success:', data);
            alert("게시글 업로드가 완료되었습니다.");
            location.href = '/'; // 성공 후 페이지 이동
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("게시글 업로드가 실패했습니다.");
        });
});
