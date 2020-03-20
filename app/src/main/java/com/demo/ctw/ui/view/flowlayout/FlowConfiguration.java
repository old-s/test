package com.demo.ctw.ui.view.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.demo.ctw.R;

public class FlowConfiguration {
    private static final int DEFAULT_LINE_SPACING = 5;
    private static final int DEFAULT_TAG_SPACING = 10;
    private static final int DEFAULT_FIXED_COLUMN_SIZE = 3; //默认列数

    private int lineSpacing;
    private int tagSpacing;
    private int columnSize;
    private boolean isFixed;

    public FlowConfiguration(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
            lineSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, DEFAULT_LINE_SPACING);
            tagSpacing = a.getDimensionPixelSize(R.styleable.FlowLayout_tagSpacing, DEFAULT_TAG_SPACING);
            columnSize = a.getInteger(R.styleable.FlowLayout_columnSize, DEFAULT_FIXED_COLUMN_SIZE);
            isFixed = a.getBoolean(R.styleable.FlowLayout_isFixed, false);
        } finally {
            a.recycle();
        }
    }

    public int getLineSpacing() {
        return lineSpacing;
    }

    public void setLineSpacing(int lineSpacing) {
        this.lineSpacing = lineSpacing;
    }

    public int getTagSpacing() {
        return tagSpacing;
    }

    public void setTagSpacing(int tagSpacing) {
        this.tagSpacing = tagSpacing;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public void setIsFixed(boolean isFixed) {
        this.isFixed = isFixed;
    }
}