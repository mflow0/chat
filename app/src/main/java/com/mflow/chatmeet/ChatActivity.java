package com.mflow.chatmeet;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.jialin.chat.Message;
import com.jialin.chat.MessageAdapter;
import com.jialin.chat.MessageInputToolBox;
import com.jialin.chat.OnOperationListener;
import com.jialin.chat.Option;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChatActivity extends ActionBarActivity {

    private MessageInputToolBox box;
    private ListView listView;
    private MessageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initMessageInputToolBox();          //메시지를 추가해주는 툴 박스

        initListView();

    }


    /**
     * init MessageInputToolBox
     */
    @SuppressLint("ShowToast")
    private void initMessageInputToolBox(){
        box = (MessageInputToolBox) findViewById(R.id.messageInputToolBox);
        box.setOnOperationListener(new OnOperationListener() {

            @Override
            public void send(String content) {      //메시지만을 받아서 보내는 로직

                System.out.println("===============" + content);

                Message message = new Message(0, 1, "Tom", "avatar", "Jerry", "avatar", content, true, true, new Date());


                adapter.getData().add(message);     //  List<Message> getData() 위의 한개의 메시지를 추가함.
                listView.setSelection(listView.getBottom());    // 리스트뷰 맨 마지막 item 항목 선택하여 마지막 글 보여주기 위한 작업 : Bottom == 끝부분

                //Just demo
                //createReplayMsg(message);
            }

            @Override
            public void selectedFace(String content) {      //face 메시지를 받아서 보내는 로직

                System.out.println("===============" + content);
                Message message = new Message(Message.MSG_TYPE_FACE, Message.MSG_STATE_SUCCESS, "Tomcat", "avatar", "Jerry", "avatar", content, true, true, new Date());
                adapter.getData().add(message);
                listView.setSelection(listView.getBottom());

                //Just demo
                //createReplayMsg(message);
            }


            @Override
            public void selectedFuncation(int index) {      //현재 사용이 안되고 있음 .추후 분석하여 내용 정의할 것임

                System.out.println("===============" + index);

                switch (index) {
                    case 0:
                        //do some thing
                        break;
                    case 1:
                        //do some thing
                        break;

                    default:
                        break;
                }
                Toast.makeText(ChatActivity.this, "Do some thing here, index :" + index, Toast.LENGTH_SHORT).show();

            }

        });

        ArrayList<String> faceNameList = new ArrayList<String>();       //그림파일을 arrayList 로 저장하기 위한 작업 아래도 똑같다.
        for(int x = 1; x <= 10; x++){
            faceNameList.add("big"+x);
        }

        ArrayList<String> faceNameList1 = new ArrayList<String>();
        for(int x = 1; x <= 7; x++){
            faceNameList1.add("cig"+x);
        }


        ArrayList<String> faceNameList2 = new ArrayList<String>();
        for(int x = 1; x <= 24; x++){
            faceNameList2.add("dig"+x);
        }

        Map<Integer, ArrayList<String>> faceData = new HashMap<Integer, ArrayList<String>>();       // 위에 담긴 그림파일을 전체리스트 그림 파일 항목에 맞게 설정 . 대분류 그림 - 소분류 그림(키,값) 작업
        faceData.put(R.drawable.em_cate_magic, faceNameList2);
        faceData.put(R.drawable.em_cate_rib, faceNameList1);
        faceData.put(R.drawable.em_cate_duck, faceNameList);
        box.setFaceData(faceData);


        //옵션 부분 현재 에뮬에서는 확인할 수 없다. 실단말기에서 테스트 해볼 수가 있다.
        List<Option> functionData = new ArrayList<Option>();
        for(int x = 0; x < 5; x++){
            Option takePhotoOption = new Option(this, "Take", R.drawable.take_photo);
            Option galleryOption = new Option(this, "Gallery", R.drawable.gallery);
            functionData.add(galleryOption);
            functionData.add(takePhotoOption);
        }
        box.setFunctionData(functionData);
        //옵션 사용 끝

    }
    private void initListView(){
        listView = (ListView) findViewById(R.id.messageListview);

        //create Data
        Message message = new Message(Message.MSG_TYPE_TEXT, Message.MSG_STATE_SUCCESS, "Tom", "avatar", "Jerry", "avatar", "Hi", false, true, new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24) * 8));

        List<Message> messages = new ArrayList<Message>();
        messages.add(message);

        adapter = new MessageAdapter(this, messages);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    //답변을 해주는 메시지이며, 현재 데모 상태로 사용중에 있다.
    /*private void createReplayMsg(Message message){

        final Message reMessage = new Message(message.getType(), 1, "Tom", "avatar", "Jerry", "avatar",
                message.getType() == 0 ? "Re:" + message.getContent() : message.getContent(),
                false, true, new Date()
        );
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Thread.sleep(1000 * (new Random().nextInt(3) +1));
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            adapter.getData().add(reMessage);
                            listView.setSelection(listView.getBottom());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
}
