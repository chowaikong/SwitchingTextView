package me.knox.switchingtextview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import java.util.List;

public class SwitchingTextView extends TextView implements View.OnClickListener {
  private static final int DEFAULT_SWITCHING_DURATION = 1000;
  private static final int DEFAULT_IDLE_DURATION = 2000;

  private List<String> lists; // string list that will display cyclically
  private int contentSize;
  private String outStr; // content that scroll out currently
  private String inStr; // content that scroll in currently
  private float textBaseY; // baseline of displayed content
  private int currentIndex = 0; // index of displayed content currently

  private int switchDuration = DEFAULT_SWITCHING_DURATION; //switch time
  private int idleDuration = DEFAULT_IDLE_DURATION; // idle time
  private int switchOrientation = 0;

  private float currentAnimatedValue = 0.0f;
  private ValueAnimator animator;

  private int paddingLeft = 0;

  private Paint mPaint;

  // listener for indicating view's current state
  public SwitchTextViewIndexListener indexListener;

  public SwitchingTextView(Context context) {
    this(context, null);
  }

  public SwitchingTextView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SwitchingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SwitchingTextView);
    try {
      switchDuration =
          array.getInt(R.styleable.SwitchingTextView_switchDuration, DEFAULT_SWITCHING_DURATION);
      idleDuration = array.getInt(R.styleable.SwitchingTextView_idleDuration, DEFAULT_IDLE_DURATION);
      switchOrientation = array.getInt(R.styleable.SwitchingTextView_switchOrientation, 0);
    } finally {
      array.recycle();
    }
    init();
  }

  private void init() {
    setOnClickListener(this);

    mPaint = getPaint();
    mPaint.setColor(getCurrentTextColor());

    animator = ValueAnimator.ofFloat(0f, 1f).setDuration(switchDuration);
    animator.setStartDelay(idleDuration);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override public void onAnimationUpdate(ValueAnimator animation) {
        currentAnimatedValue = (float) animation.getAnimatedValue();
        if (currentAnimatedValue < 1.0f) {
          invalidate();
        }
      }
    });
    animator.addListener(new Animator.AnimatorListener() {
      @Override public void onAnimationStart(Animator animation) {

      }

      @Override public void onAnimationEnd(Animator animation) {
        currentIndex = (++currentIndex) % contentSize; // index cycle
        if (indexListener != null) {
          indexListener.showNext(currentIndex);
        }
        outStr = lists.get(currentIndex);
        inStr = lists.get((currentIndex + 1) % contentSize);

        animator.setStartDelay(idleDuration);
        animator.start();
      }

      @Override public void onAnimationCancel(Animator animation) {

      }

      @Override public void onAnimationRepeat(Animator animation) {

      }
    });
  }

  /**
   * @param content content list
   */
  public void setTextContent(List<String> content) {
    lists = content;
    if (lists == null || lists.size() == 0) {
      return;
    }
    contentSize = lists.size();

    outStr = lists.get(0);
    if (contentSize > 1) {
      inStr = lists.get(1);
    } else {
      inStr = lists.get(0);
    }

    if (contentSize > 0) {
      animator.start();
    }
  }

  /**
   * measuring TextView's height
   */
  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    int mWidth = MeasureSpec.getSize(widthMeasureSpec);

    Rect bounds = new Rect();
    if (contentSize <= 0) {
      return;
    }
    String text = lists.get(0);
    mPaint.getTextBounds(text, 0, text.length(), bounds);
    int textHeight = bounds.height();

    paddingLeft = getPaddingLeft();
    int paddingBottom = getPaddingBottom();
    int paddingTop = getPaddingTop();
    int mHeight = textHeight + paddingBottom + paddingTop;

    Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
    float fontHeight = fontMetrics.bottom - fontMetrics.top;
    textBaseY = mHeight - (mHeight - fontHeight) / 2 - fontMetrics.bottom;

    setMeasuredDimension(mWidth, mHeight);
  }

  @Override protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    if (contentSize <= 0) {
      return;
    }

    int verticalOffset = Math.round(2 * textBaseY * (0.5f - currentAnimatedValue));
    if (switchOrientation == 0) {
      if (verticalOffset > 0) { // switching upwards
        canvas.drawText(outStr, paddingLeft, verticalOffset, mPaint);
      } else {
        canvas.drawText(inStr, paddingLeft, 2 * textBaseY + verticalOffset, mPaint);
      }
    } else {
      if (verticalOffset > 0) { // switching downwards
        canvas.drawText(outStr, paddingLeft, 2 * textBaseY - verticalOffset, mPaint);
      } else {
        canvas.drawText(inStr, paddingLeft, -verticalOffset, mPaint);
      }
    }
  }

  @Override public void onClick(View v) {
    if (contentSize > currentIndex) {
      if (indexListener != null) {
        indexListener.onItemClick(currentIndex);
      }
    }
  }

  public interface SwitchTextViewIndexListener {
    void showNext(int index);

    void onItemClick(int index);
  }

  public void setIndexListener(SwitchTextViewIndexListener listener) {
    indexListener = listener;
  }
}

