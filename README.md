
# 📱 Stratizen Core — Student Event Management App

**Stratizen Core** is an offline-first, gamified event management system built for university students. It enables students to create, manage, and organize both academic and non-academic events seamlessly. The app also promotes collaboration through group filtering, all while tracking user engagement via an XP system — making productivity fun.

---

## 🚀 Features

- 🧠 **Offline-first** — Uses Room Database for reliable local storage
- 🧪 **MVVM Architecture** — Clean separation of concerns with LiveData and ViewModel
- 🎮 **Gamification** — XP and level tracking to motivate participation
- 🔄 **Smart Group Filtering** — Organize events by *General*, *Clubs*, *Transport*, or *Class*
- 📅 **Auto-grouped by Date** — Events are dynamically grouped by day
- ✏️ **Add, Edit & Delete Events** — Includes Undo Snackbar for error recovery
- 🎨 **Modern UI** — Jetpack Compose + Material3 for sleek, responsive design
- 🧱 **Scaffold Layout** — Organized and consistent screen structure

---

## 🏗️ Tech Stack

| Tool               | Purpose                                  |
|--------------------|------------------------------------------|
| **Kotlin**         | Primary development language             |
| **Jetpack Compose**| Declarative, modern Android UI           |
| **Room DB**        | Local persistence for offline use        |
| **ViewModel**      | Manages UI-related data lifecycle-aware  |
| **LiveData**       | Observables for reactive UI updates      |
| **Material 3**     | Modern UI design components              |
| **Navigation**     | In-app screen transitions with Compose   |

---

## 📁 Project Structure

```
com.stratizen.core/
├── data/
│   ├── db/           # Room database setup (DAOs, entities)
│   └── model/        # Data models (Event, XP, etc.)
├── repository/       # Repository layer for data abstraction
├── ui/
│   ├── components/   # Reusable UI elements (EventCard, DropdownMenuBox)
│   └── screens/      # Screen Composables (HomeScreen, AddEventScreen)
├── viewmodel/        # State management using ViewModel & LiveData
```

---

## 🧪 How It Works

1. Users can **create events**, choosing group tags (like "Class", "Club", etc.)
2. Events are stored in a **Room Database**, ensuring access even offline.
3. The app groups events by **date** and **tag**, and displays them on a scrollable list.
4. Students earn **XP** for creating events — laying the foundation for achievements, ranks, or unlocks.
5. Users can **edit** or **delete events**, with an *Undo* option for safe rollbacks.
6. A clean **Material3 UI** and **Scaffold layout** ensure smooth navigation and a great user experience.

---

## 🛠️ Getting Started

### ✅ Requirements

- Android Studio **Giraffe** or newer  
- Kotlin **1.9+**  
- Gradle **8.0+**  
- Android device or emulator (**API 31+** recommended)

### ▶️ Run Locally

```bash
# Clone the repo
git clone https://github.com/your-username/stratizen-core.git

# Open in Android Studio and Run
```

---

## 🧠 Upcoming Features

- 📆 **Google Calendar integration**
- 🧭 **Deep links** and **in-app notifications**
- 🔄 **Cloud sync** with Firestore or Supabase
- 👥 **Peer-to-peer collaboration** and **chat support**
- 🛍️ **In-app student marketplace**
- 🔐 **Encrypted backups** and event sharing

---

## 📄 License

This project is licensed under the **MIT License** — free to use for educational or personal purposes.

```
MIT License

Permission is hereby granted, free of charge, to any person obtaining a copy...
```

---

## 🙌 Credits

Built with ❤️ by **Vincent Nyamao**  
Part of the **Stratizen Initiative** — a decentralized movement for enhancing student productivity, coordination, and innovation on campus.

---

## 📸 Screenshots (Coming Soon)

<!-- Uncomment and add images once available
![Home Screen](screenshots/home_screen.png)
![Add Event Screen](screenshots/add_event.png)
-->
