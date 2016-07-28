package me.knox.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import me.knox.switchingtextview.SwitchingTextView;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    SwitchingTextView switchingTextView1 = (SwitchingTextView) findViewById(R.id.st1);
    SwitchingTextView switchingTextView2 = (SwitchingTextView) findViewById(R.id.st2);
    List<String> strings = new ArrayList<>();
    strings.add("android");
    strings.add("android studio");
    strings.add("android studio preview");
    strings.add("android s");
    strings.add("android st");
    strings.add("android stu");
    strings.add("android stud");
    strings.add("android studi");
    switchingTextView1.setTextContent(strings);
    switchingTextView2.setTextContent(strings);
  }
}
