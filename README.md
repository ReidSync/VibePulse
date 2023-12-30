# VibePulse
[![Kotlin](https://img.shields.io/badge/Kotlin--white.svg?logo=kotlin&color=6A5ACD)](http://kotlinlang.org/)
[![Swift](https://img.shields.io/badge/Swift--white.svg?logo=swift&color=EC5E42)](https://developer.apple.com/swift/)
[![Jetpack Compose](https://img.shields.io/static/v1?&message=Jetpack+Compose&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label=)](https://developer.android.com/jetpack/compose)
[![SwiftUI](https://img.shields.io/badge/SwiftUI-white.svg?logo=swift&color=3a66f5&logoColor=white)](https://developer.apple.com/xcode/swiftui/)


[![badge-kotlin-multiplatform]](https://kotlinlang.org/docs/multiplatform.html)
[![badge-MVVM]](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93viewmodel)
[![badge-TCA]](https://github.com/pointfreeco/swift-composable-architecture)

**VibePulse** is a mood journal application that runs on both Android and iOS platforms.

## Description
This uses the [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) to provide support for both Android and iOS platforms through a single project with partially shared code like data models, business logic, and some common resources. Plans are in progress to extend this support to all platforms in the future.

## Previews

| Android    | iOS |
| ----------- | ----------- |
| ![](https://github.com/ReidSync/VibePulse/demo-gif/VibePulse_Android.gif) | ![](https://github.com/ReidSync/VibePulse/demo-gif/VibePulse_iOS.gif) |

## Todo
> Everything is a work in progress. The functions could be improved and modified, even if they have already been completed.

Function | Done
:------------ | :-------------
Home screen with a journal list | :heavy_check_mark:
Screen for editing journal contents | :heavy_check_mark:
Journal metadata screen | :heavy_check_mark:
Add a new journal | :heavy_check_mark:
Edit a journal metadata | :heavy_check_mark:
Support dark and light themes | :heavy_check_mark:
Check feelings | :heavy_check_mark:
Check mood factors | :heavy_check_mark:
Shared theme color resource | :heavy_check_mark:
Common data models | :heavy_check_mark:
User login with Firebase auth | 
Store user data in Firestore |
Show weather information |
Track & analyze mood patterns | 
Screen for tracking & analyzing mood patterns |
Save each journal in a separate file |
Attractive app design |



<!-- Badges -->
[badge-kotlin-multiplatform]: https://img.shields.io/badge/Shared-Kotlin%20Multiplatform-6A5ACD.svg?style=flat
[badge-TCA]: https://img.shields.io/badge/iOS-The%20Composable%20Architecture-3B7097.svg?style=flat
[badge-MVVM]: https://img.shields.io/badge/Android-MVVM-C56C86.svg?style=flat