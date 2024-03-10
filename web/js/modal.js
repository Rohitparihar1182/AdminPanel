const deleteUserBtns = document.querySelectorAll(".delete-user");
const closeModal = document.querySelector("#close-modal");
const modalContainer = document.querySelector('.modal-container');
const modal = document.querySelector(".modal");
const deleteAccForm = document.querySelector("#delete-account-form");
const deleteAccBtn = document.querySelector("#confirm-delete");

const ANIMATION_DURATION = 400;

deleteUserBtns.forEach(btn => {
    btn.addEventListener('click' , (e) => {
        handleOpenModal();
        console.log(btn.getAttribute("data-uid"));
        deleteAccForm.setAttribute("data-uid", btn.getAttribute("data-uid"));
        deleteAccBtn.addEventListener('click', () => {
            handleDeleteAcc();
        });
    });
});

closeModal.addEventListener('click', () => {
    handleCloseModal();
});

window.addEventListener('keyup', (e) => {
    if (e.key === 'Escape') {
        if(!modalContainer.classList.contains('hidden')) handleCloseModal();
    }
});

function handleOpenModal(){
    modalContainer.classList.remove('hidden');
    const shadowArea = modalContainer.querySelector('.shadow');
    shadowArea.animate([{opacity: "0"}, {opacity: 1}], {duration: ANIMATION_DURATION, easing: "ease-in-out" })
    shadowArea.addEventListener('click', () => {
        handleCloseModal();
    });
}

function handleCloseModal(){
    modalContainer.classList.remove('hidden');
    modal.animate({transform: "translateY(70vh)"},{duration: ANIMATION_DURATION+150, easing: "linear" });
    const shadowArea = modalContainer.querySelector('.shadow');
    shadowArea.animate({opacity: "0"}, {duration: ANIMATION_DURATION, easing: "ease-in-out" })
    setTimeout(() => {
        modalContainer.classList.add('hidden');
    }, ANIMATION_DURATION);
}

function handleDeleteAcc(){
    const uid = deleteAccForm.getAttribute("data-uid");
    var hiddenInput = document.createElement("input");
    hiddenInput.type = "hidden";
    hiddenInput.name = "uid";
    hiddenInput.value = uid;
    deleteAccForm.appendChild(hiddenInput);
    deleteAccForm.submit();
}
