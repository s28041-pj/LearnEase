const API_URL = 'http://localhost:8080/flashcards';
let flashcards = [];
let currentIndex = 0;

window.onload = () => {
    loadSubjects();
};

async function loadSubjects() {
    try {
        const response = await fetch(API_URL);
        if (!response.ok) throw new Error('Failed to load flashcards');

        const data = await response.json();

        const grouped = data.reduce((acc, card) => {
            acc[card.subject] = acc[card.subject] || [];
            acc[card.subject].push(card);
            return acc;
        }, {});

        const subjectsContainer = document.getElementById('subjectsContainer');
        subjectsContainer.innerHTML = '';

        if (Object.keys(grouped).length === 0) {
            subjectsContainer.innerHTML = `
                <div class="text-center mt-5">
                    <p class="text-muted">No flashcards available. Add your first flashcard!</p>
                </div>
            `;
            return;
        }

        for (const subject in grouped) {
            const subjectGroup = document.createElement('div');
            subjectGroup.className = 'mb-4';
            subjectGroup.innerHTML = `
                <div class="subject-header" onclick="loadFlashcards('${subject}')">
                    <i class="fas fa-book fa-2x text-primary"></i>
                    <h5>${subject}</h5>
                </div>
            `;
            subjectsContainer.appendChild(subjectGroup);
        }

        subjectsContainer.style.display = 'block';
    } catch (error) {
        console.error(error);
        alert('Error loading subjects.');
    }
}

async function loadFlashcards(subject) {
    try {
        const response = await fetch(`${API_URL}/${subject}`);
        if (!response.ok) throw new Error('Failed to load flashcards');

        flashcards = await response.json();

        if (flashcards.length === 0) {
            alert(`No flashcards found for the subject: ${subject}`);
            return;
        }

        currentIndex = 0;
        displayFlashcard(flashcards[currentIndex]);

        document.getElementById('flashcardDeck').style.display = 'block';
    } catch (error) {
        console.error(error);
        alert('Error loading flashcards.');
    }
}

function displayFlashcard(flashcard) {
    const flashcardElement = document.getElementById('currentFlashcard');
    flashcardElement.classList.remove('is-flipped');
    flashcardElement.querySelector('.flashcard-front').textContent = flashcard.frontText;
    flashcardElement.querySelector('.flashcard-back').textContent = flashcard.backText;

    document.getElementById('cardCounter').textContent = `${currentIndex + 1} / ${flashcards.length}`;
    document.getElementById('prevButton').disabled = currentIndex === 0;
    document.getElementById('nextButton').disabled = currentIndex === flashcards.length - 1;
}

function showPreviousCard() {
    if (currentIndex > 0) {
        currentIndex--;
        displayFlashcard(flashcards[currentIndex]);
    }
}

function showNextCard() {
    if (currentIndex < flashcards.length - 1) {
        currentIndex++;
        displayFlashcard(flashcards[currentIndex]);
    }
}
function flipFlashcard() {
    const currentFlashcard = document.getElementById('currentFlashcard');
    currentFlashcard.classList.toggle('is-flipped');
}

