package greg.com.calculator.utils;

import android.content.Context;

/**
 * Created by Greg on 10-01-2017.
 */
public class ResourceRetrieverImpl implements ResourceRetriever {

    private Context mContext;

    public ResourceRetrieverImpl(Context c) {
        mContext = c;
    }
    @Override
    public String getString(int id){
        return mContext.getString(id);
    }
    @Override
    public int getInt(int id) {
        return mContext.getResources().getInteger(id);
    }
}
