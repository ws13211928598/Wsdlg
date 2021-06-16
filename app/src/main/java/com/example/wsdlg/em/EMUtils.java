package com.example.wsdlg.em;

import android.net.Uri;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMContactListener;
import com.hyphenate.EMValueCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMUserInfo;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * created by ws
 * on 2021/6/11
 * describe:
 */
public  class EMUtils {

    //用户登录
    public void userLogin(String username,String password){
        EMClient.getInstance().login(username, password, new EMCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onProgress(int progress, String status) {

            }
        });
    }

    //获取好友名字列表
    public List<String> getFriendNameList() {
        try {
            List<String> userNames = EMClient.getInstance().
                    contactManager().
                    getAllContactsFromServer();

            return userNames;
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    //添加好友,需要好友名字和添加信息
    public void addFriend(String userName,String message){
        try {
            EMClient.getInstance().contactManager().addContact(userName,message);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
    //删除好友
    public void deleteFriend(String username){
        try {
            EMClient.getInstance().contactManager().deleteContact(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
    //同意好友请求
    //默认好友请求是自动同意的,如果要手动同意需要在初始化SDK时调用
    public void agreeFriend(String username){
        try {
            EMClient.getInstance().contactManager().declineInvitation(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }

    //拒绝好友请求
    public void refuseFriend(String username){
        try {
            EMClient.getInstance().contactManager().declineInvitation(username);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
    //监听好友状态
    public void listenToFriends(EMContactListener contactListener){
        EMClient.getInstance().contactManager().setContactListener(contactListener);
        /*@Override
        public void onContactAgreed(String username) {
            //好友请求被同意
        }

        @Override
        public void onContactRefused(String username) {
            //好友请求被拒绝
        }

        @Override
        public void onContactInvited(String username, String reason) {
            //收到好友邀请
        }

        @Override
        public void onContactDeleted(String username) {
            //被删除时回调此方法
        }


        @Override
        public void onContactAdded(String username) {
            //增加了联系人时回调此方法
        }*/
    }

    //发送文本消息,text为文本消息,username 是用户username或者群id
    //0 单聊 1群聊
    public void sendTextMessage(String text,String username,int chatType){
        EMMessage txtSendMessage = EMMessage.createTxtSendMessage(text, username);
        //如果是群聊,设置chat type 默认是单聊
        if (chatType==1){
            txtSendMessage.setChatType(EMMessage.ChatType.GroupChat);
        }
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(txtSendMessage);
    }

        //表情消息,表情消息实质上是一个文字消息.emojiCode是表情图片对应的文本字符串,touChatUsername为对方用户或者群聊的id

    //语音消息
    public void sendVoiceMessage(Uri uri,int length,String userName,int chatType){
        //uri是本地资源标识符,length为录音时间
        EMMessage voiceSendMessage = EMMessage.createVoiceSendMessage(uri, length, userName);
        if (chatType==1){
            voiceSendMessage.setChatType(EMMessage.ChatType.GroupChat);
        }

        //发送消息
        EMClient.getInstance().chatManager().sendMessage(voiceSendMessage);
        //发送成功后 获取语音消息附件
        EMVoiceMessageBody voiceMessageBody = (EMVoiceMessageBody) voiceSendMessage.getBody();

        //获取语音文件在服务器的地址
        String remoteUrl = voiceMessageBody.getRemoteUrl();

        //本地语音文件的资源路径
        Uri localUri = voiceMessageBody.getLocalUri();
    }


    //视频消息
    public void sendVideoMessage(Uri videoUri,Uri thumbLocalUri,int videoLength,String userName,int chatType){

        EMMessage videoSendMessage = EMMessage.createVideoSendMessage(videoUri, thumbLocalUri, videoLength, userName);
        if (chatType==1){
        videoSendMessage.setChatType(EMMessage.ChatType.GroupChat);
         }
        EMClient.getInstance().chatManager().sendMessage(videoSendMessage);

        //发送成功后
    }

    //接受消息
    //通过注册消息监听来接收消息
    //记得在不需要的时候移除listener,如activity的onDestroy
    public void acceptMessageMonitoring(EMMessageListener eMMessageListener){
        //移除listener
        //EMClient.getInstance().chatManager().removeMessageListener(emConversationListener);
        EMClient.getInstance().chatManager().addMessageListener(eMMessageListener);
       /* @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }*/

    }
    public void removeMessageListener(EMMessageListener emMessageListener){
        EMClient.getInstance().chatManager().removeMessageListener(emMessageListener);
    }

    public List<EMGroup> getGroupList(){
        try {
            //从服务器加载群组列表
            List<EMGroup> joinedGroupsFromServer = EMClient.getInstance().groupManager().getJoinedGroupsFromServer();
            return joinedGroupsFromServer;

            //从本地加载群组列表
            //List<EMGroup> allGroups = EMClient.getInstance().groupManager().getAllGroups();

        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return new ArrayList<EMGroup>();
    }

    public void getUserProperty(String[] id, EMValueCallBack<Map<String, EMUserInfo>> callBack){
        EMClient.getInstance().userInfoManager().fetchUserInfoByUserId(id,callBack);
    }

}
