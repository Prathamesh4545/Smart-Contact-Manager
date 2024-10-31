console.log("contacts.js");

const viewContactModal = document.getElementById("view_contact_modal");

// Options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses: 'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => console.log('modal is hidden'),
    onShow: () => console.log('modal is shown'),
    onToggle: () => console.log('modal has been toggled'),
};

// Instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactData(id) {
    console.log(id);

    try {
        const response = await fetch(`http://localhost:8081/api/contact/${id}`);
        const data = await response.json();

        console.log(data);

        document.querySelector('#contact_name').innerText = data.name;
        document.querySelector('#contact_img').src = `/upload/directory/${data.picture}` || '/img/user-circles-set.png'; // Default fallback image
        document.querySelector('#contact_email').innerText = data.email;
        document.querySelector('#contact_phone').innerText = data.phoneNumber;
        document.querySelector('#contact_address').innerText = data.address;
        document.querySelector('#contact_description').innerText = data.description;

        const favoriteElement = document.getElementById('contact_favorite');
        if (data.favorite) {
            favoriteElement.classList.remove('hidden');
        } else {
            favoriteElement.classList.add('hidden');
        }

        document.querySelector('#contact_linkedin').href = data.linkedInLink;
        document.querySelector('#contact_website').href = data.webSiteLink;

        openContactModal();
    } catch (error) {
        console.log('Error loading contact data:', error);
    }
}
