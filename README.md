# 🛍️ Easy Dealers

A modern Android e-commerce application that allows users to browse products across multiple categories and compare prices between Flipkart and Amazon and more.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Material Design](https://img.shields.io/badge/Material%20Design-757575?style=for-the-badge&logo=material-design&logoColor=white)

## 📱 Features

### User Authentication
- **Login & Registration** - Secure user authentication with input validation
- **Form Validation** - Email, password, phone number, and address validation
- **Persistent Sessions** - Stay logged in across app restarts

### Product Browsing
- **Multiple Categories** - Browse products across 6 categories:
  - 👕 Clothing
  - 📱 Smartphones
  - 💻 Electronics
  - 💄 Beauty Products
  - 💊 Pharmacy
  - 🛒 Groceries
- **Search Functionality** - Real-time search across all products
- **Product Details** - View detailed information about each product

### Shopping Experience
- **Shopping Cart** - Add, view, and remove items from cart
- **Cart Badge** - Visual indicator showing number of items in cart
- **Price Display** - Clear pricing with Indian Rupee (₹) formatting
- **Total Calculation** - Automatic cart total calculation

### Price Comparison
- **Multi-Platform Compare** - Compare prices between Flipkart and Amazon
- **Savings Calculator** - See how much you can save
- **Visual Results** - Easy-to-understand comparison interface

### Additional Features
- **Dark Mode** - Toggle between light and dark themes
- **Material Design 3** - Modern, clean UI following Material Design guidelines
- **Bottom Navigation** - Easy access to Home, Cart, and Profile
- **Smooth Animations** - Polished transitions and interactions

## 🏗️ Architecture

### Tech Stack
- **Language**: Kotlin
- **Minimum SDK**: API 24 (Android 7.0)
- **UI Framework**: Material Design 3 Components
- **Architecture Pattern**: Single Activity with multiple fragments approach
- **Data Storage**: JSON-based product catalog
- **Image Loading**: Glide library

### Key Components
```
app/
├── activities/
│   ├── MainActivity.kt (Login)
│   ├── Register.kt (Registration)
│   ├── HomeActivity.kt (Main Dashboard)
│   ├── CategoryActivities/ (Product Listings)
│   ├── ProductDetailActivity.kt
│   ├── CartActivity.kt
│   ├── CompareActivity.kt
│   └── ProfileActivity.kt
├── adapters/
│   ├── ProductAdapter.kt
│   └── CartAdapter.kt
├── models/
│   └── Product.kt
└── managers/
    ├── CartManager.kt (Singleton Cart Manager)
    └── ThemeManager.kt (Theme Preferences)
```

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 11 or higher
- Android SDK with API 24+

### Installation

1. **Clone the repository**

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory

3. **Add Product Data**
   - Place your `products.json` file in `app/src/main/assets/`
   - Format: See [Product JSON Format](#product-json-format) below

4. **Build and Run**
   - Click "Run" (Shift + F10)
   - Select your device/emulator
   - Wait for Gradle build to complete

## 📋 Product JSON Format

Place a `products.json` file in `app/src/main/assets/` with the following structure:
```json
[
  {
    "id": "1",
    "name": "Product Name",
    "category": "Clothing",
    "price": 999.99,
    "imageUrl": "https://example.com/image.jpg",
    "description": "Product description here"
  }
]
```

**Supported Categories:**
- `Clothing`
- `SmartPhones`
- `Electronics`
- `Beauty`
- `Pharmacy`
- `Groceries`

## 🎨 Screenshots

### Light Mode
| Login | Home | Product Details |
|-------|------|-----------------|
| ![Login](screenshots/login_light.png) | ![Home](screenshots/home_light.png) | ![Details](screenshots/details_light.png) |

### Dark Mode
| Cart | Compare | Profile |
|------|---------|---------|
| ![Cart](screenshots/cart_dark.png) | ![Compare](screenshots/compare_dark.png) | ![Profile](screenshots/profile_dark.png) |

## 🔧 Configuration

### Theme Customization
Edit `res/values/themes.xml` and `res/values-night/themes.xml` to customize colors:
```xml
<color name="lavender">#8B7AB8</color>
<color name="md_theme_light_primary">#6750A4</color>
<color name="md_theme_dark_primary">#D0BCFF</color>
```

### String Localization
All user-facing strings are in `res/values/strings.xml` for easy localization.

## 📦 Dependencies
```gradle
dependencies {
    // Material Design
    implementation 'com.google.android.material:material:1.11.0'
    
    // Image Loading
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    
    // JSON Parsing
    implementation 'com.google.code.gson:gson:2.10.1'
    
    // AndroidX
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    implementation 'androidx.recyclerview:recyclerview:1.3.2'
    implementation 'androidx.cardview:cardview:1.0.0'
}
```

## 🧪 Testing

### Manual Testing Checklist
- [ ] Login with valid credentials
- [ ] Browse all 6 categories
- [ ] Search products
- [ ] Add items to cart
- [ ] Remove items from cart
- [ ] Compare prices
- [ ] Toggle dark mode
- [ ] Navigate using bottom navigation

### Known Issues
- Price comparison uses simulated Amazon prices (random variation)
- Cart data is not persisted across app restarts
- No actual payment integration

## 🛣️ Roadmap

- [ ] Implement backend API integration
- [ ] Add user authentication with Firebase
- [ ] Persist cart data in local database (Room)
- [ ] Add product filtering and sorting
- [ ] Implement actual price scraping from e-commerce sites
- [ ] Add wishlist functionality
- [ ] Implement order history
- [ ] Add payment gateway integration
- [ ] Push notifications for deals
- [ ] Product reviews and ratings

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Style
- Follow Kotlin coding conventions
- Use meaningful variable and function names
- Add comments for complex logic
- Maintain consistent indentation (4 spaces)

## 👨‍💻 Authors

**Aashish Sah**  
- GitHub: [@aashishsah005](https://github.com/aashishsah005)
- Email: aashishsah005@gmail.com

---

## 🙏Acknowledgments

- Android SQLite Documentation
- Material Design Guidelines
- Kotlin Coroutines Documentation
- Stack Overflow Community
- MyJSON.online for free JSON hosting

---

## Support

If you found this project helpful, please give it a ⭐ on GitHub!

---