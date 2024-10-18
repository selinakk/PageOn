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

//아래로 필터링 관련입니다.
let selectedCategories = [];

function filterByCategory(element) {
    const category = element.getAttribute('data-category');
    const badges = document.querySelectorAll('.hashtags .badge');

    // 클릭된 배지의 색상을 토글
    if (element.classList.contains('bg-primary')) {
        element.classList.remove('bg-primary');
        element.classList.add('bg-secondary');
        selectedCategories = selectedCategories.filter(cat => cat !== category);
    } else {
        element.classList.remove('bg-secondary');
        element.classList.add('bg-primary');
        selectedCategories.push(category);
    }

    // AJAX 요청을 통해 필터링된 데이터를 가져옴
    fetch(`/wt_filter?categories=${selectedCategories.join(',')}`)
        .then(response => response.json())
        .then(data => {
            const contentGrid = document.querySelector('.content-grid');
            contentGrid.innerHTML = '';

            data.forEach(webtoon => {
                const webtoonItem = `
                        <div class="col-md-3 col-sm-6 mb-4 content-item">
                            <div class="card" onclick="location.href='/wt_selectOne?num=${webtoon.num}'" style="cursor:pointer;">
                                <img src="/img/${webtoon.img_name}" class="img-thumbnail">
                                <div class="card-body">
                                    <div class="rating">★ ${webtoon.rank}</div>
                                    <div class="card-title">${webtoon.title}</div>
                                </div>
                            </div>
                        </div>
                    `;
                contentGrid.innerHTML += webtoonItem;
            });
        });
//필터링 끝


}
