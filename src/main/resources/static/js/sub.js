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
