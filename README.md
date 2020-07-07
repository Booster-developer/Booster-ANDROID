# 🚀We are BOOSTER ANDROID🚀

## BOOSTER - 빠르게 출력하는 편리함

> SOPT 26기 Appjam '부스터'
>
> Faster / Easier / Together
>
> 프로젝트 기간 2020.06 ~ 진행중

## 📈 Workflow

<img src="https://user-images.githubusercontent.com/45157374/86798602-eb6dbe80-c0ab-11ea-86cb-23cc73b472c1.png" width="80%"></img>

## 📌 Code Convention

- 변수명은 기본적으로 camelCase로 작성.

- ID NAMING : 뷰이름_위젯줄인말_기능이름

## 🌞 Github Branching

- 개인 Branch를 이름으로 판 뒤 개발한다.

- 개인 Branch에서 develop branch로 PR을 보낸다.

- 모든 기능이 완벽하면서, 모든 팀원이 동의할 때 Master 브랜치로 PR을 보낸다.

## 🚀 Project Purpose

- 빠르게 출력하는 편리함

- 대학생을 위한 빠르고 간편한 인쇄 서비스

- 사전 인쇄 주문 서비스

## 🛠 Technology Stack

- Minimum SDK version 24

- Language : Kotlin

- Retrofit : REST API Library

- Gson : Json Data process Library

- Glide : Image Process Library

## 🔑 Dependency

```kotlin
//Retrofit 라이브러리 : https://github.com/square/retrofit
implementation 'com.squareup.retrofit2:retrofit:2.6.2'
//Retrofit 라이브러리
implementation 'com.squareup.retrofit2:retrofit-mock:2.6.2'
// Retrofit2.
implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'

//객체 시리얼라이즈를 위한 Gson 라이브러리 : https://github.com/google/gson
implementation 'com.google.code.gson:gson:2.8.6'
//Retrofit 에서 Gson 을 사용하기 위한 라이브러리
implementation 'com.squareup.retrofit2:converter-gson:2.6.2'

//okHttp
implementation 'com.squareup.okhttp3:logging-interceptor:3.8.1'
implementation 'com.squareup.okhttp3:okhttp:3.12.0'

//리사이클러뷰 라이브러리
implementation 'androidx.recyclerview:recyclerview:1.2.0-alpha04'

//동그란 이미지 커스텀 뷰 라이브러리 : https://github.com/hdodenhof/CircleImageView
implementation 'de.hdodenhof:circleimageview:3.1.0'

// Glide
implementation 'com.github.bumptech.glide:glide:4.11.0'

//rxjava
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
implementation 'io.reactivex.rxjava2:rxjava:2.2.7'
implementation 'io.reactivex.rxjava2:rxkotlin:2.3.0'

//생명주기를 공유하기 위한 라이브러리
implementation "androidx.appcompat:appcompat:1.1.0"
//LiveData를 사용하기 위한 라이브러리
implementation "androidx.lifecycle:lifecycle-viewmodel:2.2.0"

// CardView Library
implementation 'androidx.cardview:cardview:1.0.0'

//Lottie Library
implementation 'com.airbnb.android:lottie:3.4.1'

// Koin for Kotlin
implementation "org.koin:koin-core:$koin_version"
// Koin extended & experimental features
implementation "org.koin:koin-core-ext:$koin_version"
// Koin for Unit tests
testImplementation "org.koin:koin-test:$koin_version"
// Koin for Java developers
implementation "org.koin:koin-java:$koin_version"
    
// Koin for Android
implementation "org.koin:koin-android:$koin_version"
// Koin Android Scope features
implementation "org.koin:koin-android-scope:$koin_version"
// Koin Android ViewModel features
implementation "org.koin:koin-android-viewmodel:$koin_version"
// Koin Android Experimental features
implementation "org.koin:koin-android-ext:$koin_version"

// Koin AndroidX Scope features
implementation "org.koin:koin-androidx-scope:$koin_version"
// Koin AndroidX ViewModel features
implementation "org.koin:koin-androidx-viewmodel:$koin_version"
// Koin AndroidX Experimental features
implementation "org.koin:koin-androidx-ext:$koin_version"
```



## 🖕 주요기능

### 0. ConstraintLayout 사용하기

- 대부분의 레이아웃을 ConstraintLayout으로 구성.

- chain 과 match_parent 를 적극 활용하여 뷰 구성.

```kotlin
<androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/item_order_prodress_cl_bar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginStart="26dp"
            android:layout_marginEnd="26dp"
            android:layout_marginTop="24dp"
            app:layout_constraintTop_toBottomOf="@id/item_order_progress_tv_list"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent">

            <View
                android:layout_width="match_parent"
                android:layout_height="3dp"
                android:background="@drawable/bg_progress_receipt"
                setGradation="@{conditionRes.status}"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintEnd_toEndOf="parent"/>

            <ImageView
                android:id="@+id/item_order_condition_iv_cicle_1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                changeCircleF="@{conditionRes.status}"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintBottom_toBottomOf="parent"/>

            ...

        </androidx.constraintlayout.widget.ConstraintLayout>
```

- Guidline을 사용해서 개행 효과 구현.

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="380dp"
    android:layout_height="96dp"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:background="@drawable/bg_ffffff_round"
    android:elevation="5dp"
    android:layout_gravity="center_horizontal">

    <androidx.constraintlayout.widget.Guideline
        android:id="@+id/my_file_guideline1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintGuide_begin="350dp"/>

    <ImageView
        android:id="@+id/iv_file"
        android:layout_width="60dp"
        android:layout_height="60dp"
        tools:src="@drawable/order_wait_img_1"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        android:layout_marginLeft="8dp"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="8dp"/>
    
   ...
   
</androidx.constraintlayout.widget.ConstraintLayout>
```


## 👨‍👨‍👧‍👧‍👧 Developer


- 김예진 - [jineee](https://github.com/jineeee)
- 김찬영 - [ghkdua1829](https://github.com/ghkdua1829)
- 김지현 - [jiHyeonMon](https://github.com/JiHyeonMon)
- 서정록 - [chop-sui](https://github.com/chop-sui)
- 이정민 - [danmin20](https://github.com/danmin20)

