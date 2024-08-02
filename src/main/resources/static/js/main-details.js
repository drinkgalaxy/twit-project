
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
                        alert(errorMessage);
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
                        alert(errorMessage);
                    });
                }
            })
            .catch(error => {
                console.error('저장 중 오류 발생:', error);
                alert('피드백 저장 중 오류 발생.');
            });
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
                    alert(errorMessage);
                });
            }
        })
        .catch(error => {
            console.error('삭제 중 오류 발생:', error);
            alert('서버와의 연결에 문제가 발생했습니다.');
        });
}

// ---------------- 댓글 신고
// 모달 창 열기
function openReportModal() {
    document.getElementById('notice-overlay').style.display = 'block';
    document.getElementById('notice-modal').style.display = 'block';
}

// 모달 창 닫기
function closeReportModal() {
    document.getElementById('notice-overlay').style.display = 'none';
    document.getElementById('notice-modal').style.display = 'none';
}

// 폼 제출 처리
document.getElementById('report-form').addEventListener('submit', function(event) {
    event.preventDefault();

    const reportReason = document.getElementById('notice-text').value;

    const postData = {
        reportReason: reportReason
    };

    const commentId = document.querySelector('.feedback-cube').getAttribute('data-comment-id');

    fetch(`/api/report/comments/${commentId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(postData)
    })
        .then(response => {
            if (response.ok) {
                alert("신고가 완료되었습니다.");
                closeReportModal();
                window.location.reload();
            } else {
                return response.text().then(errorMessage => {
                    alert(errorMessage);
                });
            }
        })
        .catch((error) => {
            console.error('Error:', error);
            alert("신고 과정에서 오류가 발생했습니다.");
        });
});


// --------------- 게시글 수정 / 삭제
document.addEventListener('DOMContentLoaded', function() {
    const modifyButton = document.querySelector('.modify-button');
    const deleteButton = document.querySelector('.delete-button');
    const postId = document.getElementById('postId').value;

    modifyButton.addEventListener('click', function () {
        const descriptionElement = document.getElementById('post-description');
        const contentsElement = document.getElementById('post-contents');

        const currentDescription = descriptionElement.innerText;
        const currentContents = contentsElement.innerText;

        descriptionElement.innerHTML = `<textarea id="new-description">${currentDescription}</textarea>`;
        contentsElement.innerHTML = `<textarea id="new-contents">${currentContents}</textarea>`;

        modifyButton.innerText = '저장';
        modifyButton.removeEventListener('click', arguments.callee);
        modifyButton.addEventListener('click', function () {
            const newDescription = document.getElementById('new-description').value;
            const newContents = document.getElementById('new-contents').value;

            if (newDescription && newContents) {
                const requestBody = {
                    description: newDescription,
                    contents: newContents
                };

                fetch(`/api/posts/${postId}`, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestBody)
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json().then(data => {
                                alert("게시글이 성공적으로 수정되었습니다.");
                                window.location.reload();
                            });
                        } else {
                            return response.text().then(errorMessage => {
                                alert(errorMessage);
                            });
                        }
                    })
                    .catch(error => {
                        console.error('게시글 수정 오류:', error);
                    });
            } else {
                alert("모든 필드를 입력해야 합니다.");
            }
        });

        deleteButton.addEventListener('click', function () {
            if (confirm("정말로 이 게시글을 삭제하시겠습니까?")) {
                fetch(`/api/posts/${postId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(response => {
                        if (response.ok) {
                            alert("게시글이 성공적으로 삭제되었습니다.");
                            window.location.href = '/'; // 삭제 후 메인 페이지로 이동
                        } else {
                            return response.text().then(errorMessage => {
                                showAlert(errorMessage);
                            });
                        }
                    })
                    .catch(error => {
                        console.error('게시글 삭제 오류:', error);
                    });
            }
        });
    });
});
