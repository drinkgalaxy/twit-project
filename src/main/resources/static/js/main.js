// ----------- 검색 버튼 알림창
document.addEventListener('DOMContentLoaded', function() {
    const searchIcon = document.querySelector('.search-icon');
    searchIcon.addEventListener('click', function() {
        alert('검색 기능은 개발 중입니다. 조금만 기다려주세요! ^_^');
    });
});

// ----------- 옆에 있는 포스트잇들 눌렀을 때 알림창

document.addEventListener('DOMContentLoaded', function() {
    const feedbackIcon = document.querySelector('.index-feedback');
    feedbackIcon.addEventListener('click', function() {
        location.href = '/'; // 메인 화면으로 이동
    });

    const freeIcon = document.querySelector('.index-free');
    freeIcon.addEventListener('click', function() {
        alert('자유 게시판은 개발 중입니다. 조금만 기다려주세요! ^_^');
    });

    const worryIcon = document.querySelector('.index-worry');
    worryIcon.addEventListener('click', function() {
        alert('고민 게시판은 개발 중입니다. 조금만 기다려주세요! ^_^');
    });

    const uploadIcon = document.querySelector('.index-upload');
    uploadIcon.addEventListener('click', function () {
        alert('[주의] 글 업로드시엔 로그인 된 상태여야 업로드가 가능합니다.');
    });

});

// ----------- 게시글 상세 페이지 이동

document.addEventListener('DOMContentLoaded', function() {
    const postBoxes = document.querySelectorAll('.post-box');

    postBoxes.forEach(postBox => {
        postBox.addEventListener('click', function() {
            // data-post-id 속성에서 게시글 ID를 가져옵니다
            const postId = this.getAttribute('data-post-id');
            // 상세 페이지로 이동합니다
            window.location.href = `/posts/${postId}`;
        });
    });
});

// ----------- 로그아웃

document.getElementById('logout-button').addEventListener('click', async function() {
    try {
        const response = await fetch('/api/users/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            // 로그아웃 성공 시 메인 페이지로 리다이렉트
            alert('로그아웃이 완료되었습니다.');
            window.location.href = '/';
        } else {
            // 로그아웃 실패 시 에러 메시지 표시
            const errorText = await response.text();
            alert('로그아웃 실패: ' + errorText);
        }
    } catch (error) {
        alert('로그아웃 요청 중 오류가 발생했습니다.');
        console.error('Logout error:', error);
    }
});

// ----------- 관리자 글 수정
document.addEventListener('DOMContentLoaded', function() {
    const modifyButton = document.querySelector('.modify');
    const editModal = document.getElementById('edit-modal');
    const closeModalButton = document.getElementById('close-edit-modal');
    const editForm = document.getElementById('edit-form');
    const noticeIdInput = document.getElementById('notice-id');
    const noticeContentTextarea = document.getElementById('notice-content');

    // 수정 버튼 클릭 시 모달 열기
    modifyButton.addEventListener('click', function() {
        // 현재 공지 ID를 설정하거나, 필요에 따라 서버에서 가져옵니다.
        const noticeId = 1; // 예시, 실제 ID를 동적으로 설정해야 합니다.
        noticeIdInput.value = noticeId;

        // 현재 공지 내용 불러오기
        noticeContentTextarea.value = '현재 공지 내용'; // 실제 데이터로 업데이트 필요

        editModal.style.display = 'block';
    });

    // 모달 닫기 버튼 클릭 시 모달 닫기
    closeModalButton.addEventListener('click', function() {
        editModal.style.display = 'none';
    });

    // 수정 폼 제출 처리
    editForm.addEventListener('submit', async function(event) {
        event.preventDefault();

        const noticeId = noticeIdInput.value;
        const noticeContent = noticeContentTextarea.value;

        try {
            const response = await fetch(`/notice/${noticeId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    noticeContents: noticeContent
                })
            });

            if (response.ok) {
                alert('공지 내용이 수정되었습니다.');
                editModal.style.display = 'none';
                // 필요 시 페이지 새로 고침 또는 공지 내용 업데이트
            } else {
                alert('공지 수정에 실패했습니다.');
            }
        } catch (error) {
            console.error('수정 중 오류 발생:', error);
            alert('오류가 발생했습니다. 다시 시도해 주세요.');
        }
    });
});

