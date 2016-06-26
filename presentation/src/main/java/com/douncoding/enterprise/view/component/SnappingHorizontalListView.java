package com.douncoding.enterprise.view.component;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.douncoding.enterprise.R;

/**
 *
 */
public class SnappingHorizontalListView extends LinearLayout {
    private Context mContext;

    private ViewPager mViewPager;
    private BasePageAdapter mPageAdapter;
    @LayoutRes
    private int contentLayoutId;

    private RecyclerView mDotsRecyclerView;
    private BaseDotsAdapter mDotsAdapter;

    public SnappingHorizontalListView(Context context) {
        super(context);

        defaultSetup(context);
    }

    public SnappingHorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(
                attrs, R.styleable.SnappingHorizontalListView);

        contentLayoutId = typedArray.getResourceId(
                R.styleable.SnappingHorizontalListView_content_layout, 0);

        typedArray.recycle();

        defaultSetup(context);

        initialize();
    }

    // --------------------------------------------------------------------------
    // API START
    // --------------------------------------------------------------------------


    public void clear() {

    }

    // --------------------------------------------------------------------------
    // API END
    // --------------------------------------------------------------------------

    private void defaultSetup(Context context) {
        this.mContext = context;
        this.setOrientation(LinearLayout.VERTICAL);
        this.setGravity(Gravity.CENTER_HORIZONTAL);
    }

    private void initialize() {
        if (contentLayoutId <= 0) {
            throw new NullPointerException("Content Layout 설정해야 합니다.");
        }

        setupDotsView(3);

        setupPagerView(3);
    }

    private void setupPagerView(int count) {
        mViewPager = new ViewPager(mContext);
        mViewPager.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mPageAdapter = new BasePageAdapter(count);
        mViewPager.setAdapter(mPageAdapter);

        mViewPager.setClipToPadding(false);
        mViewPager.setPadding(60, 0, 60, 0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mDotsAdapter != null) {
                    mDotsAdapter.changeToFocusWithPosition(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                /**
                 * Looping ViewPager Optional
                 * DotAdapter 와 호환성을 없어 일단 구현하지 않는다.
                 * 추후 요구사항 발생이 추가한다.
                 */
//                if (state == ViewPager.SCROLL_STATE_IDLE) {
//                    int curr = mViewPager.getCurrentItem();
//                    int last = mPageAdapter.getCount() - 2;
//
//                    if (curr == 0) {
//                        mViewPager.setCurrentItem(last, false);
//                    } else if (curr > last) {
//                        mViewPager.setCurrentItem(1, false);
//                    }
//                }
            }
        });

        mViewPager.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        addView(mViewPager);
    }

    /**
     * {@link BasePageAdapter#getCount()} 의 수에 따라 점을 생성한다. 이후 포커스가 변경되는 시점마다
     * {@link BaseDotsAdapter#changeToFocusWithPosition(int)} 호출을 통해 포커스를 자동으로 변경한다.
     */
    private void setupDotsView(int count) {
        mDotsAdapter = new BaseDotsAdapter(count);

        mDotsRecyclerView = new RecyclerView(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mDotsRecyclerView.setLayoutParams(params);
        mDotsRecyclerView.setLayoutManager(new LinearLayoutManager(
                mContext, LinearLayoutManager.HORIZONTAL, false));
        mDotsRecyclerView.setAdapter(mDotsAdapter);

        addView(mDotsRecyclerView);
    }

    private class BasePageAdapter extends PagerAdapter {
        private int pageCount;

        public BasePageAdapter(int count) {
            super();
            this.pageCount = count;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(container.getContext())
                    .inflate(contentLayoutId, container, false);

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return this.pageCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            container.removeView((View)object);
        }
    }

    private class BaseDotsAdapter extends RecyclerView.Adapter<BaseDotsAdapter.ViewHolder> {
        private final String DOT_IMAGEVIEW_TAG = "DOT";
        // 점의 개수
        private int dotsCount;
        // 선택된 위치
        private int seletedPostion;

        public BaseDotsAdapter(int dotsCount) {
            this.dotsCount = dotsCount;
            this.seletedPostion = 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
            ImageView mDotImage;

            public ViewHolder(View itemView) {
                super(itemView);
                mDotImage = (ImageView)itemView.findViewWithTag(DOT_IMAGEVIEW_TAG);
            }

            @Override
            public void onClick(View v) {
                //TODO 클릭 시 선택된 위치로 BasePageAdapter 위치 변경
            }
        }

        private int dpToPx(int dp) {
            return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout parentView = new LinearLayout(parent.getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.leftMargin = dpToPx(10);
            params.rightMargin = dpToPx(10);
            parentView.setLayoutParams(params);

            ImageView dotImage = new ImageView(parent.getContext());
            dotImage.setLayoutParams(new ViewGroup.LayoutParams(dpToPx(14), dpToPx(14)));
            dotImage.setImageResource(R.drawable.ic_dot_clear_black);
            dotImage.setColorFilter(parent.getResources().getColor(R.color.colorPrimary)
                    , PorterDuff.Mode.SRC_ATOP);
            dotImage.setTag(DOT_IMAGEVIEW_TAG);

            parentView.addView(dotImage);
            return new ViewHolder(parentView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ImageView dotImage = holder.mDotImage;
            if (dotImage != null) {
                if (position == seletedPostion) {
                    dotImage.setImageResource(R.drawable.ic_dot_black);
                } else {
                    dotImage.setImageResource(R.drawable.ic_dot_clear_black);
                }
            }
        }

        @Override
        public int getItemCount() {
            return dotsCount;
        }

        public void changeToFocusWithPosition(int position) {
            this.seletedPostion = position;
            notifyDataSetChanged();
        }
    }
}
