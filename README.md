#Project Title  :
_______________

this project is a spotify application that allow user to listen to musics and add his favourite
playlist and he can search for songs,artists,playlists or albums

********************************************************************************************

#Getting Started:
________________


*Prerequisites:
_______________

-you need to install Android studio "https://developer.android.com/studio/?gclid=CjwKCAjw1cX0BRBmEiwAy9tKHsfJ9T5RUFtlU2dYP6ipkWzfxQ8I61SzgyYOqoCrre5ANsOn5CzZmxoCg8IQAvD_BwE&gclsrc=aw.ds"
-you must have an internet connection
-you need before run any test or generate code coverage to

"enable Developer option on your device" :
(head to setting->update&security->aboutphone->click 7 times at software version->Developer option will appear )
by using either:

1)connect your device with USP

or

2)Emulator by using "https://www.dummies.com/web-design-development/mobile-apps/how-to-set-up-an-emulator-in-android-studio/"


*Installing:
_______________

- Clone this repo:
"https://github.com/MohmedMonsef/Marioplex-Android"

- Open the project in Android Studio

- Make sure to have `buildToolsVersion "29.0.0"`

*********************************************************************************************

#Running Tests:
_______________
-Test will be in "TestHomeSearch"
-you must turn off animation on your phone:
Developer option->turn off (Window animation scale,Transition animation scale,animator deuration scale)
-open Android studio 
-in the project tap in the lift 
-->open project name "FragSpotify"
->click on app
-->src
-->androidTest
-->right click on java and choose "Run'AllTests'"


*To Generate Code Coverage:
___________________________
-Open Android Studio
-click on Gradle  in top right
--> click on app
 -->click on the Gradle icon
-->write in the command line"createDebugAndroidTestCoverageReport"

-the tests will run when finished the report will be in app->>build->>reports->>coverage->>debug
-then open index.html in browse

*****************************************************************************************

#How to run for developers:
____________

- Open project in Android Studio
- Press Shift+F10 to run

********************************************************************************************* 

#How to build for production:
____________

- choose Build in toolbar
- select Build APK
- copy the APK file to your phone and install it
- then you will have APP on your phone running

********************************************************************************************* 

#Generate Functional Documentation:
___________________________________
-open Android studio
-Go to Tools
-click on Generate "JavaDoc"
-choose you output directory
-choose the function type
-then OK

********************************************************************************************* 

#Built with:
____________
(1)Retrofit & OkHttp:

implementation 'com.squareup.retrofit2:retrofit:2.5.0'
implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
implementation 'com.google.code.gson:gson:2.8.5'

(2)Loading Images:
implementation 'com.squareup.picasso:picasso:2.71828'

(3)Recyclerview:

implementation 'com.android.support:recyclerview-v7:29.0.0'

(4)ViewModel and LiveData

def lifecycle_version = "2.0.0"
implementation "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"

(5) Design Circular images:

implementation 'com.android.support:design:29.1.0'

(6) CardView :

implementation "com.android.support:cardview-v7:29.0.0-rc02"

(7) slidinguppanel:

implementation 'com.sothree.slidinguppanel:library:3.4.0'

(8) Login with facebook:

 implementation 'com.facebook.android:facebook-login:[5,6)'

(9) For testing :

androidTestImplementation 'androidx.test.ext:junit:1.1.1'
androidTestImplementation 'androidx.test:runner:1.2.0'
androidTestImplementation 'androidx.test.espresso:espresso-core:3.2.0'
androidTestImplementation 'androidx.test:rules:1.2.0'

*************************************************************************************

#Authors:

-CMP 2020
