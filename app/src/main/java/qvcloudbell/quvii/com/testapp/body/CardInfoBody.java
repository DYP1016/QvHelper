package qvcloudbell.quvii.com.testapp.body;

public class CardInfoBody {

    /**
     * success : true
     * errorCode : null
     * errorMsg : null
     * result : {"friendStatus":0,"uid":235753137,"myFriend":false,"name":"邓元鹏","cardProfileModel":{"cardSettingModel":{"titleVisiableForStranger":false,"emailVisiableForStranger":false,"mobileVisiableForStranger":false,"addressVisiableForStranger":false,"orgVisiableForStranger":false,"receiveCardMessage":true},"uid":235753137,"cardExtensionModel":{"orgLogo":"@lADOB-ISy80BLM0D6A","orgAuthLevel":1,"titleAuthed":false,"nameAuthed":true,"cardAuthed":false,"orgAuthed":true},"cardDynamicModel":{"qrCode":"https://h5.dingtalk.com/zproject/profile.html?profile=%40kgDODg1OsQ&cardToken=43fbdb8656","themeType":1,"orgThemeModel":{"color":"white","themeConfig":{"orgThemeId":"white","iconBgColor":"#FF191F25","hasMask":"-1","btnBlockColor":"#FF666666","bgColor":"#FFFFFFFF","nameBlockColor":"#FF191F25","telBlockColor":"#FF666666","bgMaskColor":"#00000000","fontColorId":"black","iconMediaId":"@lALPDgQ9qanE78l4zNg","titleBlockColor":"#FF666666","bgMediaId":"@lALPDgQ9qZ9KdDfNAhzNBAU"},"type":1,"orgId":4469196},"completeDegree":50,"cardToken":"43fbdb8656"},"orgId":4469196,"cardStyleModel":{"orgThemeId":"white","theme":"white","themeConfig":{"orgThemeId":"white","btnBlockColor":"#FFBBBBBB","bgColor":"#FFFFFFFF","nameBlockColor":"#FF191F25","telBlockColor":"#FF888888","bgMaskColor":"#00000000","iconMediaId":"@lALPDgQ9qX1ZGs14zNg","titleBlockColor":"#FF888888","bgMediaId":"@lALPDgQ9qX-XGSHNAhzNBAU"}}},"encodeUid":"%40kgDODg1OsQ"}
     * arguments : null
     */

    private boolean success;
    private String errorCode;
    private String errorMsg;
    private String result;
    private String arguments;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }
}
