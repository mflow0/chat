package com.mflow.chatmeet.Message;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.mflow.chatmeet.R;

/**
 *
 * 메시지를 보낼때 메시지가 담긴 레이아웃 뷰를 커스텀하게 만들어 정보를 실제로 구현해주는 알고리즘이 담긴 클래스
 *
 **/

public class MessageAdapter extends BaseAdapter {
	
	private Context context;
	LayoutInflater Inflater;
	private List<Message> data = null;
	
	public MessageAdapter(Context context, List<Message> list) {
		super();
		this.context = context;
		this.data = list;
		Inflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return data != null ? data.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return this.data.get(position).getIsSend() ? 1 : 0;
	}
	
	@Override
	public int getViewTypeCount() {
		return 2;
	}

    public List<Message> getData() {
        return data;
    }

    public void setData(List<Message> data) {
        this.data = data;
    }

    //년도와 날짜가 같은지를 판별하는 method
    public static boolean inSameDay(Date date1, Date Date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(Date2);                            //date 객체의 날짜와 시간 정보를 현재 객체로 생성한다.
        int year2 = calendar.get(Calendar.YEAR);            //현재 년도를 가져온다.
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);      //현재 년도의 날짜

        if ((year1 == year2) && (day1 == day2)) {
            return true;
        }
        return false;
    }


    static class ViewHolder {

        public ImageView	userAvatarImageView;    // 아바타 이미지
        public TextView 	sendDateTextView;       //첫번째 필드 날짜
        public TextView 	userNameTextView;       //글 입력시 나타나는 필드

        public TextView 	textTextView;
        public ImageView 	photoImageView;
        public ImageView 	faceImageView;

        public ImageView 	failImageView;          //실패 이미지
        public TextView 	sendTimeTextView;
        public ProgressBar 	sendingProgressBar;     //전송중 프로그레스바

        public boolean 		isSend = true;
    }

	@SuppressLint("InflateParams")
	public View getView(int position, View convertView, ViewGroup parent) {

		final Message message = data.get(position);
		boolean isSend = message.getIsSend();

		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			if (isSend) {
				convertView = LayoutInflater.from(context).inflate(R.layout.msg_item_right, null);
			} else {
				convertView = LayoutInflater.from(context).inflate(R.layout.msg_item_left, null);
			}
			viewHolder.sendDateTextView = (TextView) convertView.findViewById(R.id.sendDateTextView);
			viewHolder.sendTimeTextView = (TextView) convertView.findViewById(R.id.sendTimeTextView);
			viewHolder.userAvatarImageView = (ImageView) convertView.findViewById(R.id.userAvatarImageView);
			viewHolder.userNameTextView = (TextView) convertView.findViewById(R.id.userNameTextView);
			viewHolder.textTextView = (TextView) convertView.findViewById(R.id.textTextView);
			viewHolder.photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
			viewHolder.faceImageView = (ImageView) convertView.findViewById(R.id.faceImageView);
			viewHolder.failImageView = (ImageView) convertView.findViewById(R.id.failImageView);
			viewHolder.sendingProgressBar = (ProgressBar) convertView.findViewById(R.id.sendingProgressBar);
			
			
			viewHolder.isSend = isSend;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		try {
            //날짜를 분할하는 작업 년도 / 시간
			String dateString = DateFormat.format(" yyyy년 MM월 dd일 ,aa h:mm", message.getTime()).toString();
			String [] t = dateString.split(",");
            String time_now = "";
			viewHolder.sendDateTextView.setText(t[0]);

            if (t[1].contains("AM")) {
                time_now = t[1].replace("AM", "오전");
            } else {
                time_now = t[1].replace("PM", "오후");
            }
            viewHolder.sendTimeTextView.setText(time_now);
			
			if(position == 0){      //처음 글에해당하는 것이면 날짜를 보여준다. -- 상단에 나타나는 날짜를 화면에 한번만 보여주기 위한 작업
				viewHolder.sendDateTextView.setVisibility(View.VISIBLE);
			}else{
                //데이터의 마지막에 작성한 글과 날짜가 지나 새로작성글을 판별하여 상단에 날짜를 표시하기 위한 작업
				//TODO is same day ?
				Message pmsg = data.get(position-1);
				if(inSameDay(pmsg.getTime(), message.getTime())){
					viewHolder.sendDateTextView.setVisibility(View.GONE);
				}else{
					viewHolder.sendDateTextView.setVisibility(View.VISIBLE);
				}
				
			}
			
		} catch (Exception e) {
		}
		
		viewHolder.userNameTextView.setText(message.getFromUserName());
		
		

		
		switch (message.getType()) {
		case 0://text
			viewHolder.textTextView.setText(message.getContent());
			viewHolder.textTextView.setVisibility(View.VISIBLE);
			viewHolder.photoImageView.setVisibility(View.GONE);
			viewHolder.faceImageView.setVisibility(View.GONE);
			if(message.getIsSend()){    //전송상태 판별 여부
				LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
				sendTimeTextViewLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.textTextView);
				viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
				
				LayoutParams layoutParams = (LayoutParams) viewHolder.failImageView.getLayoutParams();
				layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.textTextView);
				if( message.getSendSucces() != null && message.getSendSucces() == false){
					viewHolder.failImageView.setVisibility(View.VISIBLE);
					viewHolder.failImageView.setLayoutParams(layoutParams);
				}else{
					viewHolder.failImageView.setVisibility(View.GONE);
				}
				
				if(message.getState() != null && message.getState() == 0){
					viewHolder.sendingProgressBar.setVisibility(View.VISIBLE);
					viewHolder.sendingProgressBar.setLayoutParams(layoutParams);
				}else{
					viewHolder.sendingProgressBar.setVisibility(View.GONE);
				}
				
			}else{
				viewHolder.failImageView.setVisibility(View.GONE);
				viewHolder.sendingProgressBar.setVisibility(View.GONE);
				
				LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
				sendTimeTextViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.textTextView);
				viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
			}
			
			
			break;
		case 1://photo
			viewHolder.textTextView.setVisibility(View.GONE);
			viewHolder.photoImageView.setVisibility(View.VISIBLE);
			viewHolder.faceImageView.setVisibility(View.GONE);
			
			//TODO set image
			int id = context.getResources().getIdentifier(message.getContent(), "drawable", context.getPackageName());
			viewHolder.photoImageView.setImageResource(id);
			
			
			if(message.getIsSend() ){
				LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
				sendTimeTextViewLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.photoImageView);
				viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
				
				LayoutParams layoutParams = (LayoutParams) viewHolder.failImageView.getLayoutParams();
				layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.photoImageView);
				if(message.getSendSucces() != null && message.getSendSucces() == false){
					viewHolder.failImageView.setVisibility(View.VISIBLE);
					viewHolder.failImageView.setLayoutParams(layoutParams);
				}else{
					viewHolder.failImageView.setVisibility(View.GONE);
				}
				
				if(message.getState() != null && message.getState() == 0){
					viewHolder.sendingProgressBar.setVisibility(View.VISIBLE);
					viewHolder.sendingProgressBar.setLayoutParams(layoutParams);
				}else{
					viewHolder.sendingProgressBar.setVisibility(View.GONE);
				}
				
			}else{
				viewHolder.failImageView.setVisibility(View.GONE);
				LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
				sendTimeTextViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.photoImageView);
				viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
			}
			
			
			break;
			
		case 2://face
			viewHolder.photoImageView.setVisibility(View.GONE);
			viewHolder.textTextView.setVisibility(View.GONE);
			viewHolder.faceImageView.setVisibility(View.VISIBLE);
			int resId = context.getResources().getIdentifier(message.getContent(), "drawable", context.getPackageName());
			viewHolder.faceImageView.setImageResource(resId);
			
			if(message.getIsSend()){
				LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
				sendTimeTextViewLayoutParams.addRule(RelativeLayout.LEFT_OF, R.id.faceImageView);
				viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
				
				LayoutParams layoutParams = (LayoutParams) viewHolder.failImageView.getLayoutParams();
				layoutParams.addRule(RelativeLayout.LEFT_OF, R.id.faceImageView);
				if(message.getSendSucces() != null && message.getSendSucces() == false){
					viewHolder.failImageView.setVisibility(View.VISIBLE);
					viewHolder.failImageView.setLayoutParams(layoutParams);
				}else{
					viewHolder.failImageView.setVisibility(View.GONE);
				}
				
				if(message.getState() != null && message.getState() == 0){
					viewHolder.sendingProgressBar.setVisibility(View.VISIBLE);
					viewHolder.sendingProgressBar.setLayoutParams(layoutParams);
				}else{
					viewHolder.sendingProgressBar.setVisibility(View.GONE);
				}
				
			}else{
				viewHolder.failImageView.setVisibility(View.GONE);
				
				LayoutParams sendTimeTextViewLayoutParams = (LayoutParams) viewHolder.sendTimeTextView.getLayoutParams();
				sendTimeTextViewLayoutParams.addRule(RelativeLayout.RIGHT_OF, R.id.faceImageView);
				viewHolder.sendTimeTextView.setLayoutParams(sendTimeTextViewLayoutParams);
			}
			
			break;

		default:
			viewHolder.textTextView.setText(message.getContent());
			viewHolder.photoImageView.setVisibility(View.GONE);
			viewHolder.faceImageView.setVisibility(View.GONE);
			break;
		}

//		viewHolder.textTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

		return convertView;
	}

}
