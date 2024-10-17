function confirmReport() {
    // 사용자에게 신고 확인 메시지를 표시하고, "확인"을 누르면 true를 반환
    if (confirm("신고하시겠습니까?")) {
        // 폼 요소를 가져옴
        const form = document.getElementById("reportForm");
        // 폼 데이터를 FormData 객체로 생성
        const formData = new FormData(form);

        // fetch API를 사용하여 비동기 POST 요청을 보냄
        fetch(form.action, {
            method: 'POST', // HTTP 메서드를 POST로 설정
            body: formData  // 요청 본문에 폼 데이터를 포함
        })
            // 서버 응답을 텍스트로 변환
            .then(response => response.text())
            // 변환된 텍스트를 alert로 표시
            .then(data => {
                alert(data);
            })
            // 요청 중 에러가 발생하면 콘솔에 에러 메시지를 출력
            .catch(error => console.error('Error:', error));
    }
}