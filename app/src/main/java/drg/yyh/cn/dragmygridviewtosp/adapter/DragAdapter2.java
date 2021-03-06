package drg.yyh.cn.dragmygridviewtosp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import drg.yyh.cn.dragmygridviewtosp.R;
import drg.yyh.cn.dragmygridviewtosp.bean.ChannelItem;
import drg.yyh.cn.dragmygridviewtosp.bean.DeviceValue;

/**
 * 类功能描述：</br>
 *  适配器GridView
 * 博客地址：http://blog.csdn.net/androidstarjack
 * 公众号：终端研发部
 * @author yuyahao
 * @version 1.0 </p> 修改时间：2017-07-19</br> 修改备注：</br>
 */
public class DragAdapter2 extends BaseCAdapter {
	/** TAG*/
	private final static String TAG = "DragAdapter";
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	private Context context;
	/** 控制的postion */
	private int holdPosition;
	/** 是否改变 */
	private boolean isChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的自定义列表） */
	public List<DeviceValue> channelList;
	/** 要删除的position */
	public int remove_position = -1;
	private TextView item_text;
	public DragAdapter2(Context context, ArrayList<DeviceValue> channelList) {
		super(context,channelList);
		this.context = context;
		this.channelList = channelList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public DeviceValue getItem(int position) {
		// TODO Auto-generated method stub
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
//		if(convertView == null){
//			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.subscribe_category_item, null);
			 item_text = (TextView) convertView.findViewById(R.id.text_item);
//			viewHolder.item_text = (TextView) convertView.findViewById(R.id.text_item);
//			convertView.setTag(viewHolder);
//		}else{
//			viewHolder = (ViewHolder) convertView.getTag();
//		}

		DeviceValue channel = getItem(position);
		 item_text.setText(channel.getDevieName());
		if ((position == 0) || (position == 1)){
//			item_text.setTextColor(context.getResources().getColor(R.color.black));
			item_text.setEnabled(false);
		}
		if (isChanged && (position == holdPosition) && !isItemShow) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (position == -1 + channelList.size())) {
			item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
		}

		if(remove_position == position){
			item_text.setText("");
		}

		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),channel.getDeviceImg());

			Drawable difPotdrawable = ContextCompat.getDrawable(context, channel.getDeviceImg());
			difPotdrawable.setBounds(0, 0, bitmap.getWidth(),bitmap.getHeight());
		item_text.setCompoundDrawablePadding(5);
		item_text.setCompoundDrawables(null, difPotdrawable, null, null);
		return convertView;
	}

	private class ViewHolder{
		TextView item_text;
	}

	/** 添加自定义列表 */
	public void addItem(DeviceValue channel) {
		channelList.add(channel);
		notifyDataSetChanged();
	}

	/** 拖动变更自定义排序 */
	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		DeviceValue dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}
	
	/** 获取自定义列表 */
	public List<DeviceValue> getChannnelLst() {
		return channelList;
	}

	/** 设置删除的position */
	public void setRemove(int position) {
		remove_position = position;
		notifyDataSetChanged();
	}

	/** 删除自定义列表 */
	public void remove() {
		channelList.remove(remove_position);
		remove_position = -1;
		notifyDataSetChanged();
	}
	
	/** 设置自定义列表 */
	public void setListDate(List<DeviceValue> list) {
		channelList = list;
	}
	
	/** 获取是否可见 */
	public boolean isVisible() {
		return isVisible;
	}
	
	/** 设置是否可见 */
	public void setVisible(boolean visible) {
		isVisible = visible;
	}
	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}
}