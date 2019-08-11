package com.wei.sample.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;

/**
 * @author shuxin.wei email:weishuxin@maoyan.com
 * @version v1.0.0
 * @date 2019-08-12
 * <p>
 * 自定义观点布局
 */
public class PartView extends View {
    private Paint mPaint;
    private Path mLeftPath;
    private Path mRightPath;

    /**
     * View的宽
     */
    private int mTotalWidth;
    /**
     * View的高
     */
    private int mTotalHeight;
    /**
     * 拐角半径
     */
    private float mCornerRadius = 20f;
    /**
     * 梯形斜边角度
     */
    private int mAngle = 30;

    /**
     * 左侧颜色
     */
    private int mLeftColor = Color.BLUE;
    /**
     * 右侧颜色
     */
    private int mRightColor = Color.RED;

    /**
     * 背景色
     */
    private int mBackGroundColor = Color.WHITE;

    /**
     * left right rate
     */
    private float rate = 0.5f;

    /**
     * 默认宽度
     */
    private static final int DEFAULT_WIDTH = 800;

    /**
     * 默认高度
     */
    private static final int DEFAULT_HEIGHT = 200;
    /**
     * 中间间隔
     */
    private int gap = 10;
    private Context mContext;


    public PartView(Context context) {
        this(context, null);
    }

    public PartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }


    public PartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mContext = context;
    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        mTotalWidth = width;
        mTotalHeight = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPain();

        // 保存为单独的层
        int saveCount = canvas.saveLayer(0, 0, mTotalWidth, mTotalHeight, mPaint, Canvas.ALL_SAVE_FLAG);
        // 绘制画布背景
        canvas.drawColor(mBackGroundColor);
        //绘制左边
        if (rate != 0) {
            initPath(true);
            mPaint.setColor(mLeftColor);
            canvas.drawPath(mLeftPath, mPaint);
        }
        //绘制右边
        if (rate != 1) {
            initPath(false);
            mPaint.setColor(mRightColor);
            canvas.drawPath(mRightPath, mPaint);
        }
        canvas.restoreToCount(saveCount);
    }

    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    private void initPain() {
        mPaint = new Paint();
        //设置拐角为圆角
        CornerPathEffect corEffect = new CornerPathEffect(mCornerRadius);
        mPaint.setPathEffect(corEffect);
        //设置线的头是圆角的
//        mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void initPath(boolean isLeft) {
        //梯形长边和短边差值
        float mDifferentLength = (float) (mTotalHeight * Math.abs(Math.tan(Math.toRadians(mAngle))));
        float gapPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gap,
                getResources().getDisplayMetrics()) / 2;
        int radius = mTotalHeight >> 1;
        float halfCornerRadius = mCornerRadius / 2;
        float half = mDifferentLength / 2 - gapPx;
        float trapezoidMaxLength = mTotalWidth * rate + half;
        float trapezoidShortLength = trapezoidMaxLength - mDifferentLength;

        if (isLeft) {
            mLeftPath = new Path();
            //左侧圆
            mLeftPath.addCircle(radius, radius, radius, Path.Direction.CW);
            //右侧梯形
            mLeftPath.moveTo(radius - halfCornerRadius, 0);
            mLeftPath.lineTo(trapezoidMaxLength, 0);
            mLeftPath.lineTo(trapezoidShortLength, mTotalHeight);
            mLeftPath.lineTo(radius - halfCornerRadius, mTotalHeight);
            //如果是Style.FILL的话，不设置close,也没有区别，可是如果是STROKE模式， 如果不设置close,图形不封闭。当然，你也可以不设置close，再添加一条线，效果一样。
            mLeftPath.close();
        } else {
            mRightPath = new Path();
            //右侧圆
            mRightPath.addCircle(mTotalWidth - radius, radius, radius, Path.Direction.CW);
            //右侧梯形
            mRightPath.moveTo(mTotalWidth - radius + halfCornerRadius, 0);
            mRightPath.lineTo(mTotalWidth - radius + halfCornerRadius, mTotalHeight);
            mRightPath.lineTo(mTotalWidth - trapezoidMaxLength, mTotalHeight);
            mRightPath.lineTo(mTotalWidth - trapezoidShortLength, 0);
            //如果是Style.FILL的话，不设置close,也没有区别，可是如果是STROKE模式， 如果不设置close,图形不封闭。当然，你也可以不设置close，再添加一条线，效果一样。
            mRightPath.close();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取宽-测量规则的模式和大小
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        // 获取高-测量规则的模式和大小
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        // 设置wrap_content的默认宽 / 高值
        // 默认宽/高的设定并无固定依据,根据需要灵活设置
        // 类似TextView,ImageView等针对wrap_content均在onMeasure()对设置默认宽 / 高值有特殊处理,具体读者可以自行查看
        int mWidth = DEFAULT_WIDTH;
        int mHeight = DEFAULT_HEIGHT;

//        // 当布局参数设置为wrap_content时，设置默认值
//        if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT && getLayoutParams().height == ViewGroup
//        .LayoutParams.WRAP_CONTENT) {
//            setMeasuredDimension(mWidth, mHeight);
//            // 宽 / 高任意一个布局参数为= wrap_content时，都设置默认值
//        } else if (getLayoutParams().width == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            setMeasuredDimension(mWidth, heightSize);
//        } else if (getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
//            setMeasuredDimension(widthSize, mHeight);
//        }


        // 当模式是AT_MOST（即wrap_content）时设置默认值
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, mHeight);
            // 宽 / 高任意一个模式为AT_MOST（即wrap_content）时，都设置默认值
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(mWidth, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, mHeight);
        }
    }

    public void setRate(int left, int right) {
        if (right == 0) {
            this.rate = 1;
            return;
        }
        float rate = (float) left / right;
        if (rate > 1) {
            rate = 1;
        } else if (rate < 0) {
            rate = 0;
        }
        this.rate = rate;
    }

    public void setAngle(int angle) {
        this.mAngle = angle;
    }

    public void setCornerRadius(float cornerRadius) {
        this.mCornerRadius = cornerRadius;
    }

    public void setLeftColor(int leftColor) {
        this.mLeftColor = leftColor;
    }

    public void setRightColor(int rightColor) {
        this.mRightColor = rightColor;
    }

    public void setBackGroundColor(int backGroundColor) {
        this.mBackGroundColor = backGroundColor;
    }

    /**
     * 常规绘制  以(0,0)作为坐标原点参考点
     *
     * @param canvas
     */
    private void drawNomal(Canvas canvas) {
        mPaint = new Paint();
        // 绘制画布背景
        canvas.drawColor(Color.GRAY);
        //设置画笔颜色
        mPaint.setColor(Color.BLUE);
        //设置画笔为空心     如果将这里改为Style.STROKE  这个图中的实线圆柱体就变成了空心的圆柱体
        mPaint.setStyle(Paint.Style.STROKE);
        //绘制直线
        canvas.drawLine(50, 50, 450, 50, mPaint);
        //绘制矩形
        canvas.drawRect(100, 100, 200, 300, mPaint);
        //绘制矩形
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(300, 100, 400, 400, mPaint);
        mPaint.setColor(Color.YELLOW);
        RectF r = new RectF(150, 500, 270, 600);
        // 画矩形
        canvas.drawRect(r, mPaint);
        // 画圆
        canvas.drawCircle(50, 500, 50, mPaint);
        RectF oval = new RectF(350, 500, 450, 700);
        // 画椭圆
        canvas.drawOval(oval, mPaint);
        RectF rect = new RectF(100, 700, 170, 800);
        // 画圆角矩形
        canvas.drawRoundRect(rect, 30, 20, mPaint);
        //绘制圆弧 绘制弧形
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
        RectF re1 = new RectF(1000, 50, 1400, 200);
        canvas.drawArc(re1, 10, 270, false, mPaint);
        RectF re2 = new RectF(1000, 300, 1400, 500);
        canvas.drawArc(re2, 10, 270, true, mPaint);
        //设置Path路径
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(3);
        Path path = new Path();
        path.moveTo(500, 100);
        path.lineTo(920, 80);
        path.lineTo(720, 200);
        path.lineTo(600, 400);
        path.close();
        mPaint.setTextSize(46);
        canvas.drawPath(path, mPaint);
        canvas.drawTextOnPath("7qiuwoeruowoqjifasdkfjksjfiojio23ur8950", path, -20, -20, mPaint);
        //三角形
        path.moveTo(10, 330);
        path.lineTo(70, 330);
        path.lineTo(40, 270);
        path.close();
        canvas.drawPath(path, mPaint);
        //把开始的点和最后的点连接在一起，构成一个封闭梯形
        path.moveTo(10, 410);//绘画基点
        path.lineTo(70, 410);
        path.lineTo(55, 350);
        path.lineTo(25, 350);
        //如果是Style.FILL的话，不设置close,也没有区别，可是如果是STROKE模式， 如果不设置close,图形不封闭。当然，你也可以不设置close，再添加一条线，效果一样。
        path.close();
        canvas.drawPath(path, mPaint);
        //参数一为渐变起初点坐标x位置，参数二为y轴位置，参数三和四分辨对应渐变终点,其中参数new int[]{startColor, midleColor,endColor}是参与渐变效果的颜色集合，
        // 其中参数new float[]{0 , 0.5f, 1.0f}是定义每个颜色处于的渐变相对位置， 这个参数可以为null，如果为null表示所有的颜色按顺序均匀的分布
        // Shader.TileMode三种模式
        // REPEAT:沿着渐变方向循环重复
        // CLAMP:如果在预先定义的范围外画的话，就重复边界的颜色
        // MIRROR:与REPEAT一样都是循环重复，但这个会对称重复
        Shader mShader = new LinearGradient(0, 0, 100, 100,
                new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW},
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mShader);// 用Shader中定义定义的颜色来话
        mPaint.setStyle(Paint.Style.FILL);
        Path path1 = new Path();
        path1.moveTo(170, 410);
        path1.lineTo(230, 410);
        path1.lineTo(215, 350);
        path1.lineTo(185, 350);
        path1.close();
        canvas.drawPath(path1, mPaint);
        canvas.save();
    }

    /**
     * 绘制方法练习
     *
     * @param canvas
     */
    private void drawTest(Canvas canvas) {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        //平移测试
        canvas.translate(50, 900);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
        canvas.translate(50, 50);
        canvas.drawRect(new Rect(0, 0, 100, 100), mPaint);
        //缩放测试
        canvas.translate(100, -50);
        canvas.drawRect(new Rect(0, 0, 300, 300), mPaint);
        // 保存画布状态
        canvas.save();
        canvas.scale(0.5f, 0.5f);
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(new Rect(0, 0, 300, 300), mPaint);
        // 画布状态回滚
        canvas.restore();
        // 先将画布平移到矩形的中心
        canvas.translate(400, -50);
        canvas.drawRect(new Rect(0, 0, 300, 300), mPaint);
        //旋转测试
        canvas.save();
        canvas.translate(350, 50);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);
        mPaint.setColor(Color.RED);
        canvas.rotate(45, 200, 200);
        canvas.drawRect(new Rect(0, 0, 200, 200), mPaint);
        canvas.restore();
        //画布错切 三角函数tan的值
        canvas.translate(350, 300);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);
        // y 方向上倾斜45 度
        canvas.skew(0, 1);
        mPaint.setColor(0x8800ff00);
        canvas.drawRect(new Rect(0, 0, 400, 400), mPaint);
    }

}
