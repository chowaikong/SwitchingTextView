# SwitchingTextView
A TextView can switching upwards and downwards cyclically.

## Usage  
Add library refrence to your module's build.gradle: 
<pre>compile 'me.knox.switchingtextview:switchingtextview:1.0.0'</pre>
Using SwitchingTextView as normal TextView in xml:
```
<me.knox.switchingtextview.SwitchingTextView
      android:id="@+id/st"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:textSize="30sp"
      android:textColor="@android:color/black"
      android:padding="10dp"
      tools:text="android"
      />
```
Create SwithingTextView instance in Java and add a String list to SwithingTextView: 
```
SwitchingTextView switchingTextView = (SwitchingTextView) findViewById(R.id.st);
List<String> strings = new ArrayList<>();
    strings.add("android");
    strings.add("android studio");
    strings.add("android studio preview");
    strings.add("android s");
    strings.add("android st");
    strings.add("android stu");
    strings.add("android stud");
    strings.add("android studi");
switchingTextView.setTextContent(strings);
switchingTextView.setIndexListener(new SwitchingTextView.SwitchTextViewIndexListener() {
      @Override public void showNext(int index) {
        // do your stuff
      }

      @Override public void onItemClick(int index) {
        // do your stuff
      }
    });
```
## Available attributes 
* switchDuration: setting duration when switching, available value type: `Integer`
* idleDuration: setting idle duration when switching, available value type: `Integer`
* switchOrientation: setting orientation when switching, available values: `up` or `down`, `up` means switching upwards, `down` means switching downwards

## ScreenShot 
![](http://g.recordit.co/x4hh9fsEVb.gif)


## License
Copyright 2016 chowaikong

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.