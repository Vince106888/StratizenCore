📱 Stratizen Core — Student Event Management App
Stratizen Core is an intelligent, offline-first event management app tailored for university students. It empowers students to organize, share, and coordinate academic or extracurricular events, complete with a gamified XP system, modern UI, and dynamic group-based filtering — all built on a robust MVVM Android architecture using Kotlin and Jetpack Compose.

🚀 Features
🧠 Offline-first Architecture – Powered by Room Database

🧪 MVVM Pattern – Clean architecture using ViewModel & LiveData

🎮 Gamification – XP tracking and level progression

🔄 Group Filtering – Filter events by General, Clubs, Transport, Class

📅 Date-based Event Grouping – Events sorted by date automatically

✏️ Event Management – Add, edit, and delete events with undo support

🎨 Modern UI – Material 3 with Jetpack Compose for fluid interaction

🧱 Scaffold Layout – Clean, responsive layouts with Material design components

🏗️ Tech Stack
Tool/Library	Purpose
Kotlin	Core programming language
Jetpack Compose	Declarative, modern UI toolkit
Room DB	Local data persistence
ViewModel	Lifecycle-aware state management
LiveData	Observable data holders
Material 3	Modern design system
Navigation	Compose navigation architecture

📁 Project Structure
bash
Copy
Edit
com.stratizen.core/
├── data/
│   ├── db/            # Room database, DAO interfaces
│   └── model/         # Data models (Event, XP, etc.)
├── repository/        # Repository layer (EventRepository, etc.)
├── ui/
│   ├── components/    # Reusable composables (EventCard, DropdownMenuBox, etc.)
│   └── screens/       # Screens (HomeScreen, AddEventScreen, etc.)
├── viewmodel/         # ViewModels (EventViewModel, XpViewModel)
🛠️ Getting Started
✅ Prerequisites
Android Studio Giraffe or later

Kotlin 1.9+

Gradle 8.0+

Emulator or physical device (API 31+ recommended)

▶️ Running the App
Clone the repository:

bash
Copy
Edit
git clone https://github.com/your-username/stratizen-core.git
Open the project in Android Studio

Run the app on an emulator or connected device

🧠 Upcoming Features (Planned)
📆 Calendar integration

🧭 Deep links & smart notifications

🔁 Cloud sync (Firestore or Supabase)

🧑‍🤝‍🧑 Peer collaboration & messaging

🛍️ In-app student marketplace

🔒 Encrypted data sharing & backups

📄 License
This project is licensed under the MIT License — free to use for educational and personal projects.

🙌 Acknowledgements
Developed with ❤️ by Vincent Nyamao
Part of the Stratizen initiative — empowering decentralized student collaboration.