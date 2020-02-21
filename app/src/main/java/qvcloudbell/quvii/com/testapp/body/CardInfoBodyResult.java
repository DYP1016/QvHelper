package qvcloudbell.quvii.com.testapp.body;

public class CardInfoBodyResult {

    /**
     * friendStatus : 0
     * uid : 235753137
     * myFriend : false
     * name : 邓元鹏
     * cardProfileModel : {"cardSettingModel":{"titleVisiableForStranger":false,"emailVisiableForStranger":false,"mobileVisiableForStranger":false,"addressVisiableForStranger":false,"orgVisiableForStranger":false,"receiveCardMessage":true},"uid":235753137,"cardExtensionModel":{"orgLogo":"@lADOB-ISy80BLM0D6A","orgAuthLevel":1,"titleAuthed":false,"nameAuthed":true,"cardAuthed":false,"orgAuthed":true},"cardDynamicModel":{"qrCode":"https://h5.dingtalk.com/zproject/profile.html?profile=%40kgDODg1OsQ&cardToken=43fbdb8656","themeType":1,"orgThemeModel":{"color":"white","themeConfig":{"orgThemeId":"white","iconBgColor":"#FF191F25","hasMask":"-1","btnBlockColor":"#FF666666","bgColor":"#FFFFFFFF","nameBlockColor":"#FF191F25","telBlockColor":"#FF666666","bgMaskColor":"#00000000","fontColorId":"black","iconMediaId":"@lALPDgQ9qanE78l4zNg","titleBlockColor":"#FF666666","bgMediaId":"@lALPDgQ9qZ9KdDfNAhzNBAU"},"type":1,"orgId":4469196},"completeDegree":50,"cardToken":"43fbdb8656"},"orgId":4469196,"cardStyleModel":{"orgThemeId":"white","theme":"white","themeConfig":{"orgThemeId":"white","btnBlockColor":"#FFBBBBBB","bgColor":"#FFFFFFFF","nameBlockColor":"#FF191F25","telBlockColor":"#FF888888","bgMaskColor":"#00000000","iconMediaId":"@lALPDgQ9qX1ZGs14zNg","titleBlockColor":"#FF888888","bgMediaId":"@lALPDgQ9qX-XGSHNAhzNBAU"}}}
     * encodeUid : %40kgDODg1OsQ
     */

    private int friendStatus;
    private String uid;
    private boolean myFriend;
    private String name;
    private String encodeUid;

    public int getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(int friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isMyFriend() {
        return myFriend;
    }

    public void setMyFriend(boolean myFriend) {
        this.myFriend = myFriend;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncodeUid() {
        return encodeUid;
    }

    public void setEncodeUid(String encodeUid) {
        this.encodeUid = encodeUid;
    }
}
