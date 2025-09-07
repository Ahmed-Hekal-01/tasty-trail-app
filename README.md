# TastyTrail - Recipe App 🍽️

> Modern Android app with **MVVM architecture**, **Hilt DI**, **Room database**, and **TheMealDB API** integration.

## 🎥 [Demo Video](https://youtu.be/your-demo-video-link) | 📸 [Screenshots](#screenshots)

## 🚀 Key Technical Features

- **🏗️ Architecture**: MVVM + Single Activity + Navigation Component
- **💉 DI**: Dagger Hilt 2.56.2 with KSP
- **🗄️ Database**: Room 2.6.0 for offline favorites
- **🌐 Networking**: Retrofit + Moshi for TheMealDB API
- **🎨 UI**: Material Design 3 + View Binding + Bottom Navigation
- **📱 Performance**: Picasso image caching + Kotlin Coroutines

## ✨ Features

| Feature | Implementation |
|---------|----------------|
| **Authentication** | Persistent login with SharedPreferences |
| **Recipe Discovery** | TheMealDB API with offline caching |
| **Search & Favorites** | Real-time search + Room database |
| **Navigation** | Smart bottom nav (hidden on auth screens) |
| **User Experience** | Material dialogs, animations, error handling |

## 📸 Screenshots

<div align="center">

| Home Feed | Recipe Details | Search & Favorites |
|:---:|:---:|:---:|
| ![Home](screenshots/home.png) | ![Details](screenshots/details.png) | ![Search](screenshots/search.png) |

</div>

## 🛠️ Tech Stack

```kotlin
// Architecture
MVVM + Single Activity + Navigation Component

// Dependencies
Hilt 2.56.2          // Dependency Injection
Room 2.6.0           // Local Database
Retrofit + Moshi     // API Integration
Picasso 2.8          // Image Loading
Material Design 3    // UI Components
```

## 🚀 Quick Start

```bash
git clone https://github.com/Ahmed-Hekal-01/tasty-trail-app.git
cd tasty-trail-app
# Open in Android Studio, sync, and run
```

**Requirements**: Android Studio | Min SDK 21 | Target SDK 36

## 📊 Project Highlights

- **Clean Architecture** with proper separation of concerns
- **Modern Android Development** using latest practices
- **Production Ready** with error handling and offline support
- **Scalable Codebase** with modular structure and DI

---

**Built by [Ahmed Hekal](https://github.com/Ahmed-Hekal-01)** | ⭐ Star if you find this helpful!
