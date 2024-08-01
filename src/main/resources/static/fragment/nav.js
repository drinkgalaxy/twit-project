// -------------- 헤더
document.addEventListener('DOMContentLoaded', function() {
    // 포스트잇 클릭 이벤트 설정
    const feedbackIcon = document.querySelector('.index-feedback');
    const freeIcon = document.querySelector('.index-free');
    const worryIcon = document.querySelector('.index-worry');
    const uploadIcon = document.querySelector('.index-upload');

    if (feedbackIcon) {
        feedbackIcon.addEventListener('click', function() {
            location.href = '/'; // 메인 화면으로 이동
        });
    }

    if (freeIcon) {
        freeIcon.addEventListener('click', function() {
            alert('자유 게시판은 개발 중입니다. 조금만 기다려주세요! ^_^');
        });
    }

    if (worryIcon) {
        worryIcon.addEventListener('click', function() {
            alert('고민 게시판은 개발 중입니다. 조금만 기다려주세요! ^_^');
        });
    }

    if (uploadIcon) {
        uploadIcon.addEventListener('click', function() {
            alert('글 업로드시엔 로그인 된 상태여야 업로드가 가능합니다.');

            // 두 번째 confirm 창
            const userConfirmed = confirm('로그인 화면으로 이동하시겠습니까?');
            if (userConfirmed) {
                // 확인을 누르면 로그인 화면으로 이동
                location.href = '/login';
            }
        });
    }

    // 게시글 상세 페이지 이동 설정
    const postBoxes = document.querySelectorAll('.post-box');

    postBoxes.forEach(postBox => {
        postBox.addEventListener('click', function() {
            const postId = this.getAttribute('data-post-id');
            if (postId) {
                window.location.href = `/posts/${postId}`;
            }
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

