# NotesAppWithSQLlite
NotesAppWithSQLlite is a simple, offline-first note-taking application for Android. It demonstrates the use of modern Android development practices, including Kotlin, Room for local database storage, MVVM architecture, and Coroutines for asynchronous operations.

## Features

-   **Create & Edit Notes:** Easily add new notes or modify existing ones.
-   **View Notes:** All notes are displayed in a clean, scrollable list on the main screen, sorted by the most recently modified.
-   **Delete Notes:** Remove notes with a long-press gesture, which prompts a confirmation dialog to prevent accidental deletion.
-   **Local Persistence:** All notes are saved locally on the device using a SQLite database, ensuring your data is always available, even without an internet connection.
-   **Modern UI:** A simple and intuitive user interface built with Material Components.

## Tech Stack & Architecture

This project is built using the **MVVM (Model-View-ViewModel)** architecture pattern.

-   **Language:** [Kotlin](https://kotlinlang.org/)
-   **Architecture:** MVVM
    -   **View:** Activities (`MainActivity`, `NoteEditorActivity`) and a `RecyclerView` with a custom `NoteAdapter`.
    -   **ViewModel:** `MainActivityViewModel` to expose data streams and handle UI logic.
    -   **Model:** A repository (`NoteRepo`) that abstracts the data source.
-   **Database:** [Room Persistence Library](https://developer.android.com/training/data-storage/room) as an abstraction layer over SQLite.
-   **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://developer.android.com/kotlin/flow) for managing background threads and database operations.
-   **UI:** Android XML with `ConstraintLayout`, `CardView`, and `MaterialButton`.
-   **Dependency Management:** [Gradle](https://gradle.org/) with the versions catalog (`libs.versions.toml`).

## Project Structure

The project follows a standard Android application structure with a focus on feature-based packaging.

```
app/src/main/java/com/coceracia/sqlnotes/
├── model/            # Data layer: Room Entity (Note), DAO, Database, and Repository
│   ├── Note.kt
│   ├── NoteDao.kt
│   ├── NoteDataBase.kt
│   └── NoteRepo.kt
├── view/             # UI layer: Activities and RecyclerView Adapter
│   ├── MainActivity.kt
│   ├── NoteEditorActivity.kt
│   └── adapter/
│       └── NoteAdapter.kt
└── viewmodel/        # ViewModel layer connecting the UI and data layers
    └── MainActivityViewModel.kt
```

## How to Build and Run

To build and run the project, follow these steps:

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/coceracia/NotesAppWithSQLlite.git
    ```

2.  **Open in Android Studio:**
    -   Open Android Studio.
    -   Select "Open" and navigate to the cloned project directory.

3.  **Sync Gradle:**
    -   Allow Android Studio to download and sync the project dependencies via Gradle.

4.  **Run the application:**
    -   Select an emulator or connect a physical Android device.
    -   Click the "Run" button in Android Studio.
