let currentEditFlashcardId = null;
async function submitFlashcard() {
    const subjectInput = document.getElementById('subjectInput').value.trim();
    const frontText = document.getElementById('frontText').value.trim();
    const backText = document.getElementById('backText').value.trim();

    if (!subjectInput || !frontText || !backText) {
        alert('All fields are required.');
        return;
    }

    try {
        let response;
        const requestBody = { subject: subjectInput, frontText, backText };

        if (currentEditFlashcardId) {
            response = await fetch(`${API_URL}/${currentEditFlashcardId}`, {
                method: 'PATCH',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(requestBody)
            });
        } else {
            response = await fetch(API_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify([requestBody])
            });
        }

        if (!response.ok) throw new Error('Failed to save flashcard.');

        $('#addFlashcardModal').modal('hide');

        currentEditFlashcardId = null;

        await loadSubjects();
        await loadFlashcards(subjectInput);
    } catch (error) {
        console.error('Error saving flashcard:', error);
        alert('Error saving flashcard. Please try again.');
    }
}

function showAddFlashcardForm() {
    document.getElementById('subjectInput').value = '';
    document.getElementById('frontText').value = '';
    document.getElementById('backText').value = '';
    $('#addFlashcardModal').modal('show');
}


async function deleteCurrentFlashcard() {
    if (!flashcards[currentIndex]) {
        console.error("No flashcard selected for deletion.");
        return;
    }

    const deleteFlashcardId = flashcards[currentIndex].flashcardId;

    if (!deleteFlashcardId) {
        console.error("Invalid flashcard ID.");
        alert("Error: Flashcard ID is invalid.");
        return;
    }

    $('#deleteConfirmationModal').modal('show');

    document.getElementById('confirmDeleteButton').onclick = async () => {
        try {
            const response = await fetch(`${API_URL}/${deleteFlashcardId}`, {
                method: 'DELETE',
                headers: { 'Content-Type': 'application/json' }
            });

            if (!response.ok) throw new Error(`Failed to delete flashcard with ID: ${deleteFlashcardId}`);

            flashcards.splice(currentIndex, 1);

            if (flashcards.length === 0) {
                document.getElementById('flashcardDeck').style.display = 'none';
                await loadSubjects();
            } else {
                currentIndex = Math.max(0, currentIndex - 1);
                displayFlashcard(flashcards[currentIndex]);
            }

            $('#deleteConfirmationModal').modal('hide');
        } catch (error) {
            console.error('Error deleting flashcard:', error);
            alert('Error deleting flashcard. Please try again.');
        }
    };
}
function editCurrentFlashcard() {
    if (!flashcards[currentIndex]) {
        console.error("No flashcard selected for editing.");
        return;
    }

    const flashcard = flashcards[currentIndex];
    currentEditFlashcardId = flashcard.flashcardId;

    document.getElementById('subjectInput').value = flashcard.subject;
    document.getElementById('frontText').value = flashcard.frontText;
    document.getElementById('backText').value = flashcard.backText;

    document.querySelector('#addFlashcardModal .modal-title').textContent = 'Edit Flashcard';

    $('#addFlashcardModal').modal('show');
}



