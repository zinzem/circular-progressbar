# Circular Progress Bar
[![Release](https://jitpack.io/v/zinzem/circular-progressbar.svg)](https://jitpack.io/#zinzem/circular-progressbar)

Inspired by [CircularProgressBar](https://github.com/lopspower/CircularProgressBar)

**Simple and lightweight library, [databinding](https://developer.android.com/topic/libraries/data-binding/index.html) compatible, to create circular progress bars.**

## Install
This library is hosted on [JitPack](https://jitpack.io/). Make sure to add it to the repositories.
```
allprojects {
  repositories {
    ...
    maven { url "https://jitpack.io" }
  }
}
```
Then just add this to your dependencies
```
compile 'com.github.zinzem:circular-progressbar:1.0.0'
```

## Usage
### XML
```
 <com.zinzem.circularprogressbar.CircularProgressBar
    android:id="@+id/circular_progress"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:progress="50"
    app:start_angle="0"
    app:swipe_angle="360"
    app:progressbar_color="#512da8"
    app:progressbar_width="10dp"
    app:progressbar_background_color="#d1c4e9"
    app:progressbar_background_width="10dp"
    app:animation_duration="1500"/>
```

#### Parameters
- `progress`: The progress value.
- `start_angle`: The start angle of the progress (and background), in degrees. **Default is -90**, equals to 12:00 on a clock.
- `swipe_angle`: The angle of the circle, in degrees. Value should be > 0 and < 360. **Default is 360**, for a full circle.
- `progressbar_color`: The progress bar color. **Default is black.**
- `progressbar_width`: The progress bar stroke width.  **Default 5dp.**
- `progressbar_background_color`: The progress bar background color. **Default is grey.**
- `progressbar_background_width`: The progress bar background stroke width. **Default 5dp.**
- `animation_duration`: The duration of the progress animation, in milliseconds. **Default is 700.**

#### Databinding

```
<layout>

  <data>
    <variable
        name="task"
        type="..."/>
  </data>
  
  ...

   <com.zinzem.circularprogressbar.CircularProgressBar
      android:id="@+id/circular_progress"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      app:progress="@{task.progress}"
      app:start_angle="0"
      app:swipe_angle="360"
      app:progressbar_color="#512da8"
      app:progressbar_width="10dp"
      app:progressbar_background_color="#d1c4e9"
      app:progressbar_background_width="10dp"
      app:animation_duration="1500"/>
    
</layout
```
