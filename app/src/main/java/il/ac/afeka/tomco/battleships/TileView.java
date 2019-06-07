package il.ac.afeka.tomco.battleships;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TileView extends LinearLayout {

    TextView text;
    ImageView imageView;

    public TileView(Context context) {
        super(context);

        this.setOrientation(VERTICAL);


        text = new TextView(context);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
       /* text.setLayoutParams(layoutParams); text.setTextAlignment(TEXT_ALIGNMENT_CENTER); text.setGravity(Gravity.CENTER_VERTICAL);
        text.setTextSize(20); text.setTextColor(Color.WHITE); setBackgroundColor(Color.LTGRAY); addView(text);*/

        imageView = new ImageView(context);
        imageView.setLayoutParams(layoutParams);
        imageView.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        setBackgroundColor(Color.LTGRAY);
        addView(imageView);

    }


}
