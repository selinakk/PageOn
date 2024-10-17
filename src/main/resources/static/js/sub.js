// const showToastAdded = document.getElementById('showToastAdded')
// const toastLiveExample = document.getElementById('toastAdded')
// const toastMsg = document.getElementById('toastBody')
//  const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample);
//
// if (showToastAdded) {
//     showToastAdded.addEventListener('click', () => {
//         const bodyTxt = showToastAdded.textContent;
//         toastMsg.textContent = `서재 > ${bodyTxt}되었습니다.`;
//         toastBootstrap.show()
//     })
// }

let arrowToggles = document.querySelectorAll('.arrow-toggle');
arrowToggles.forEach(arrowToggle => {
    arrowToggle.addEventListener('click', function() {
        if (this.classList.contains('up')) {
            this.classList.remove('up');
        } else {
            this.classList.add('up');
        }
    });
});
