# 🚀We are BOOSTER ANDROID🚀

## BOOSTER - 빠르게 출력하는 편리함

> SOPT 26기 Appjam '부스터'
>
> Faster / Easier / Together
>
> 프로젝트 기간 2020.06 ~ 진행중

<img src="https://github.com/Booster-developer/Booster-SERVER/blob/dev/images/main.png" ></img>
<br><br>

## 🚀 Project Purpose

- 빠르게 출력하는 편리함

- 대학생을 위한 빠르고 간편한 인쇄 서비스

- 사전 인쇄 주문 서비스
<br>

## 🔧 Tools

- Android Studio

- Zeplin

- Postman
<br>

## 📌 Code Convention

- 변수명은 기본적으로 camelCase로 작성

- ID NAMING : 뷰이름_위젯줄인말_기능이름

- 커밋하기 전에 reformat code를 실행시켜서 코드를 정리해준다.
<br>

## 🌞 Github Branching

- 개인 Branch를 이름으로 만든 뒤 개발한다.

- 개인 Branch에서 develop branch로 PR을 보낸다.

- 모든 기능이 완벽하면서, 모든 팀원이 동의할 때 Master 브랜치로 PR을 보낸다.
<br>

## 🛠 Technology Stack

- Minimum SDK version 24

- Language : Kotlin

- Retrofit : REST API Library

- Gson : Json Data process Library

- Glide : Image Process Library

<br>

## 🔑 Dependency

```kotlin


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

//Naver Map api
implementation "com.naver.maps:map-sdk:3.8.0"

//coordinator layout
implementation "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
```

<br>

## 🖕 주요기능

### 0. ConstraintLayout 사용하기

- 대부분의 레이아웃을 ConstraintLayout으로 구성

- chain 과 match_parent 를 적극 활용하여 뷰 구성

item_order_condition.xml
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

- Guidline을 사용해서 개행 효과 구현

my_file.xml
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
<br>

### 1. 확장함수 사용하기 

- kotlin collection에서 제공하는 확장함수 사용

- 기존 클래스에 custom 함수를 확장하여 사용

BindingAdapter.kt
```kotlin
@BindingAdapter("setCancelVisible")
fun TextView.setCancelVisible(status : Int) {
    if (status!=1){
        visibility = GONE
    }
}

@BindingAdapter("setFavStar")
fun ImageView.setFavStar(status : Int) {
    if (status==0){
        setImageResource(R.drawable.store_detail_ic_star_inactive)
    }else{
        setImageResource(R.drawable.store_detail_ic_star_active)
    }
}
```

<br>

### 2. 중복 클릭 방지

#### 🔥 issue

- 액티비티를 이동하는 버튼 클릭을 여러 번 연속으로 빠르게 할 때 똑같은 액티비티 여러 개가 계속해서 쌓인다.

#### 📒 solution

- ktx(kotlin-extension)을 활용하여 중복 클릭 방지 구현

```kotlin
class OnlyOneClickListener(
    private val clickListener: View.OnClickListener,
    private val interval: Long = 300
) :
    View.OnClickListener {

    private var clickable = true

    override fun onClick(view: View?) {
        if (clickable) {
            clickable = false
            view?.run {
                postDelayed({
                    clickable = true
                }, interval)
                clickListener.onClick(view)
            }
        } else {
            Log.e(TAG, "waiting for a while")
        }
    }
}

fun View.onlyOneClickListener(action: (v: View) -> Unit) {
    val listener = View.OnClickListener { action(it) }
    setOnClickListener(OnlyOneClickListener(listener))
}
```

##### 이전 코드

```kotlin
act_main_btn_store.setOnClickListener {
            val intent = Intent(this@MainActivity, StoreListActivity::class.java)
            startActivity(intent)
        }
```

##### 바뀐 코드

```kotlin
act_main_btn_store.onlyOneClickListener {
            val intent = Intent(this@MainActivity, StoreListActivity::class.java)
            startActivity(intent)
        }
```

#### 🗞 result

- 연달아 터치시 불필요한 clickEvent가 일어나지 않도록 막을 수 있다.

<br>

### 3. Scroll Animation

#### 🔥 issue

- 뷰 스크롤시 타이틀 레이아웃이 상단에 고정된채로 RecyclerView가 스크롤 되야한다.

#### 📒 solution

- CollapsingToolbarLayout를 사용하여 타이틀 상단 고정
- addOnOffsetChangedListener 안에서 뷰의 alpha 값을 조절하여 toolbar fade out 효과 구현

frag_store_list.xml

```kotlin
<androidx.coordinatorlayout.widget.CoordinatorLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fitsSystemWindows="true"
        tools:context=".ui.storeList.StoreListFragment">

        <com.google.android.material.appbar.AppBarLayout
            android:id="@+id/frag_store_list_appBar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:fitsSystemWindows="true"
            android:theme="@style/AppTheme.AppBarOverlay">

            <com.google.android.material.appbar.CollapsingToolbarLayout
                android:id="@+id/frag_store_list_toolBar_layout"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:background="@color/white"
                android:fitsSystemWindows="true"
                app:layout_scrollFlags="scroll|exitUntilCollapsed"
                app:toolbarId="@+id/frag_store_list_toolBar">

                <androidx.appcompat.widget.Toolbar
                    android:id="@+id/frag_store_list_toolBar"
                    android:layout_width="match_parent"
                    android:layout_height="97dp"
                    android:background="@color/white"
                    app:layout_collapseMode="pin"
                    app:popupTheme="@style/AppTheme.PopupOverlay">

                    <ImageView
                        android:id="@+id/frag_store_list_iv_map"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_gravity="end|top"
                        android:layout_marginTop="4dp"
                        android:layout_marginEnd="2dp"
                        android:layout_marginBottom="4dp"
                        android:src="@drawable/store_detail_ic_map_blue"
                        app:layout_collapseMode="parallax" />

                </androidx.appcompat.widget.Toolbar>

                <androidx.constraintlayout.widget.ConstraintLayout
                    android:id="@+id/frag_store_list_cl_title"
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    android:layout_marginTop="80dp"
                    android:background="@android:color/transparent"
                    app:layout_collapseMode="pin">

                    <TextView
                        android:id="@+id/frag_store_list_tv_title"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:layout_marginStart="16dp"
                        android:layout_marginTop="9dp"
                        android:fontFamily="@font/noto_sans_kr_bold"
                        android:text="매장"
                        android:textColor="@color/black"
                        android:textSize="26sp"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintTop_toTopOf="parent" />

                    ...

                </androidx.constraintlayout.widget.ConstraintLayout>

            </com.google.android.material.appbar.CollapsingToolbarLayout>

        </com.google.android.material.appbar.AppBarLayout>

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/frag_store_list_rv"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">

        </androidx.recyclerview.widget.RecyclerView>

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

StoreListFragment.kt

```kotlin
frag_store_list_appBar.addOnOffsetChangedListener(OnOffsetChangedListener { frag_store_list_appBar, verticalOffset ->
            if (frag_store_list_appBar.totalScrollRange == 0 || verticalOffset == 0) {
                frag_store_list_iv_map.alpha = 1f
                return@OnOffsetChangedListener
            }
            val ratio = verticalOffset.toFloat() / frag_store_list_appBar.totalScrollRange.toFloat()
            frag_store_list_iv_map.alpha = 1f- abs(ratio)
    })
```

#### 🗞 result

- 애니메이션을 더하니 좀 더 생기있는 뷰를 만들 수 있었다.

- 하지만 애니메이션을 적용하니 디자이너가 요구하는 정확한 뷰(그림자 등)을 만드는 데에는 약간의 어려움이 있었다.

### 4. form-data 로 서버에 데이터(image,pdf 등) 전송하기

#### 🔥 issue

- form-data 로 pdf,image 파일을 서버에 전송해야 한다.

#### 📒 solution

- 경로를 통해 File 객체를 만들어 준 다음 RequestBody -> Multipart.Part 순으로 변환한 다음 통신을 진행한다.

BoosterService.kt

```kotlin
    @Multipart
    @POST("/orders/{order_idx}/file")
    suspend fun postUploadFile(
        @Header("token") token: String,
        @Path("order_idx") orderIdx: Int,
        @Part file: MultipartBody.Part?,
        @Part thumbnail: MultipartBody.Part?
    ): ApiWrapper<com.example.booster.data.datasource.model.File>

```

FileStorageViewModel.kt

```kotlin
var requestBody: RequestBody? = null
        var requestBody2: RequestBody? = null

        when (file?.file_extension) {
            ".png" -> {
                requestBody = RequestBody.create(
                    MediaType.parse("image/png"), imageFile
                )
                requestBody2 = RequestBody.create(
                    MediaType.parse("image/png"), imageFile
                )
            }
            ".pdf" -> {
                requestBody = RequestBody.create(
                    MediaType.parse("application/pdf"), docFile
                )
                requestBody2 = RequestBody.create(
                    MediaType.parse("image/png"), thumbnailFile
                )
            }
            ".docx" -> requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"), docFile
            )
            ".jpeg", ".jpg" -> {
                requestBody = RequestBody.create(
                    MediaType.parse("image/jpeg"), imageFile
                )
                requestBody2 = RequestBody.create(
                    MediaType.parse("image/jpeg"), imageFile
                )
            }
        }
        Log.e(
            "pdfcheck",
            "check: " + requestBody + " " + file?.file_extension + " " + file?.file_name
        )
        val multipartBody =
            MultipartBody.Part.createFormData("file", file?.file_name, requestBody)

        val multipartBody2 =
            MultipartBody.Part.createFormData("thumbnail", "png", requestBody2)
            
```

#### 🗞 result

- MediaType 변환 문구가 틀리고, 불 필요한 헤더를 넣어서 처음엔 시행착오를 많이 겪었지만, 결국 해내서 또 한 번의 성장을 이룩했다.

### 5. pdf 미리보기 기능

#### 🔥 issue

- pdf를 저장소로부터 받아와서 미리보기 기능을 제공한다.

#### 📒 solution

- PdfRenderer 를 이용해서 pdf 미리보기 기능 제공.

```kotlin
val fileDescriptor: ParcelFileDescriptor?
        fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)

        //min. API Level 21
        val pdfRenderer: PdfRenderer?
        pdfRenderer = PdfRenderer(fileDescriptor)
        val pageCount: Int = pdfRenderer.pageCount
        Log.e(
            "pagecount",
            "check: " + pageCount.toString() + " " + pdfviewer_act_main_total_page.text
        )
        pdfviewer_act_main_total_page.text = pageCount.toString()
        Toast.makeText(this, "pageCount = $pageCount", Toast.LENGTH_LONG).show()

        val parentlayout = LinearLayout(this)
        parentlayout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
        parentlayout.orientation = LinearLayout.HORIZONTAL

        if (pageCount != 1) {
            pdfviewer_act_main_hs.removeView(pdfviewer_act_main_ll)
            pdfviewer_act_main_hs.addView(parentlayout)
        }

        for (i in 0 until pageCount) {
            pdfviewer_act_main_cur_page.text = (i + 1).toString()
            val imageView = ImageView(this)
            imageView.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ) // value is in pixels
            val rendererPage = pdfRenderer.openPage(i)
            val rendererPageWidth: Int = rendererPage.width
            val rendererPageHeight: Int = rendererPage.height
            val bitmap =
                Bitmap.createBitmap(rendererPageWidth, rendererPageHeight, Bitmap.Config.ARGB_8888)
            rendererPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            imageView.setImageBitmap(bitmap)

            if (pageCount == 1) {
                pdfviewer_act_main_ll.addView(imageView)
            }else {
                parentlayout.addView(imageView)
            }

            rendererPage!!.close()
        }

        pdfRenderer.close()
        fileDescriptor.close()
```

#### 🗞 result

- pdf나 이미지 미리보기를 제공 할 수 있었지만, hwp,ppt 등 오피스 기반 문서들은 제공하기에 까다로웠다. 방법을 찾아보도록 하겠다.


## 👨‍👨‍👧‍👧‍👧 Developer


- 김예진 - [jineee](https://github.com/jineeee)
- 김찬영 - [ghkdua1829](https://github.com/ghkdua1829)
- 김지현 - [jiHyeonMon](https://github.com/JiHyeonMon)
- 서정록 - [chop-sui](https://github.com/chop-sui)
- 이정민 - [danmin20](https://github.com/danmin20)

