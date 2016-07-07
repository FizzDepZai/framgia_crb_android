package framgia.vn.framgiacrb.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import framgia.vn.framgiacrb.R;

/**
 * Created by lucky_luke on 7/6/2016.
 */
public class MonthView extends View {
    private final Resources r = getResources();
    private final float sMarginTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, r.getDisplayMetrics());
    private final float sMarginBottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, r.getDisplayMetrics());;
    private final float sMarginLeft = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 36, r.getDisplayMetrics());;
    private final float sMarginRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, r.getDisplayMetrics());;
    private final float sMarginDateOfWeek = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, r.getDisplayMetrics());;
    private final float sMarginText = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, r.getDisplayMetrics());;
    private final float radius_highlight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18, r.getDisplayMetrics());;
    private final int MAX_X = 6;
    private static final int MAX_Y = 5;
    private static final int HIGHLIGHT_ALPHA = 50;
    private static final int DAYS_COUNT = 42;
    private float mWidth;
    private float mHeight;
    private int selX;     // X index of selection
    private int selY;     // Y index of selection
    private final Rect selRect = new Rect();
    private ArrayList<Date> cells;
    private Calendar mCurrentCalendar = Calendar.getInstance();

    public MonthView(Context context) {
        super(context);
        updateCalendar();
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        updateCalendar();
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        updateCalendar();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = (w - sMarginLeft - sMarginRight) / 7.0f;
        mHeight = (h - sMarginTop - sMarginBottom) / 6.0f;
        getRect(selX, selY, selRect);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void getRect(int x, int y, Rect rect) {
        rect.set((int) (x * mWidth + sMarginLeft), (int) (y * mHeight + sMarginTop), (int) (x * mWidth + mWidth + sMarginLeft),
                (int) (y * mHeight + mHeight + sMarginTop));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onDraw(Canvas canvas) {
        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.greyed_out));
        // Draw the line
        for (int i = 1; i < 6; i++) {
            canvas.drawLine(0, i * mHeight + sMarginTop, getWidth(), i * mHeight + sMarginTop, dark);
        }

        Paint grey = new Paint();
        grey.setColor(getResources().getColor(R.color.color_text));
        grey.setTextSize(mHeight * 0.15f);
        grey.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        String[] day_of_week = getResources().getStringArray(R.array.day_of_week);
        // Draw text day of week
        for (int j = 0; j < 7; j++) {
            canvas.drawText(day_of_week[j], j * mWidth + sMarginLeft, sMarginTop - sMarginDateOfWeek, grey);
        }

        // Draw day of month
        // today
        Date today = new Date();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Date date = cells.get(i * 7 + j);
                int day = date.getDay();
                int month = date.getMonth();
                int year = date.getYear();
                Paint dayOfMonth = new Paint();
                dayOfMonth.setColor(Color.BLACK);
                dayOfMonth.setTextSize(mHeight * 0.12f);
                dayOfMonth.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                if (date.getMonth() != today.getMonth() || date.getYear() != today.getYear()) {
                    dayOfMonth.setColor(getResources().getColor(R.color.greyed_out));
                } else if (date.getDate() == today.getDate()){
                    // Draw the number in the center of the tile
                    Paint.FontMetrics fm = dayOfMonth.getFontMetrics();
                    // Centering in X: use alignment (and X at midpoint)
                    float x = mWidth * j + sMarginLeft + dayOfMonth.measureText(String.valueOf(date.getDate())) / 2;
                    // Centering in Y: measure ascent/descent first
                    float y = mHeight * i + sMarginTop + sMarginText + (fm.ascent + fm.descent) / 2;
                    dayOfMonth.setColor(getResources().getColor(R.color.colorPrimary));
                    dayOfMonth.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(x, y, radius_highlight, dayOfMonth);
                    dayOfMonth.setColor(Color.WHITE);
                }
                canvas.drawText(String.valueOf(date.getDate()), j * mWidth + sMarginLeft, i * mHeight + sMarginTop + sMarginText, dayOfMonth);
            }
        }

        // Draw the selection...
        Paint selected = new Paint();
        selected.setColor(getResources().getColor(R.color.selected));
        selected.setAlpha(HIGHLIGHT_ALPHA);
        canvas.drawRect(selRect, selected);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN) return super.onTouchEvent(event);
        int x = (int)((event.getX() - sMarginLeft) / mWidth);
        int y = (int)((event.getY() - sMarginTop) / mHeight);
        select(x, y);
        Toast.makeText(getContext(), x + " " + y, Toast.LENGTH_SHORT).show();
        return true;
    }

    private void select(int x, int y) {
        invalidate(selRect);
        selX = Math.min(Math.max(x, 0), MAX_X);
        selY = Math.min(Math.max(y, 0), MAX_Y);
        getRect(selX, selY, selRect);
        invalidate(selRect);
    }

    public void setTime(Calendar calendar) {
        this.mCurrentCalendar = calendar;
        updateCalendar();
        this.invalidate();
    }

    public void updateCalendar() {
        cells = new ArrayList<>();
        Calendar currentDate = (Calendar) mCurrentCalendar.clone();
        currentDate.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = currentDate.get(Calendar.DAY_OF_WEEK) - 2;
        currentDate.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);
        while (cells.size() < DAYS_COUNT) {
            cells.add(currentDate.getTime());
            currentDate.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
