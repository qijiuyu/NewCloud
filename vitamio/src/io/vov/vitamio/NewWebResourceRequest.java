package io.vov.vitamio;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.webkit.WebResourceRequest;

import java.util.Map;

/**
 * Created by addis on 2018/11/7.
 */
@SuppressLint("NewApi")
public class NewWebResourceRequest implements WebResourceRequest {
    Uri uri;
    boolean isForMainFrame;
    boolean isRedirect;
    boolean hasGesture;
    String getMethod;
    Map<String, String> getRequestHeaders;

    @Override
    public Uri getUrl() {
        return uri;
    }

    @Override
    public boolean isForMainFrame() {
        return isForMainFrame;
    }

    @Override
    public boolean isRedirect() {
        return isRedirect;
    }

    @Override
    public boolean hasGesture() {
        return hasGesture;
    }

    @Override
    public String getMethod() {
        return getMethod;
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        return getRequestHeaders;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setForMainFrame(boolean forMainFrame) {
        isForMainFrame = forMainFrame;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }

    public void setHasGesture(boolean hasGesture) {
        this.hasGesture = hasGesture;
    }

    public void setGetMethod(String getMethod) {
        this.getMethod = getMethod;
    }

    public void setGetRequestHeaders(Map<String, String> getRequestHeaders) {
        this.getRequestHeaders = getRequestHeaders;
    }
}
