package il.ac.afeka.tomco.battleships;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;

import il.ac.afeka.tomco.battleships.logic.Board;

public class PlayerTileAdapter extends BaseAdapter {
    private Context mContext;
    private Board mBoard;

    PlayerTileAdapter(Context context, Board board) {
        mBoard = board;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mBoard.getBoardSize();
    }

    @Override
    public Object getItem(int position) {
        return mBoard.getTile(position);
    }

    @Override
    public long getItemId(int position) {//not in use...
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final TileView tileView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            Log.e("Tile Adapter", "New Tile");
            tileView = new TileView(mContext);
            tileView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            tileView.setPadding(1, 1, 1, 1);
        } else {
            tileView = (TileView) convertView;
            Log.e("Tile Adapter", "Recycled Tile");

        }

        tileView.text.setText(mBoard.getTile(position).getStatus().toString());
        if (mBoard.getTile(position).getStatus() == Board.TileState.SHIP)
            tileView.setBackgroundResource(mBoard.getTile(position).getShipAssigned().getColor());
        else if (mBoard.getTile(position).getStatus() == Board.TileState.HIT) {
            tileView.imageView.setImageResource(R.drawable.explode);
        } else if (mBoard.getTile(position).getStatus() == Board.TileState.MISS) {
            tileView.imageView.setImageResource(R.drawable.ripple);
        } else {
            tileView.setBackgroundColor(0xFF353535);
        }
        tileView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tileView.getViewTreeObserver().removeOnPreDrawListener(this);
                tileView.setLayoutParams(new GridView.LayoutParams(tileView.getWidth(), tileView.getWidth()));
                return false;
            }
        });

        return tileView;
    }
}
