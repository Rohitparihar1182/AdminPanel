const html = document.querySelector("html");
const btn = document.querySelector("#toggle-theme");
btn.addEventListener("click", () => {
	html.classList.toggle("dark");
	if (html.classList.contains("dark")) {
		localStorage.setItem("theme", JSON.stringify("dark"));
	} else {
		localStorage.setItem("theme", JSON.stringify("light"));
	}
});
const header = document.querySelector("header");

window.addEventListener("scroll", (e) => {
	if (window.scrollY > 40) {
		if (!header.classList.contains("with-shadow"))
			header.classList.add("with-shadow");
	} else if (header.classList.contains("with-shadow"))
		header.classList.remove("with-shadow");
});

window.addEventListener("DOMContentLoaded", () => {
	const theme = localStorage.getItem("theme");
	if (theme && JSON.parse(theme) === "dark") {
		document.querySelector("html").classList.add("dark");
	}
});

// handle navbar

const navTrigger = document.querySelector('.nav-trigger');
const primaryNavigation = document.querySelector('.primary-navigations');
navTrigger.addEventListener('click', () => {
	primaryNavigation.classList.toggle('opened');
})