# android-go-example
[![Codacy Badge](https://api.codacy.com/project/badge/grade/e48173d1a215408cb0088fd70260857e)](https://www.codacy.com/app/artemnikitin/android-go-example)    
Example Android app that used Go mobile

### Prerequisites
You should have valid credentials to use HERE REST API. If you don't have it register on https://developer.here.com/

### Setup
1. Setup https://github.com/artemnikitin/android-go-shared as described in readme
2. Clone this repo  
3. Put HERE credentials in AndroidManifest.xml
4. Run ```./gradlew clean assembleDebug``` to build an apk    
Warning! Due to fact that tools are not yet fully ready for gomobile you can't run project from Android Studio.
