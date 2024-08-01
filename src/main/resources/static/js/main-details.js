
// --------------- 하트 클릭

document.addEventListener('DOMContentLoaded', function() {
    const heartImage = document.getElementById('heart-image');
    const postId = document.getElementById('postId').value; // 현재 포스트 ID

    // 하트 클릭 시 스크랩 상태를 토글하고 서버에 업데이트
    heartImage.addEventListener('click', function() {
        // 현재 하트 이미지가 채워진 하트인지 빈 하트인지 확인
        const isFilled = heartImage.src.includes('filled-heart.png');

        // 하트 상태를 토글
        heartImage.src = isFilled ? '/image/empty-heart.png' : '/image/filled-heart.png';

        // 서버에 스크랩 상태를 업데이트
        fetch(`/api/post/${postId}/scrap`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
                // CSRF 토큰을 제외합니다.
            }
        })
            .then(response => {
                if (response.ok) {
                    return response.json().then(data => {
                        alert(data.message); // 서버에서 받은 메시지를 알림창으로 띄움
                        window.location.reload();
                    });
                } else {
                    return response.text().then(errorMessage => {
                        showAlert(errorMessage);
                        window.location.reload();
                    });
                }
            })
            .then(data => {
                // 서버 응답을 처리합니다.
                console.log('서버 응답:', data);
            })
            .catch(error => {
                // 오류 처리
                console.error('스크랩 상태 업데이트 오류:', error);
            });
    });
});

// showAlert 함수 정의
function showAlert(message) {
    alert(message);
}


// ---------------- 피드백 붙이기

document.addEventListener('DOMContentLoaded', function() {
    const feedbackButton = document.querySelector('.feedback-button');
    const feedbackOverlay = document.querySelector('.feedback-overlay');
    const feedbackModal = document.querySelector('.feedback-modal');
    const feedbackForm = document.getElementById('feedback-form');
    const feedbackContent = document.getElementById('feedback-content');
    const maxWordLength = document.getElementById('max-word-length');

    feedbackButton.addEventListener('click', function() {
        feedbackOverlay.style.display = 'block';
        feedbackModal.style.display = 'block';
    });

    feedbackOverlay.addEventListener('click', function() {
        feedbackOverlay.style.display = 'none';
        feedbackModal.style.display = 'none';
    });

    feedbackForm.addEventListener('submit', function(event) {
        event.preventDefault();
        submitFeedback();
    });

    feedbackContent.addEventListener('input', function() {
        updateWordCount();
    });

    function updateWordCount() {
        const maxLength = 250;
        const currentLength = feedbackContent.value.length;
        maxWordLength.textContent = `최대 글자 수 ${currentLength}/${maxLength}`;
    }

    function submitFeedback() {
        const postId = document.getElementById('postId').value;
        const content = feedbackContent.value;

        fetch(`/api/comments/${postId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ comments: content })
        })
            .then(response => {
                if (response.ok) {
                    alert('피드백이 성공적으로 저장되었습니다.');
                    feedbackOverlay.style.display = 'none';
                    feedbackModal.style.display = 'none';
                    window.location.reload();
                    return response.json();
                } else {
                    return response.text().then(errorMessage => {
                        showAlert(errorMessage); // 오류 메시지를 알림창으로 띄움
                    });
                }
            })
            .catch(error => {
                console.error('저장 중 오류 발생:', error);
                alert('피드백 저장 중 오류 발생.');
            });
    }

    function showAlert(message) {
        alert(message);
    }
});




// ---------------- 댓글 삭제

document.addEventListener('DOMContentLoaded', function() {
    const feedbacks = document.querySelectorAll('.feedback-cube');

    feedbacks.forEach(feedback => {
        const deleteButton = feedback.querySelector('.make-delete');
        deleteButton.addEventListener('click', function() {
            const commentId = feedback.getAttribute('data-comment-id');
            if (confirm('정말로 댓글을 삭제하시겠습니까?')) {
                deleteComment(commentId, feedback);
            }
        });
    });
});

function deleteComment(commentId, feedbackElement) {
    fetch(`/api/comments/${commentId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
    })
        .then(response => {
            if (response.ok) {
                feedbackElement.remove(); // 삭제 성공 시 댓글 요소 제거
            } else {
                return response.text().then(errorMessage => {
                    showAlert(errorMessage); // 오류 메시지를 알림창으로 띄움
                });
            }
        })
        .catch(error => {
            console.error('삭제 중 오류 발생:', error);
            showAlert('서버와의 연결에 문제가 발생했습니다.');
        });
}

function showAlert(message) {
    alert(message); // 단순한 알림창
}

// ---------------- 댓글 신고
document.addEventListener('DOMContentLoaded', function() {
    const noticeButtons = document.querySelectorAll('.notice-button');
    const noticeModal = document.querySelector('.notice-modal');
    const noticeOverlay = document.querySelector('.notice-overlay');
    const reportForm = document.getElementById('report-form');
    const inputNotice = reportForm.querySelector('.input-notice');

    let currentCommentId = null; // 현재 신고할 댓글 ID 저장

    noticeButtons.forEach(button => {
        button.addEventListener('click', function() {
            currentCommentId = this.closest('.feedback-cube').getAttribute('data-comment-id');
            noticeModal.style.display = 'block';
            noticeOverlay.style.display = 'block';
        });
    });

    noticeOverlay.addEventListener('click', function() {
        noticeModal.style.display = 'none';
        noticeOverlay.style.display = 'none';
    });

    reportForm.addEventListener('submit', function(event) {
        event.preventDefault();

        const noticeText = inputNotice.value.trim();
        if (!noticeText) {
            alert('신고 사유를 입력해주세요.');
            return;
        }

        fetch(`/api/report/comments/${currentCommentId}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'X-Requested-With': 'XMLHttpRequest'
            },
            body: JSON.stringify({ noticeText })
        })
            .then(response => {
                if (response.ok) {
                    alert('신고가 접수되었습니다.');
                    noticeModal.style.display = 'none';
                    noticeOverlay.style.display = 'none';
                    window.location.reload();
                } else response.text().then(errorMessage => {
                    showAlert(errorMessage); // 오류 메시지를 알림창으로 띄움
                });
            })
            .catch(error => {
                console.error('신고 중 오류 발생:', error);
                alert('서버와의 연결에 문제가 발생했습니다.');
            });
    });
});


