# 🚀We are BOOSTER ANDROID🚀

## BOOSTER - 빠르게 출력하는 편리함

> SOPT 26기 Appjam '부스터'
>
> Faster / Easier / Together
>
> 프로젝트 기간 2020.06 ~ 진행중

<img src="https://user-images.githubusercontent.com/46866476/87736308-bcc1b780-c812-11ea-855c-b9306a894200.jpeg" ></img>
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

## ⚙️ Project Structure

- data,ui,util 의 큰 패키지를 두고, 안에 세부 패키지로 나눔.

<img width="300" alt="파일구조 세번째" src="https://user-images.githubusercontent.com/45157374/87788277-0db7c700-c878-11ea-8103-0c0180c51777.png"><img width="300" alt="파일구조 첫번째" src="https://user-images.githubusercontent.com/45157374/87788260-085a7c80-c878-11ea-9b3f-68e82833ce55.png">
<img width="300" alt="파일구조 두번째" src="https://user-images.githubusercontent.com/45157374/87788271-0c869a00-c878-11ea-8722-5b5d99108f6a.png">

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

//파일픽커 라이브러리
implementation 'com.droidninja:filepicker:2.2.4'

//Material Components
implementation 'com.google.android.material:material:1.3.0-alpha01'
//TedPermission 라이브러리
implementation 'gun0912.ted:tedpermission:2.2.3'
//coroutines
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.4"
//Glide
implementation 'com.github.bumptech.glide:glide:4.11.0'
annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'

//lifecycle
implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0-alpha01"
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.1.0-beta01"

//Naver map
implementation "com.naver.maps:map-sdk:3.8.0"

//coordinator layout
implementation "androidx.coordinatorlayout:coordinatorlayout:1.1.0"

//pdfium
implementation 'com.github.barteksc:pdfium-android:1.9.0'
```

<br>

## 🖕 주요기능

### 0. ConstraintLayout 사용하기

- 대부분의 레이아웃을 ConstraintLayout으로 구성
- chain 과 match_parent 를 적극 활용하여 뷰 구성

<center><img width="196" alt="스크린샷 2020-07-17 오후 6 26 23" src="https://user-images.githubusercontent.com/37479631/87776681-19e55980-c863-11ea-8ab0-47fd592889c8.jpg"></center>

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

- Constraint Chain을 이용해 가운데 정렬로 배치 

<img width="196" alt="스크린샷 2020-07-17 오후 6 26 23" src="https://user-images.githubusercontent.com/37479631/87776082-22896000-c862-11ea-8968-35ccd393b2c6.jpg"><img width="196" alt="스크린샷 2020-07-17 오후 6 26 23" src="https://user-images.githubusercontent.com/37479631/87775776-9a0abf80-c861-11ea-9470-a51dae3f9eb0.png">

activity_store_file_option.kt

```kotlin
<androidx.constraintlayout.widget.ConstraintLayout
            android:id="@+id/option4-1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginTop="12dp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/option4">

            <LinearLayout
                android:id="@+id/linearcut1"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toStartOf="@+id/linearcut2"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent">
                
                <ImageView
                    android:id="@+id/order_option_btn_cut_1"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    app:srcCompat="@drawable/sel_order_option_btn_cut_1" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_horizontal"
                    android:layout_marginTop="4dp"
                    android:fontFamily="@font/noto_sans_kr_regular"
                    android:text="1개"
                    android:textColor="#7d7d7d"
                    android:textSize="12sp" />
            </LinearLayout>

            <LinearLayout
                android:id="@+id/linearcut2"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toStartOf="@+id/linearcut3"
                app:layout_constraintStart_toEndOf="@id/linearcut1"
                app:layout_constraintTop_toTopOf="parent"
                app:layout_constraintVertical_bias="0.0">

                <ImageView
                    android:id="@+id/order_option_btn_cut_2"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    app:srcCompat="@drawable/sel_order_option_btn_cut_2" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_horizontal"
                    android:layout_marginTop="4dp"
                    android:fontFamily="@font/noto_sans_kr_regular"
                    android:text="2개"
                    android:textColor="#7d7d7d"
                    android:textSize="12sp" />
            </LinearLayout>

            <LinearLayout
                android:id="@+id/linearcut3"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toStartOf="@+id/linearcut4"
                app:layout_constraintStart_toEndOf="@id/linearcut2"
                app:layout_constraintTop_toTopOf="parent">

                <ImageView
                    android:id="@+id/order_option_btn_cut_3"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    app:srcCompat="@drawable/sel_order_option_btn_cut_3" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_horizontal"
                    android:layout_marginTop="4dp"
                    android:fontFamily="@font/noto_sans_kr_regular"
                    android:text="3개"
                    android:textColor="#7d7d7d"
                    android:textSize="12sp" />
            </LinearLayout>

            <LinearLayout
                android:id="@+id/linearcut4"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:orientation="vertical"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toEndOf="@id/linearcut3"
                app:layout_constraintTop_toTopOf="parent">

                <ImageView
                    android:id="@+id/order_option_btn_cut_4"
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_weight="1"
                    app:srcCompat="@drawable/sel_order_option_btn_cut_4" />

                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_gravity="center_horizontal"
                    android:layout_marginTop="4dp"
                    android:fontFamily="@font/noto_sans_kr_regular"
                    android:text="4개"
                    android:textColor="#7d7d7d"
                    android:textSize="12sp" />
            </LinearLayout>

</androidx.constraintlayout.widget.ConstraintLayout>
```

<br>

### 1. 확장함수 사용하기 

- kotlin collection에서 제공하는 확장함수 사용
- split() 함수를 이용해 uri에서 파일명을 분리한다

BoosterUtil.kt

```kotlin
fun getFileName(uri: Uri?): String? {
        if (uri == null) {
            return ""
        }
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.moveToNext()
        val path = cursor?.getString(cursor.getColumnIndex("_data"))
        cursor?.close()
        val filePath = path?.split("/")

        return filePath?.get(filePath.size - 1)
}
```

- 기존 클래스에 custom 함수를 확장하여 사용
- BindingAdapter에서TextView와 ImageView 등의 view 요소에 확장함수를 구현하여 사용

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

<br>

### 4. 화면을 아래로 당겨서 List Refresh 하기

#### 🔥 issue

- 주문 현황 업데이트를 위해 List를 당겨서 새로고침할 수 있도록 한다

#### 📒 solution

- 원하는 List Layout을 SwipeRefreshLayout으로 감싼다
- SwipeRefreshLayout에 setOnRefreshListener를 추가해 통신 함수를 수행한다

fragment_store_list.kt

```kotlin
<androidx.swiperefreshlayout.widget.SwipeRefreshLayout
            android:id="@+id/frag_store_list_srl"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:layout_behavior="@string/appbar_scrolling_view_behavior">

            <androidx.recyclerview.widget.RecyclerView
                android:id="@+id/frag_store_list_rv"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/white"
                android:clipToPadding="false"
                android:paddingTop="8dp" />
</androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
```

StoreListFragment.kt

```kotlin
private fun refresh(){
        frag_store_list_srl.apply{
            setOnRefreshListener {
                viewModel.getStoreList(univIdx)
                this@apply.isRefreshing = false
            }
        }
    }
```

#### 🗞 result

- 사용자가 원할 때 레이아웃을 당겨 List를 업데이트할 수 있다

<br>

### 5. 로컬 디바이스에서 파일 가져오기

#### 🔥 issue

- 파일을 가져올 수 있는 커스텀 저장소를 구현한다. 

#### 📒 solution

- DroidNinja 의 Android-FilePicker(https://github.com/DroidNinja/Android-FilePicker) 라이브러리를 활용하여 커스텀 파일 저장소 구현 

```kotlin
 private fun fileAdd() {
        val builder: AlertDialog.Builder =
            AlertDialog.Builder(this, R.style.MyAlertDialogStyle2)
        builder.setTitle("추가할 파일의 종류를 선택해주세요")
        builder.setPositiveButton("이미지") { dialogInterface: DialogInterface, i: Int ->
            FilePickerBuilder.instance
                .setMaxCount(1)
                .setActivityTheme(R.style.LibAppTheme) //optional
                .setActivityTitle("이미지 선택")
                .pickPhoto(this, REQUEST_CODE_PHOTO);
        }
        builder.setNegativeButton("문서") { dialogInterface: DialogInterface, i: Int ->
            FilePickerBuilder.instance
                .setMaxCount(1)
                .setActivityTheme(R.style.LibAppTheme) //optional
                .setActivityTitle("문서 선택")
                .pickFile(this, REQUEST_CODE_DOC);
        }
        builder.show()

    }
```

#### 🗞 result

- File Picker Open Source를 분석해서 부스터 프로젝트에 적용하는 것이 까다로웠다. 하지만 프로젝트의 요구사항에 맞게 테마 및 기능을 수정하여 성공적으로 파일을 업로드 할 수 있었다. 

<br>

### 6. form-data 로 서버에 데이터(image,pdf 등) 전송하기

#### 🔥 issue

- form-data 로 pdf, image 파일을 서버에 전송해야 한다.

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

<br>

### 7. pdf 미리보기 기능 및 thumbnail 추출

#### 🔥 issue

- pdf를 저장소로부터 받아와서 미리보기 기능을 제공하고 첫 페이지(썸네일)를 이미지로 추출한다.

#### 📒 solution

- PdfRenderer 를 이용해서 pdf 미리보기 기능 제공

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

- PdfRenderer를 이용해서 썸네일 이미지(bitmap) 추출

```kotlin
object PDFThumbnailUtils {
    fun convertPDFtoBitmap(context: Context, uri: Uri, pageNumber: Int): Bitmap? {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val pdfRenderer = parcelFileDescriptor?.let { PdfRenderer(it) }
            val currentPage = pdfRenderer?.openPage(pageNumber)
            val bitmap = Bitmap.createBitmap(currentPage?.width!!, currentPage?.height!!, Bitmap.Config.ARGB_8888)
            currentPage?.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            // Here, we render the page onto the Bitmap.
            return bitmap
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
    }
}
```

- 실제 리사이클러뷰 데이터에 적용

```kotlin
inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(file: File) {
            //Log.e("uri", file.uri.path.toString())

            Log.e("file", file.file_name + " " + file.file_extension)
            if (file.file_extension == ".png" || file.file_extension == ".jpeg" || file.file_extension == ".jpg") {
                Glide.with(itemView.context).load(file.file_path).into(itemView.iv_file)
            } else {
                val uri = file.file_uri
                if (uri != null) {
                    val bitmap =
                        PDFThumbnailUtils.convertPDFtoBitmap(
                            itemView.context,
                            uri,
                            PAGE_NUMBER
                        )
                    if (bitmap != null) {
                        itemView.iv_file.setImageBitmap(bitmap)
                        //Log.e("context check: ", " " + itemView.context + " " + itemView.context.javaClass.name)
                    }
```

#### 🗞 result

- pdf나 이미지 미리보기를 제공 할 수 있었지만, hwp,ppt 등 오피스 기반 문서들은 제공하기에 까다로웠다. 방법을 찾아보도록 하겠다.

<br>

### 8. 추출한 썸네일을 Multipart로  서버로 전송

#### 🔥 issue

- bitmap 형태의 썸네일을 Multipart를 이용하여 서버에 업로드하기 위해 이미지를 파일형태로 변환하여 전송한다.

#### 📒 solution

- bitmap을 png형태 파일로 변환

```kotlin
private fun bitmapToFile(bitmap:Bitmap): java.io.File? {
        // Get the context wrapper
        val wrapper = ContextWrapper(applicationContext)

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images",Context.MODE_PRIVATE)
        file = java.io.File(file,"${UUID.randomUUID()}.png")

        try{
            // Compress the bitmap and save in jpg format
            val stream:OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return file
    }
```

#### 🗞 result

- Local Storage에서 가져온 bitmap 파일이 아니라 filepath를 만들어주기 위하여 함수를 정의하여 사용하였고 성공적으로 업로드가 가능하였다.

<br>

### 9. 네이버지도 API 사용하기 

#### 🔥 issue

- 매장 리스트를 지도 위의 마커로 나타낼 수 있고, 마커를 통해 상세정보 페이지로 이동하여 정보를 제공할 수 잇다. 

#### 📒 solution

- 네이버지도 API를 이용해서 매장의 위도, 경도 정보를 리스트로 받아와서 마커로 표시한다.

StoreListFragment.kt

```kotlin
markers.clear()
            for(i in 0 .. it.size-1){
                markers.add(
                    MarkerData(
                        latitude = it[i].store_x_location,
                        longitude = it[i].store_y_location,
                        name = it[i].store_name,
                        idx = it[i].store_idx
                    )
                )
            }
```

MapActvity.kt

```kotlin
@UiThread
    override fun onMapReady(nMap: NaverMap) {

        val uiSettings = nMap.uiSettings
        uiSettings.isZoomControlEnabled = true
        uiSettings.isLocationButtonEnabled = true

        nMap.locationSource
        nMap.locationTrackingMode
        uiSettings.isScaleBarEnabled = false

        if (university == "숭실대학교"){
            cameraUpdate = CameraUpdate.scrollTo(LatLng(37.496575, 126.957427))
        }... //선택한 학교 별로 focus 맞추기
        act_map_txt_univ.text = university

        nMap.moveCamera(cameraUpdate)
        draw(nMap)
    }
    //실제 지도를 그리기 시작한다. 
    fun draw(nMap: NaverMap){
        for(i in 0 until markers.size){
            repeat(1000) {
                array.plusAssign(Marker().apply {
                    position = LatLng(markers[i].latitude!!.toDouble(), markers[i].longitude!!.toDouble())
                    icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker)
                    tag = markers[i].name
                    width = Marker.SIZE_AUTO
                    height = Marker.SIZE_AUTO
                })
            }
        }
        //마커 클릭시 tag 띄우기
        val infoWindow = InfoWindow()
        infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(this) {
            override fun getText(infoWindow: InfoWindow): CharSequence {
                return infoWindow.marker?.tag as CharSequence? ?:""
            }
        }
        //받아온 매장 리스트별로 마커를 띄워준다. 마커 클릭시 마커 이미지 바꿈 + tag띄우기 + tag클릭시 상세페이지로 이동
        array.forEach { marker ->
            marker.map = nMap
            marker.setOnClickListener {
                for ( i in 0 until array.size){
                    array[i].icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker)
                }
                marker.icon = OverlayImage.fromResource(R.drawable.store_map_ic_marker_click)
                val cameraUpdate = CameraUpdate.scrollTo(marker.position)
                nMap.moveCamera(cameraUpdate)

                infoWindow.open(marker)
                infoWindow.setOnClickListener {
                    val intent = Intent(this, StoreDetailActivity::class.java)
                    for(i in 0 .. array.size){
                        if(markers[i].name == marker.tag){
                            val idx = markers[i].idx
                            intent.putExtra("storeIdx", idx)
                            break
                        }
                    }
                    startActivity(intent)
                    false
                }
                false
            }
        }
    }
```

#### 🗞 result

- 여러개의 매장을 지도에 마커로 띄워줌으로써 사용자가 직관적으로 매장의 위치를 확인할 수 있게 했다.

<br>


### 10. SharedPreferences를 이용해서 사용자 토큰 관리하기

#### 🔥 issue

- 앱을 재구동하여도 사용자의 활동기록을 다시 볼 수 있게 사용자별 토큰을 저장한다. 해당 토큰으로 통신을 하여 사용자별 활동을 식별한다. 

#### 📒 solution

- SharedPreferences를 이용하여 로그인시 서버로부터 받은 토큰을 저장하여, 전역에서 해당 토큰에 접근할 수 있다. 
- 또한 Interceptor로 만들어둬 통신할 때마다 필요한 token값을 따로 넣어주지 않고 미리 sharedpreferences에 저장해둔 token값을 사용할 수 있다. 

UserManager.kt

```kotlin
object UserManager {
    private lateinit var pref: SharedPreferences
    fun init(context: Context) {
        pref = context.getSharedPreferences("Booster", Context.MODE_PRIVATE)
    }

    var token: String?
        get() = pref.getString("token", null)
        set(value) = pref.edit {
            it.putString("token", value)
        }
}
```

CookiesInterceptor

```kotlin
class CookiesInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().header("Content-Type", "application/json")
                .header("token", UserManager.token?:"")
                .build()
        return chain.proceed(request)
    }
}
```

- 로그인 통신시 서버로 토큰 값을 받아온다.

LoginActivity.kt

```kotlin
intent.putExtra("token", response.body()!!.data.accessToken)
```

BottomTabActivity.kt

```kotlin
if(intent.hasExtra("token")){
            bottom_vp.currentItem = 0
            token = intent.getStringExtra("token")
            UserManager.token = token

        }
```

#### 🗞 result

- 초기에 여러 기능을 구현하고 test를 할 때 동일한 토큰을 사용해서 주문현황 및 상세내역 정보가 굉장히 많아서 보기 힘들었는데, 각 사용자별 토큰을 사용하니 기능 test하기 편해졌고, 사용자별 관리를 할 수 있어 좋았고, 이후 이 기능을 더 배워보고 공부하고 싶다. 

<br>

### 11. setOnKeyListener 이용해서 focus 

#### 🔥 issue

- 엔터로 EditText를 나올 때 focus를 해제할 수 있도록 구현한다. 

#### 📒 solution

- setOnFocuseChangeListener와 setOnKeyListener 함께 사용하여 focus를 설정 및 해제한다.

JoinActivity.kt

```kotlin
join_edt_pw_chk.setOnFocusChangeListener { v, hasFocus ->
   join_edt_pw_chk.isSelected = hasFocus
}

join_edt_pw_chk.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                v.clearFocus()
                val keyboard: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                keyboard.hideSoftInputFromWindow(join_edt_pw_chk.windowToken, 0)
                return@OnKeyListener true
            }
            false
        })
```

#### 🗞 result

- KeyEvent를 이용하여 원하는 입력에 따라 focus를 해제할 수 있다

<br>

### 12. addTextChangedListener 이용해서 비밀번호 실시간 체크

#### 🔥 issue

- 비밀번호 입력과 비밀번호 확인 입력을 실시간으로 체크하여 TextView의 visibility를 바꾼다.

#### 📒 solution

- addTextChangedListener를 이용하여 입력의 변화를 실시간으로 체크할 수 있게끔 하였다.

JoinActivity.kt

```kotlin
join_edt_pw_chk.addTextChangedListener {

                if (join_edt_pw.text.toString() == join_edt_pw_chk.text.toString()) {
                    join_tv_pw_check_fail.visibility = View.INVISIBLE
                    pwChk = true
                } else {
                    join_tv_pw_check_fail.visibility = View.VISIBLE
                }

            }
```

#### 🗞 result

- EditText의 text를 실시간으로 비교하여 사용자에게 적절한 경고가 가능하도록 했다.

<br>

### putExtra를 이용해서 Fragment와 Activity 간의 데이터를 전달

#### 🔥 issue

- 단순한 데이터 표시를 위해 불필요하게 api 요청을 하게 되어 코드가 불필요하게 길어지는 문제가 있었다.

#### 📒 solution

- putExtra와 getStringExtra를 이용하여 Fragment와 Activity간에 데이터를 전달할 수 있게끔 하였다.

MypageFragment.kt

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mypage_tv_goto_edit.setOnClickListener {

            val intent = Intent(context, EditProfileActivity::class.java)
            intent.putExtra("id", mypage_tv_id.text.toString())
            intent.putExtra("univ", univIdx.toString())
            intent.putExtra("name", mypage_tv_name.text.toString())
            startActivity(intent)
        }

        mypage_tv_goto_myengine.setOnClickListener {

            val intent = Intent(context, MyengineActivity::class.java)
            startActivity(intent)
        }
    }
```

EditProfileActivity.kt

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        
        var extraId = intent.getStringExtra("id")
        var extraUnivIdx = intent.getStringExtra("univ")
        var extraName = intent.getStringExtra("name")
}
```

#### 🗞 result

- 간단히 data를 View간에 전달하며 불필요한 통신을 줄였다.

<br>


## 👨‍👨‍👧‍👧‍👧 Developer


- 김예진 - [jineee](https://github.com/jineeee)
- 김찬영 - [ghkdua1829](https://github.com/ghkdua1829)
- 김지현 - [jiHyeonMon](https://github.com/JiHyeonMon)
- 서정록 - [chop-sui](https://github.com/chop-sui)
- 이정민 - [danmin20](https://github.com/danmin20)
