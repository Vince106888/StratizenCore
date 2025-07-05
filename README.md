# 📱 Stratizen Core — Student Event Management App

**Stratizen Core** is a smart, offline-first event management app designed for university students. It enables students to manage, share, and collaborate on academic and extracurricular events with gamified XP tracking and dynamic group filters — all built on modern Android architecture.

---

## 🚀 Features

- 🧠 **Offline-first Architecture** using Room DB
- 🧪 **MVVM Pattern** with LiveData and ViewModel
- 🎮 **Gamification**: XP and Level system
- 🔄 **Group Filtering**: General, Clubs, Transport, Class
- 📅 **Dynamic Event Grouping by Date**
- 💬 **Edit & Delete Events** with Undo Snackbar
- 🎨 **Modern UI** with Material3 + Jetpack Compose
- ✍️ **Event Creation & Editing Screens**
- ✅ **Scaffold-based Layout**, responsive and clean

---

## 🏗️ Built With

| Stack             | Description                                |
|------------------|--------------------------------------------|
| **Kotlin**        | Primary language                           |
| **Jetpack Compose** | Declarative UI framework                 |
| **Room DB**       | Local database persistence                 |
| **ViewModel + LiveData** | Reactive architecture               |
| **Material3**     | Modern design system                       |
| **Navigation**    | Compose Navigation                         |

---

## 🧩 Modules & Structure

com.stratizen.core/
├── data/
│ └── db/ # Room Database & DAO
│ └── model/ # Data models (Event, XP, etc.)
├── repository/ # EventRepository layer
├── ui/
│ ├── components/ # Reusable UI (EventCard, DropdownMenuBox, etc.)
│ └── screens/ # Composables for screens (HomeScreen, AddEventScreen)
├── viewmodel/ # EventViewModel & XpViewModel

yaml
Copy
Edit

---

## 🛠️ Getting Started

### Prerequisites
- Android Studio Giraffe or later
- Kotlin 1.9+
- Gradle 8.0+
- Emulator or real device (API 31+ recommended)

### Run the App
1. Clone the repo:
```bash
git clone https://github.com/your-username/stratizen-core.git
Open in Android Studio

Click Run ▶️ on an emulator or connected device

🧠 Future Features (Planned)
📆 Calendar Integration

🧭 Deep links and notifications

🔁 Cloud sync (Firestore or Supabase)

🧑‍🤝‍🧑 Peer collaboration & chat

🛍️ In-app student marketplace

🔒 Encrypted backups & sharing

📄 License
MIT License — free for educational and personal use.

🙌 Credits
Developed with ❤️ by Vincent Nyamao
Built as part of the Stratizen initiative for decentralizing student collaboration.

yaml
Copy
Edit

---