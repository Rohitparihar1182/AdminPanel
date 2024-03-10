const blob = document.querySelector("#blob");

window.addEventListener("mousemove", (e) => {
    console.log(e.clientX, e.clientY)
    const w = 80;
    console.log(w);
	blob.animate({
		left: e.clientX - w + "px" ,
		top: e.clientY - w + "px",
	}, {
        duration: 1000, fill: 'forwards', easing: 'linear'
    });
});