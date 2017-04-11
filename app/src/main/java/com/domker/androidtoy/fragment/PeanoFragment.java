/**
 * @file PeanoFragment.java
 * @Package com.domker.androidtoy.fragment
 * @Project AndroidToy
 * Description: TODO 
 * 
 * @author wanlipeng
 * @since 1.0.0.0
 * @version 1.0.0.0
 * @date 2016 上午11:27:35
 * 
 *       \if TOSPLATFORM_CONFIDENTIAL_PROPRIETARY
 *       ================================
 *            \n Copyright (c) 2014
 *       wanlipeng. All Rights Reserved.\n \n
 *       ==================================
 *       ==========================================\n \n Update History\n \n
 *       Author (Name[WorkID]) | Modification | Tracked Id | Description\n
 *       --------------------- | ------------ | ---------- |
 *       ------------------------\n wanlipeng | 2016 上午11:27:35 | <xxxxxxxx>
 *       | Initial Created.\n \n \endif
 * 
 *       <tt>
 * \n
 * Release History:\n
 * \n
 * Author (Name[WorkID]) | ModifyDate | Version | Description \n
 * --------------------- | ---------- | ------- | -----------------------------\n
 * wanlipeng |2016 上午11:27:35 | 1.0.0.0 | Initial created. \n
 * \n
 * </tt>
 */
package com.domker.androidtoy.fragment;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.domker.androidtoy.R;
import com.domker.androidtoy.color.ColorPickerDialog;
import com.domker.androidtoy.color.ColorPickerDialog.OnColorChangedListener;
import com.domker.androidtoy.view.PeanoView;

/**
 * 皮亚诺曲线
 * 
 * @ClassName: PeanoFragment
 * @author wanlipeng
 * @date 2016年12月8日 上午11:27:35
 */
public class PeanoFragment extends BaseFragment implements
		OnSeekBarChangeListener, OnClickListener {
	private SeekBar sizeBar = null;
	private SeekBar widthBar = null;
	private PeanoView peanoView = null;
	private TextView textViewSize = null;
	private TextView textViewWidth = null;
	private ImageView imageViewColor = null;

	private int size = 1;
	private int width = 1;

	/*
	 * <p>Title: initLayoutResId</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.domker.androidtoy.fragment.BaseFragment#initLayoutResId()
	 */
	@Override
	protected int initLayoutResId() {
		return R.layout.graph_peano_layout;
	}

	/*
	 * <p>Title: initSubViews</p> <p>Description: </p>
	 * 
	 * @see com.domker.androidtoy.fragment.BaseFragment#initSubViews()
	 */
	@Override
	protected void initSubViews() {
		sizeBar = (SeekBar) this.findViewById(R.id.seekBarSize);
		widthBar = (SeekBar) this.findViewById(R.id.seekBarWidth);
		peanoView = (PeanoView) this.findViewById(R.id.peanoView1);
		textViewSize = (TextView) this.findViewById(R.id.textViewSize);
		textViewWidth = (TextView) this.findViewById(R.id.textViewWidth);
		imageViewColor = (ImageView) this.findViewById(R.id.imageViewColor);
		findViewById(R.id.buttonSelectColor).setOnClickListener(this);

		sizeBar.setOnSeekBarChangeListener(this);
		widthBar.setOnSeekBarChangeListener(this);
		size = sizeBar.getProgress();
		width = widthBar.getProgress();
		imageViewColor.setBackgroundColor(peanoView.getColor());
	}

	@Override
	protected void onShow() {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSelectColor:
			showColorSelector(peanoView.getColor());
			break;
		}
	}

	/** 
	 * 显示颜色选择器
	 * 
	 * @param initColor
	 */
	private void showColorSelector(int initColor) {
		ColorPickerDialog picker = new ColorPickerDialog(getActivity(), initColor);
		picker.setOnColorChangedListener(new OnColorChangedListener() {

			@Override
			public void onColorChanged(int color) {
				peanoView.setColor(color);
				imageViewColor.setBackgroundColor(color);
				peanoView.invalidate();
			}
		});
		picker.setAlphaSliderVisible(true);
		picker.show();
	}
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		if (seekBar == sizeBar) {
			size = progress + 1;
			this.textViewSize.setText(String.valueOf(size));
		} else if (seekBar == widthBar) {
			width = progress + 1;
			this.textViewWidth.setText(String.valueOf(width));
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		peanoView.setLevel(size);
		peanoView.setLineWidth(width);
		peanoView.invalidate();
	}

}
