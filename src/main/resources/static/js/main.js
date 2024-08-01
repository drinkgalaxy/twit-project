// ----------- 검색 버튼 알림창
document.addEventListener('DOMContentLoaded', function() {
    const searchIcon = document.querySelector('.search-icon');
    searchIcon.addEventListener('click', function() {
        alert('검색 기능은 개발 중입니다. 조금만 기다려주세요! ^_^');
    });
});

// ----------- 관리자 공지 수정
function editNotice() {
    const noticeText = document.getElementById('notice-text');
    const currentText = noticeText.innerText.replace('공지 : ', '');
    noticeText.innerHTML = `
        <textarea id="notice-input" style="width: 500px; height: 100px; background: #F7EFC4;">${currentText}</textarea>
        <button id="save-button">저장</button>
        <div id="char-count" style="font-size: 12px; color: gray;">글자 수: ${currentText.length} / 200</div>
        <div id="error-message" style="color: red;"></div>
    `;

    const textarea = document.getElementById('notice-input');
    const charCount = document.getElementById('char-count');

    textarea.addEventListener('input', function () {
        const length = textarea.value.length;
        charCount.innerText = `글자 수: ${length} / 200`;
    });

    document.getElementById('save-button').addEventListener('click', saveNotice);
}

function saveNotice() {
    const noticeId = document.querySelector('.notice-box').getAttribute('data-notice-id');
    const newText = document.getElementById('notice-input').value;
    const errorMessage = document.getElementById('error-message');

    fetch(`/api/notice/${noticeId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ noticeContents: newText })
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.text().then(text => {
                    try {
                        const data = JSON.parse(text); // 텍스트를 JSON으로 파싱
                        throw new Error(data.error || 'Unknown error');
                    } catch (e) {
                        throw new Error(text); // JSON 파싱 오류 시 원본 텍스트를 에러로 처리
                    }
                });
            }
        })
        .then(data => {
            document.getElementById('notice-text').innerText = '공지 : ' + data.noticeContents;
            errorMessage.innerText = ''; // 성공 시 오류 메시지 제거
        })
        .catch(error => {
            errorMessage.innerText = error.message; // 에러 메시지를 화면에 표시
        });
}

