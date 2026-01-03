# Royal Order Sorter

An Android app and web service that uses AI to reorder adjectives and adverbs according to the "Royal Order" of English grammar.

## Versions

This repository includes two versions:

1. **Android App** (`/app`) - Native Android application using WebView
2. **Web Service** (`/web`) - Standalone HTML file that runs in any modern browser

## Features

- **AI-Powered Analysis**: Uses Transformers.js with the Xenova/all-MiniLM-L6-v2 model for semantic similarity matching
- **NLP Tokenization**: Uses Compromise.js for natural language processing
- **Adjective Categories**: Opinion, Size, Physical Quality, Age, Shape, Color, Origin, Material, Purpose
- **Adverb Categories**: Manner, Place, Frequency, Time, Purpose
- **Export Options**: Save results as TXT, DOCX, or PDF
- **Offline AI**: The AI model runs entirely on-device after initial download

## The Royal Order

English has a specific order for stacking adjectives before a noun:
1. Opinion (beautiful, ugly)
2. Size (big, small)
3. Physical Quality (rough, smooth)
4. Age (old, new)
5. Shape (round, square)
6. Color (red, blue)
7. Origin (French, American)
8. Material (wooden, metal)
9. Purpose (sleeping, cooking)

## Building

### Using the Web Service

The web version requires no installation - just open the HTML file in a browser:

1. Download `web/RoyalOrderSorter.html`
2. Open it in any modern browser (Chrome, Firefox, Edge, Safari)
3. Wait for the AI model to download (first run only, ~30MB)
4. Start analyzing text!

You can also host it on any web server or GitHub Pages.

### Android App Prerequisites
- Android Studio Arctic Fox or later
- JDK 17
- Android SDK 34

### Build Steps

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Build and run on device/emulator

### Building with Termux

```bash
# Install required packages
pkg install git openjdk-17

# Clone repo
git clone https://github.com/YOUR_USERNAME/RoyalOrderSorter.git
cd RoyalOrderSorter

# Build (requires Gradle wrapper or manual Gradle installation)
./gradlew assembleDebug
```

## Requirements

- Android 7.0 (API 24) or higher
- Internet connection (for initial AI model download)
- ~100MB storage for cached AI model

## Credits

- **Concept**: Paul Pruitt
- **AI Model**: [Xenova/all-MiniLM-L6-v2](https://huggingface.co/Xenova/all-MiniLM-L6-v2)
- **Libraries**: 
  - [Transformers.js](https://huggingface.co/docs/transformers.js) (Apache 2.0)
  - [Compromise](https://github.com/spencermountain/compromise) (MIT)
  - [FileSaver.js](https://github.com/eligrey/FileSaver.js) (MIT)
  - [docx](https://github.com/dolanmiu/docx) (MIT)
  - [jsPDF](https://github.com/parallax/jsPDF) (MIT)

## License

MIT License
