document.getElementById('login-form').addEventListener('submit', async function(event) {
    event.preventDefault(); // 폼의 기본 제출 동작 방지

    const loginId = document.getElementById('loginId').value;
    const password = document.getElementById('password').value;

    // 입력 필드 검증
    if (!loginId || !password) {
        alert('아이디와 비밀번호를 입력해주세요.');
        return;
    }

    // 로그인 요청
    const response = await fetch('/api/users/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            loginId,
            password
        })
    });

    const result = await response.text();

    if (response.ok) {
        alert('로그인 성공! 메인 페이지로 이동합니다.');
        window.location.href = '/'; // 로그인 성공 시 리다이렉트할 페이지
    } else {
        alert('로그인 실패: ' + result);
    }
});

