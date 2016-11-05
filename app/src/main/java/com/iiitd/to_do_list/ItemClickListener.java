package com.iiitd.to_do_list;

import android.view.View;

/**
 * Created by Nayeem on 11/4/2016.
 */

public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);

}
