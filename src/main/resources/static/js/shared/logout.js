 async function logout() {
    try {
    const response = await fetch('/logout', {
    method: 'POST',
    headers: {
    'Content-Type': 'application/json',
    'X-CSRF-TOKEN': getCsrfToken()
},
    credentials: 'include'
});

    if (response.ok) {
    localStorage.removeItem('authToken');
    sessionStorage.clear();

    window.location.href = 'login.html';
} else {
    alert('Logout failed. Please try again.');
}
} catch (error) {
    console.error('Error during logout:', error);
    alert('An error occurred. Please try again.');
}
}

    function getCsrfToken() {
    const meta = document.querySelector('meta[name="csrf-token"]');
    return meta ? meta.getAttribute('content') : '';
}
