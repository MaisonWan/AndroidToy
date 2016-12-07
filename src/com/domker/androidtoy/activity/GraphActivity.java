package com.domker.androidtoy.activity;

import android.app.Activity;
import android.os.Bundle;
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
 * 绘制图形学
 * 
 * @ClassName: GraphActivity
 * @author wanlipeng
 * @date 2016年12月7日 下午5:29:55
 */
public class GraphActivity extends Activity implements OnSeekBarChangeListener, OnClickListener {
	private SeekBar sizeBar = null;
	private SeekBar widthBar = null;
	private PeanoView peanoView = null;
	private TextView textViewSize = null;
	private TextView textViewWidth = null;
	private ImageView imageViewColor = null;
	
	private int size = 1;
	private int width = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph_layout);
		init();
	}

	private void init() {
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
	
	/** 
	 * 显示颜色选择器
	 * 
	 * @param initColor
	 */
	private void showColorSelector(int initColor) {
		ColorPickerDialog picker = new ColorPickerDialog(this, initColor);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonSelectColor:
			showColorSelector(peanoView.getColor());
			break;
		}
	}
}
